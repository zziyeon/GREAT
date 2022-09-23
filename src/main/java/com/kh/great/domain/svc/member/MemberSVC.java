package com.kh.great.domain.svc.member;

import com.kh.great.domain.Member;

import java.util.List;

public interface MemberSVC {
    /**
     * 회원가입
     * @param member 가입정보
     * @return 회원아이디
     */
    Member join(Member member);

    /**
     * 조회 by 회원아이디
     * @param memNumber 회원아이디
     * @return 회원정보
     */
    Member findById(int memNumber);

    /**
     * 수정
     * @param memNumber 회원아이디
     * @param member  수정할 정보
     * @return 수정건수
     */
    int update(int memNumber, Member member);

    /**
     * 탈퇴
     * @param memNumber 회원아이디
     * @return 삭제건수
     */
    int delete(int memNumber);

    /**
     * 목록
     * @return 회원전체
     */
    List<Member> all();

    /**
     * 이메일 중복체크
     * @param email 이메일
     * @return 존재하면 true
     */
    Boolean dupChkOfMemberEmail(String email);
}
