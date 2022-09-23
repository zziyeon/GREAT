package com.kh.great.domain.dao.member;

import com.kh.great.domain.Member;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class MemberDAOImpl implements MemberDAO {
    private final JdbcTemplate jt;

    /**
     * 신규 회원아이디(고객회원) 생성
     * @return 회원아이디
     */
    public int generateMemberNumber(){
        String sql = "select mem_num.nextval from dual ";
        int memNumber = jt.queryForObject(sql, int.class);
        return memNumber;
    }

    /**
     * 회원가입
     *
     * @param member 가입정보
     * @return 회원아이디
     */
    @Override
    public int join(Member member) {
        int result = 0;
        StringBuffer sql = new StringBuffer();
        sql.append("insert into member (mem_number, mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email, ");
        sql.append(" mem_businessnumber, mem_store_name, mem_store_phonenumber, mem_store_location, mem_store_introduce, mem_store_sns) ");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ");
        sql.append(" ?, ?, ?, ?, ?, ?) ");

        result = jt.update(sql.toString(), member.getMemNumber(), member.getMemType(), member.getMemId(), member.getMemPassword(), member.getMemName(), member.getMemNickname(), member.getMemEmail(),
                member.getMemBusinessnumber(), member.getMemStoreName(), member.getMemStorePhonenumber(), member.getMemStoreLocation(), member.getMemStoreIntroduce(), member.getMemStoreSns());

        return result;
    }

    /**
     * 조회 by 회원아이디
     *
     * @param memNumber 회원아이디
     * @return 회원정보
     */
    @Override
    public Member findById(int memNumber) {
        StringBuffer sql = new StringBuffer();

        sql.append("select mem_number, mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email, ");
        sql.append("mem_businessnumber, mem_store_name, mem_store_phonenumber, mem_store_location, mem_store_latitude, mem_store_longitude, ");
        sql.append("mem_store_introduce, mem_store_sns, mem_regtime, mem_lock_expiration, mem_admin ");
        sql.append("  from member ");
        sql.append(" where mem_number = ? ");

        Member findedMember = null;
        try {
            //BeanPropertyRowMapper는 매핑되는 자바클래스에 디폴트 생성자 필수!
            findedMember = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Member.class), memNumber);
        } catch (DataAccessException e) {
            log.info("찾고자하는 회원이 없습니다!={}", memNumber);
        }

        return findedMember;
    }

    /**
     * 수정
     *
     * @param memNumber 회원아이디
     * @param member   수정할 정보
     */
    @Override
    public int update(int memNumber, Member member) {
        int result = 0;
        StringBuffer sql = new StringBuffer();
        sql.append("update member ");
        sql.append("   set nickname = ?, ");
        sql.append("       pw = ?, ");
        sql.append("       udate = systimestamp ");
        sql.append(" where member_id = ? ");

        result = jt.update(sql.toString(), member.getMemNickname(),  member.getMemPassword(), memNumber);
        return result;
    }

    /**
     * 탈퇴
     *
     * @param memNumber 회원아이디
     */
    @Override
    public int delete(int memNumber) {
        int result = 0;
        String sql = "delete from member where member_id = ? ";

        result = jt.update(sql, memNumber);
        return result;
    }

    /**
     * 목록
     *
     * @return 회원전체
     */
    @Override
    public List<Member> all() {

        StringBuffer sql = new StringBuffer();
        sql.append("select member_id, email, pw, nickname, cdate, udate ");
        sql.append("  from member ");

        return jt.query(sql.toString(), new BeanPropertyRowMapper<>(Member.class));
    }

    /**
     * 이메일 중복체크
     *
     * @param email 이메일
     * @return 존재하면 true
     */
    @Override
    public Boolean dupChkOfMemberEmail(String email) {
        String sql = "select count(email) from member where email = ? ";
        Integer rowCount = jt.queryForObject(sql, Integer.class, email);
        return rowCount == 1 ? true : false;
    }
}
