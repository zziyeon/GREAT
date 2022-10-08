package com.kh.great.domain.dao.article;

import com.kh.great.domain.dao.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ArticleDAOImpl implements ArticleDAO {

  private final JdbcTemplate jt;

  /**
   * 게시글 목록 조회1 : 전체
   *
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select article_num, article_category, article_title, attachment, mem_nickname, create_date, views, comments ");
    sql.append("from article a, member m ");
    sql.append("where a.mem_number = m.mem_number ");
    sql.append("order by a.article_num desc ");

    List<Article> articles = jt.query(sql.toString(), new RowMapper<Article>() {
      @Override
      public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
        Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs, rowNum);
        article.setMember(member);
        return article;
      }
    });

    return articles;
  }

  /**
   * 게시글 목록 조회2 : 카테고리
   *
   * @param category 카테고리
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findAll(String category) {

    StringBuffer sql = new StringBuffer();
    sql.append("select article_num, article_category, article_title, attachment, mem_nickname, create_date, views, comments ");
    sql.append("from article a, member m ");
    sql.append("where a.mem_number = m.mem_number and a.article_category = ? ");
    sql.append("order by a.article_num desc ");

    List<Article> articles = jt.query(sql.toString(), new RowMapper<Article>() {
      @Override
      public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
        Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs, rowNum);
        article.setMember(member);
        return article;
      }
    }, category);

    return articles;
  }

  /**
   * 게시글 목록 조회3 : 페이지
   *
   * @param startRec 첫 페이지
   * @param endRec   마지막 페이지
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findAll(int startRec, int endRec) {
    StringBuffer sql = new StringBuffer();

    sql.append("select t1.* ");
    sql.append("from (select row_number() over (order by a.article_num desc) no, article_num, article_category, ");
    sql.append("article_title, attachment, mem_nickname, create_date, views, comments ");
    sql.append("from article a, member m where a.mem_number = m.mem_number) t1 ");
    sql.append("where t1.no between ? and ? ");

    List<Article> articles = jt.query(sql.toString(), new RowMapper<Article>() {
      @Override
      public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
        Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs, rowNum);
        article.setMember(member);
        return article;
      }
    }, startRec, endRec);

    return articles;
  }

  /**
   * 게시글 목록 조회4 : 카테고리, 페이지
   *
   * @param category 카테고리
   * @param startRec 첫 페이지
   * @param endRec   마지막 페이지
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findAll(String category, int startRec, int endRec) {
    StringBuffer sql = new StringBuffer();

    sql.append("select t1.* ");
    sql.append("from (select row_number() over (order by a.article_num desc) no, article_num, article_category, ");
    sql.append("article_title, attachment, mem_nickname, create_date, views, comments ");
    sql.append("from article a, member m where a.mem_number = m.mem_number and a.article_category = ?) t1 ");
    sql.append("where t1.no between ? and ? ");

    List<Article> articles = jt.query(sql.toString(), new RowMapper<Article>() {
      @Override
      public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
        Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs, rowNum);
        article.setMember(member);
        return article;
      }
    }, category, startRec, endRec);

    return articles;
  }

  /**
   * 게시글 목록 조회5 : 검색
   *
   * @param filterCondition 분류, 시작 레코드 번호, 종료 레코드 번호, 검색 유형, 검색어
   * @return
   */
  @Override
  public List<Article> findAll(ArticleFilterCondition filterCondition) {
    StringBuffer sql = new StringBuffer();

    sql.append("select t1.* ");
    sql.append("from (select row_number() over (order by a.article_num desc) no, article_num, article_category, ");
    sql.append("article_title, article_contents, attachment, mem_nickname, create_date, views, comments ");
    sql.append("from article a, member m where a.mem_number = m.mem_number and ");

    //분류
    sql = dynamicQuery(filterCondition, sql);

    sql.append(") t1 ");
    sql.append("where t1.no between ? and ? ");

    List<Article> list = null;

    //게시판 전체
    if (StringUtils.isEmpty(filterCondition.getCategory())) {
      log.info("1");
      list = jt.query(sql.toString(), new RowMapper<Article>() {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
          Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
          Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs, rowNum);
          article.setMember(member);
          return article;
        }
      }, filterCondition.getStartRec(), filterCondition.getEndRec());

      //게시판 분류
    } else {
      log.info("2");
      list = jt.query(sql.toString(), new RowMapper<Article>() {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
          Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
          Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs, rowNum);
          article.setMember(member);
          return article;
        }
      }, filterCondition.getCategory(), filterCondition.getStartRec(), filterCondition.getEndRec());
    }

    return list;
  }


  /**
   * 게시글 조회
   *
   * @param articleNum 게시글 번호
   * @return 게시글
   */
  @Override
  public Optional<Article> read(Long articleNum) {
    StringBuffer sql = new StringBuffer();
    sql.append("select article_num, article_category, article_title, article_contents, attachment, a.mem_number, mem_nickname, create_date, views, comments ");
    sql.append("from article a, member m ");
    sql.append("where a.mem_number = m.mem_number and a.article_num = ? ");


    try {
      Article article = jt.queryForObject(sql.toString(), new RowMapper<Article>() {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
          Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
          Article article = (new BeanPropertyRowMapper<>(Article.class)).mapRow(rs, rowNum);
          article.setMember(member);
          return article;
        }
      }, articleNum);
      return Optional.of(article);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  /**
   * 게시글 작성
   *
   * @param article 게시글 작성 내용
   * @return 게시글
   */
  @Override
  public int save(Article article) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into article (article_num, mem_number, article_category, article_title, article_contents, attachment, create_date, views, comments) ");
    sql.append("values (?,?,?,?,?,?,sysdate,0,0) ");

    log.info("아티클 넘버 : {}",article.getArticleNum());

    int affectedRow = jt.update(sql.toString(),
        article.getArticleNum(),
        article.getMemNumber(),
        article.getArticleCategory(),
        article.getArticleTitle(),
        article.getArticleContents(),
        article.getAttachment());

    return affectedRow;
  }

  /**
   * 게시글 수정
   *
   * @param articleNum 게시글 번호
   * @param article    게시글 수정 내용
   */
  @Override
  public int update(Long articleNum, Article article) {
    StringBuffer sql = new StringBuffer();
    sql.append("update article ");
    sql.append("set article_category = ?, article_title = ?, ");
    sql.append("article_contents = ?, attachment = ? ");
    sql.append("where article_num = ? ");

    int affectedRow = jt.update(sql.toString(), article.getArticleCategory(), article.getArticleTitle(), article.getArticleContents(), article.getAttachment(), articleNum);
    return affectedRow;
  }

  /**
   * 게시글 삭제
   */
  @Override
  public int delete(Long articleNum) {
    String sql = "delete from article where article_num = ? ";
    int affectedRow = jt.update(sql, articleNum);
    return affectedRow;
  }

  /**
   * 신규 게시물 번호 생성
   *
   * @return
   */
  @Override
  public Long generatedArticleNum() {
    String sql = "select article_article_num_seq.nextval from dual ";
    Long articleNum = jt.queryForObject(sql, Long.class);
    return articleNum;
  }

  /**
   * 조회수 증가
   *
   * @param articleNum 게시글 번호
   * @return 수정건수
   */
  @Override
  public int increaseViewCount(Long articleNum) {
    String sql = "update article set views = views +1 where article_num = ? ";
    int affectedRow = jt.update(sql, articleNum);
    return affectedRow;
  }

  /**
   * 게시물의 댓글 수 업데이트
   *
   * @param articleNum          게시물 번호
   * @param totalCountOfArticle 댓글 수
   * @return 수정건수
   */
  @Override
  public int updateCommentsCnt(Long totalCountOfArticle,Long articleNum) {
    String sql = "update article set comments = ? where article_num = ? ";
    int affectedRow = jt.update(sql,totalCountOfArticle,articleNum);
    return affectedRow;
  }

  /**
   * 전체 건수
   *
   * @return 게시글 전체 건수
   */
  @Override
  public int totalCount() {

    String sql = "select count(*) from article ";

    Integer cnt = jt.queryForObject(sql, Integer.class);

    return cnt;
  }

  /**
   * 전체 건수
   *
   * @param category 게시글 유형
   * @return 게시글 전체 건수
   */
  @Override
  public int totalCount(String category) {

    String sql = "select count(*) from article where article_category = ? ";

    Integer cnt = jt.queryForObject(sql, Integer.class, category);

    return cnt;
  }

  /**
   * 전체 건수
   *
   * @param filterCondition 필터 조건
   * @return 게시글 전체 건수
   */
  @Override
  public int totalCount(ArticleFilterCondition filterCondition) {

    StringBuffer sql = new StringBuffer();

    sql.append("select count(*) ");
    sql.append("  from article a, member m where a.mem_number = m.mem_number and ");

    sql = dynamicQuery(filterCondition, sql);

    Integer cnt = 0;
    //게시판 전체 검색 건수
    if (StringUtils.isEmpty(filterCondition.getCategory())) {
      cnt = jt.queryForObject(
          sql.toString(), Integer.class
      );
      //게시판 분류별 검색 건수
    } else {
      cnt = jt.queryForObject(
          sql.toString(), Integer.class,
          filterCondition.getCategory()
      );
    }

    return cnt;
  }

  /**
   * 동적 쿼리
   *
   * @param filterCondition
   * @param sql
   * @return
   */
  private StringBuffer dynamicQuery(ArticleFilterCondition filterCondition, StringBuffer sql) {
    //분류
    if (StringUtils.isEmpty(filterCondition.getCategory())) {

    } else {
      sql.append("       a.article_category = ? ");
    }

    //분류, 검색 유형,검색어 존재
    if (!StringUtils.isEmpty(filterCondition.getCategory()) &&
        !StringUtils.isEmpty(filterCondition.getSearchType()) &&
        !StringUtils.isEmpty(filterCondition.getKeyword())) {

      sql.append(" AND ");
    }

    //검색유형
    switch (filterCondition.getSearchType()) {
      case "title":           //제목
        sql.append("      a.article_title like '%" + filterCondition.getKeyword() + "%' ");
        break;
      case "contents":        //내용
        sql.append("       a.article_contents like '%" + filterCondition.getKeyword() + "%' ");
        break;
      case "titleOrContents": //제목 또는 내용
        sql.append("     (  a.article_title like '%" + filterCondition.getKeyword() + "%' ");
        sql.append("    or a.article_contents like '%" + filterCondition.getKeyword() + "%' )");
        break;
      case "nickname":        //넥네임
        sql.append("       m.mem_nickname like '%" + filterCondition.getKeyword() + "%' ");
        break;
      default:
    }
    return sql;
  }
}
