package com.kh.great.domain.dao.notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NoticeDAOImpl implements NoticeDAO {

  private final JdbcTemplate jt;


  /**
   * 게시글 목록 조회1 : 전체
   *
   * @return 게시글 리스트
   */
  @Override
  public List<Notice> foundAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT ");
    sql.append("  noticeid, ");
    //.append("  bcategory, ");
    sql.append("  title, ");
    sql.append("  count, ");
    sql.append("  content, ");
    sql.append("  udate ");
    sql.append("FROM ");
    sql.append("  notice ");
    sql.append("Order by notice_id desc");

    List<Notice> list = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Notice.class));

    return list;
  }

//  @Override
//  public List<Notice> findAll(String category) {
//    StringBuffer sql = new StringBuffer();
//    sql.append("SELECT ");
//    sql.append("  noticeid, ");
//    //sql.append("  bcategory, ");
//    sql.append("  title, ");
//    sql.append("  count, ");
//    sql.append("  content, ");
//    sql.append("  udate ");
//    sql.append("FROM ");
//    sql.append("  notice ");
//    //sql.append("WHERE bcategory = ? ");
//    sql.append("Order by notice_id desc");
//
//    List<Notice> list = jt.query(sql.toString(), new BeanPropertyRowMapper<>(Notice.class),category);
//
//    return list;
//  }

  /**
   * 게시글 목록 조회3 : 페이지
   *
   * @param startRec 첫 페이지
   * @param endRec   마지막 페이지
   * @return 게시글 리스트
   */
  @Override
  public List<Notice> findAll(int startRec, int endRec) {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append("    SELECT ");
    sql.append("    ROW_NUMBER() OVER (ORDER BY notice_id DESC) no, ");
    sql.append("    notice_id, ");
    sql.append("    title, ");
    sql.append("    count, ");
    sql.append("    content, ");
    sql.append("    udate ");
    sql.append("    FROM notice) t1 ");
    sql.append("where t1.no between ? and ? ");

    List<Notice> list = jt.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(Notice.class),
        startRec, endRec
    );
    return list;
  }

  /**
   * 검색
   *
   * @param filterCondition 분류,시작레코드번호,종료레코드번호,검색유형,검색어
   * @return
   */
  @Override
  public List<Notice> findAll(BbsFilterCondition filterCondition) {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append("    SELECT  ROW_NUMBER() OVER (ORDER BY notice_id DESC) no, ");
    sql.append("            notice_id, ");
    sql.append("            title, ");
    sql.append("            count, ");
    sql.append("            content, ");
    sql.append("            udate ");
    sql.append("      FROM notice ");

    //분류
    sql = dynamicQuery(filterCondition, sql);

    sql.append(") t1 ");
    sql.append("where t1.no between ? and ? ");


    List<Notice> list = null;

    //게시판 전체
    list = jt.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(Notice.class),
        filterCondition.getStartRec(),
        filterCondition.getEndRec()
    );

    return list;
  }

  /**
   * 등록
   * @param notice
   * @return
   */
  @Override
  public Notice save(Notice notice) {

    StringBuffer sql = new StringBuffer();
    sql.append("insert into notice(notice_id, title, content,write, count,attachments, udate) ");
    sql.append("values(notice_notice_id_seq.nextval,?,?,'관리자', 0,?,sysdate) ");

    //SQL실행
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jt.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"notice_id"}  // insert 후 insert 레코드중 반환할 컬럼명, KeyHolder에 저장됨.
        );

        pstmt.setString(1, notice.getTitle());
        pstmt.setString(2, notice.getContent());
        pstmt.setString(3, notice.getWrite());
        pstmt.setString(4,notice.getAttachments());


        return pstmt;
      }
    },keyHolder);

    long notice_id = Long.valueOf(keyHolder.getKeys().get("notice_id").toString());
    return read(notice_id);
  }

  /**
   *전체조회
   * @return
   */
  @Override
  public List<Notice> selectAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select notice_id, title, content, write, attachments, count, udate ");
    sql.append("  from notice ");
    sql.append("order by notice_id desc ");

    List<Notice> list = jt.query(
        sql.toString(), new BeanPropertyRowMapper<>(Notice.class));

    return list;
  }

  /**
   * 상세조회
   * @param noticeId
   * @return
   */
  @Override
  public Notice read(Long noticeId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select notice_id, title,content,attachments,write, count,  udate ");
    sql.append("from notice ");
    sql.append("where notice_id = ? ");

    Notice notice = null;
    try {
      notice = jt.queryForObject(
          sql.toString(), new BeanPropertyRowMapper<>(Notice.class), noticeId);
    } catch (EmptyResultDataAccessException e) {
      log.info("삭제대상이 없습니다 공지사항넘버={}", noticeId);
    }
    return notice;
  }

  /**
   * 수정
   * @param notice
   * @return
   */
  @Override
  public int update(Long noticeId, Notice notice) {
    StringBuffer sql = new StringBuffer();
    sql.append("update notice ");
    sql.append("set title = ? , ");
    sql.append("    content = ? , ");
    sql.append("    attachments = ?, ");
    sql.append("    udate   = sysdate ");
    sql.append("where notice_id = ? ");

    int affectedRow = jt.update(sql.toString(), notice.getTitle(), notice.getContent(), notice.getAttachments(), noticeId);

    return affectedRow;
  }

  /**
   * 삭제
   * @param noticeId
   * @return
   */
  @Override
  public int delete(Long noticeId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from notice ");
    sql.append(" where notice_id = ? ");

    int cnt = jt.update(sql.toString(), noticeId);

    return cnt;
  }

  /**
   * 조회수 증가
   *
   * @param noticeId 게시글 번호
   * @return 수정건수
   */
  @Override
  public int increaseViewCount(Long noticeId) {
    String sql = "update notice set count = count +1 where notice_id = ? ";
    int affectedRow = jt.update(sql, noticeId);
    return affectedRow;
  }

  /**
   *전체건수
   * @return
   */
  @Override
  public int totalCount() {
    String sql = "select count(*) from notice";

    Integer cnt = jt.queryForObject(sql, Integer.class);

    return cnt;
  }


  /**
   *
   * @param filterCondition
   * @return
   */
  @Override
  public int totalCount(BbsFilterCondition filterCondition) {
    StringBuffer sql = new StringBuffer();

    sql.append("select count(*) ");
    sql.append("  from notice  ");

    sql = dynamicQuery(filterCondition, sql);

    Integer cnt = 0;
    //게시판 전체 검색 건수
    cnt = jt.queryForObject(sql.toString(), Integer.class);

    return cnt;
  }
  private StringBuffer dynamicQuery(BbsFilterCondition filterCondition, StringBuffer sql) {

    //분류,검색유형,검색어 존재
    if(!StringUtils.isEmpty(filterCondition.getSearchType()) && !StringUtils.isEmpty(filterCondition.getKeyword())){
      sql.append(" where ");
    }else {
      return sql;
    }

    //검색유형
    switch (filterCondition.getSearchType()){
      case "TC":  //제목 + 내용
        sql.append("    (  title    like '%"+ filterCondition.getKeyword()+"%' ");
        sql.append("    or content like '%"+ filterCondition.getKeyword()+"%' )");
        break;
      case "T":   //제목
        sql.append("       title    like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      case "C":   //내용
        sql.append("       content like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      default:
    }
    return sql;
  }
}