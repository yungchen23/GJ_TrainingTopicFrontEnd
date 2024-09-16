package com.restfront.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class MemberService {

    @Autowired
    private LoginForMemberRepository loginForMemberRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void registerMember(RegistrationForm registrationForm) {
        // 1. 保存 LoginForMember 實體
        LoginForMember loginForMember = new LoginForMember();
        loginForMember.setLogin_account(registrationForm.getLogin_account());
        loginForMember.setLogin_password(registrationForm.getLogin_password());
        loginForMember.setLogin_question(registrationForm.getLogin_question());
        loginForMember.setLogin_answer(registrationForm.getLogin_answer());
        loginForMember.setMember_state(true); // 設置為有效
        LoginForMember savedLoginForMember = loginForMemberRepository.save(loginForMember);

        // 2. 保存 Member 實體
        Member member = new Member();
        member.setMember_phone(registrationForm.getMember_phone());
        member.setMember_mail(loginForMember.getLogin_account());

        // 3. 設置 LoginForMember 並保存 Member
        member.setLoginformember(savedLoginForMember);  // 將 LoginForMember 與 Member 關聯
        memberRepository.save(member);
    }
}
