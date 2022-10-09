package com.kh.great.domain.dao.comment;

import com.kh.great.domain.dao.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentDAOImpl implements CommentDAO {

  private final JdbcTemplate jt;

  /**
   * 댓글 조회
   *
   * @param commentNum 댓글 번호
   * @return 댓글
   */
  @Override
  public Optional<Comment> find(Long commentNum) {
    StringBuffer sql = new StringBuffer();

    sql.append("select article_num, comment_group, comment_num, p_comment_num, step, comment_order, ");
    sql.append("p_comment_nickname, m.mem_nickname, comment_contents, create_date, reply ");
    sql.append("from comments c, member m ");
    sql.append("where c.mem_number = m.mem_number and c.comment_num = ? ");

    try {
      Comment comment = jt.queryForObject(sql.toString(), new RowMapper<Comment>() {
        @Override
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
          Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
          Comment comment = (new BeanPropertyRowMapper<>(Comment.class)).mapRow(rs, rowNum);
          comment.setMember(member);
          return comment;
        }
      }, commentNum);
      return Optional.of(comment);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  /**
   * 게시글에 달린 댓글 목록 조회
   *
   * @param articleNum
   * @return 댓글 목록
   */
  @Override
  public List<Comment> findAll(Long articleNum) {
    StringBuffer sql = new StringBuffer();

    sql.append("select article_num, comment_group, comment_num, p_comment_num, step, comment_order, ");
    sql.append("p_comment_nickname, m.mem_nickname, c.mem_number, comment_contents, create_date, reply ");
    sql.append("from comments c, member m ");
    sql.append("where c.mem_number = m.mem_number and c.article_num = ? ");
    sql.append("order by comment_group asc, comment_order asc ");

    List<Comment> comments = jt.query(sql.toString(), new RowMapper<Comment>() {
      @Override
      public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
        Comment comment = (new BeanPropertyRowMapper<>(Comment.class)).mapRow(rs, rowNum);
        comment.setMember(member);
        return comment;
      }
    },articleNum);

    return comments;
  }

  /**
   * 댓글 작성
   *
   * @param comment 댓글 정보
   * @return 작성된 댓글 수
   */
  @Override
  public int save(Comment comment) {

    //답댓글이라면
    if (comment.getPCommentNum() != null) {
      Optional<Comment> parentComment = find(comment.getPCommentNum());
      //답댓글 step = 부모 댓글 step + 1
      comment.setStep(parentComment.get().getStep() + 1);
      //step이 1인 경우
      if (comment.getStep() == 1) {
        //순서 = 그룹 내 최고 순서 + 1
        comment.setCommentOrder(maxCommentOrder(comment) + 1);
        //step이 2이상인 경우
      } else {
        //같은 부모 댓글을 가진 동일 step 중 최고 순서보다 큰 순서 + 1
        changeCommentOrder(comment, maxCommentOrderInSameParent(comment));
        //순서 = 같은 부모 댓글을 가진 동일 step 중 최고 순서 + 1
        comment.setCommentOrder(maxCommentOrderInSameParent(comment) + 1);
      }
    }


    StringBuffer sql = new StringBuffer();

    sql.append("insert into comments ");
    sql.append("(article_num, comment_group, comment_num, p_comment_num, step, comment_order, ");
    sql.append("p_comment_nickname, mem_number, comment_contents, create_date, reply) ");
    sql.append("values (?,?,?,?,?,?,?,?,?,sysdate, ?) ");

    log.info("쿼리 삽입하기 전 코멘트 내용 : {}", comment);


    int affectedRow = jt.update(sql.toString(),
        comment.getArticleNum(),
        comment.getCommentGroup(),
        comment.getCommentNum(),
        comment.getPCommentNum(),
        comment.getStep(),
        comment.getCommentOrder(),
        comment.getPCommentNickname(),
        comment.getMemNumber(),
        comment.getCommentContents(),
        comment.getReply());

    return affectedRow;
  }

  //부모 댓글과 동일한 그룹의 답댓글들의 step 변경
//  private int updateStep(Comment comment){
//    StringBuffer sql = new StringBuffer();
//
//    sql.append("update comments ");
//    sql.append("set step = step + 1 ");
//    sql.append("where comment_group = ? ");
//    sql.append("and step > ? ");
//    sql.append("and article_num = ? ");
//
//    int affectedRows = jt.update(sql.toString(), comment.getCommentGroup(), comment.getStep(),comment.getArticleNum());
//
//    return affectedRows;
//  }

  //그룹 내 댓글 순서 최댓값 산출
  private Long maxCommentOrder(Comment comment) {
    String sql = "select max(comment_order) from comments where article_num = ? and comment_group = ? ";

    Long maxCommentOrder = jt.queryForObject(sql, Long.class,comment.getArticleNum(),comment.getCommentGroup());

    return maxCommentOrder;
  }

  //같은 부모 댓글을 가진 동일 step내 순서 최댓값 산출
  private Long maxCommentOrderInSameParent (Comment comment) {
    StringBuffer sql = new StringBuffer();
    sql.append("select max(comment_order) from comments ");
    sql.append("where article_num = ? and comment_group = ? and p_comment_num = ? and step = ? ");

    Long maxCommentOrderInSameParent = jt.queryForObject(sql.toString(), Long.class, comment.getArticleNum(), comment.getCommentGroup(),
                                                                                     comment.getPCommentNum(), comment.getStep());
    log.info("코멘트 : {}",comment);
    log.info("과연 : {}",maxCommentOrderInSameParent);
    //만약 은 부모 댓글을 가진 동일 step 중 최고 순서가 null이라면 부모 댓글 순서 반환
    if (maxCommentOrderInSameParent == null) {
      maxCommentOrderInSameParent = find(comment.getPCommentNum()).get().getCommentOrder();
    }

    return maxCommentOrderInSameParent;

  }

  //댓글 순서 변경
  private void changeCommentOrder(Comment comment, Long commentOrder){
    StringBuffer sql = new StringBuffer();

    sql.append("update comments ");
    sql.append("set comment_order = comment_order + 1 ");
    sql.append("where article_num =? and comment_group = ? ");
    sql.append("and comment_order > ? ");

    jt.update(sql.toString(),comment.getArticleNum(), comment.getCommentGroup(), commentOrder);
  }


  /**
   * 댓글 수정
   *
   * @param commentNum 댓글 번호
   * @param comment    댓글 내용
   * @return
   */
  @Override
  public int update(Long commentNum, Comment comment) {
    StringBuffer sql = new StringBuffer();

    sql.append("update comments ");
    sql.append("set comment_contents = ?, create_date = sysdate ");
    sql.append("where comment_num = ? ");

    int affectedRow = jt.update(sql.toString(),
        comment.getCommentContents(),
        commentNum);

    return affectedRow;
  }

  /**
   * 댓글 삭제
   *
   * @param commentNum 댓글 번호
   * @return 댓글 내용
   */
  @Override
  public int delete(Long commentNum) {
    String sql = "delete from comments where comment_num = ? ";

    int affectedRow = jt.update(sql, commentNum);

    return affectedRow;
  }

  /**
   * 신규 댓글 번호 생성
   *
   * @return 댓글 번호
   */
  @Override
  public Long generatedCommentNum() {
    String sql = "select comments_comment_num_seq.nextval from dual ";
    Long commentNum = jt.queryForObject(sql, Long.class);
    return commentNum;
  }

  /**
   * 신규 댓글 그룹 번호 생성
   *
   * @return 댓글 그룹 번호
   */
  @Override
  public Long generatedCommentGroupNum() {
    String sql = "select comments_comment_num_seq.currval from dual ";
    Long commentGroupNum = jt.queryForObject(sql, Long.class);
    return commentGroupNum;
  }

  /**
   * 게시물 댓글 건수 조회
   *
   * @param articleNum 게시글 번호
   * @return 댓글 건수
   */
  @Override
  public int totalCountOfArticle(Long articleNum) {

    String sql = "select count(*) from comments where article_num = ? ";
    Integer cntPerArticle = jt.queryForObject(sql, Integer.class, articleNum);
    return cntPerArticle;
  }

  /**
   * 자식 댓글 수 조회
   *
   * @param commentNum 삭제할 댓글 번호
   * @return 자식 댓글 수
   */
  @Override
  public int countOfChildrenComments(Long commentNum) {
    String sql = "select count(*) from comments where p_comment_num = ? ";
    //List<Comment> foundChildrenComments = jt.query(sql, new BeanPropertyRowMapper<>(Comment.class), commentNum);
    Integer cntOfChildrenComments = jt.queryForObject(sql, Integer.class, commentNum);
    return cntOfChildrenComments;
  }

  /**
   * 자식 댓글이 있는 댓글 삭제 처리
   *
   * @param commentNum 삭제할 댓글 번호
   * @return
   */
  @Override
  public int updateToDeletedComment(Long commentNum) {

    StringBuffer sql = new StringBuffer();

    sql.append("update comments ");
    sql.append("set comment_contents = '!DELETEDCOMMENT!', create_date = sysdate ");
    sql.append("where comment_num = ? ");

    int affectedRow = jt.update(sql.toString(), commentNum);

    return affectedRow;
  }
}