package com.kh.great.domain.svc.member;

import com.kh.great.domain.Member;
import com.kh.great.domain.dao.member.MemberDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC {
    private final MemberDAO memberDAO;

    /**
     * 회원가입
     *
     * @param member 가입정보
     * @return 회원아이디
     */
    @Override
    public Member join(Member member) {

        //회원 아이디 생성
        int generateMemberNumber = memberDAO.generateMemberNumber();
        member.setMemNumber(generateMemberNumber);
        memberDAO.join(member);
        return memberDAO.findById(generateMemberNumber);
    }

    /**
     * 조회 by 회원아이디
     *
     * @param memNumber 회원아이디
     * @return 회원정보
     */
    @Override
    public Member findById(int memNumber) {
        return memberDAO.findById(memNumber);
    }

    /**
     * 수정
     *
     * @param memNumber 아이디
     * @param member   수정할 정보
     * @return 수정건수
     */
    @Override
    public int update(int memNumber, Member member) {
        int cnt = memberDAO.update(memNumber, member);
        log.info("수정건수={}", cnt);
        return cnt;
    }

    /**
     * 탈퇴
     *
     * @param memNumber 회원아이디
     * @return 삭제건수
     */
    @Override
    public int delete(int memNumber) {
        int cnt = memberDAO.delete(memNumber);
        log.info("삭제건수={}", cnt);
        return cnt;
    }

    /**
     * 목록
     *
     * @return 회원전체
     */
    @Override
    public List<Member> all() {
        return memberDAO.all();
    }

    /**
     * 이메일 중복체크
     *
     * @param email 이메일
     * @return 존재하면 true
     */
    @Override
    public Boolean dupChkOfMemberEmail(String email) {

        return memberDAO.dupChkOfMemberEmail(email);
    }
}
