package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(val memberRepository: MemberRepository) {

    //회원 가입
    @Transactional
    fun join(member: Member): Long? {
        validateDuplicateMember(member) //중복 회원 검증
        memberRepository.save(member)
        return member.id
    }

    private fun validateDuplicateMember(member: Member) {
        //EXCEPTION
        val findMembers:List<Member> = memberRepository.findByName(member.name)
        if (findMembers.isNotEmpty()){
            throw IllegalStateException("이미 존재하는 회원입니다.")
        }
    }

    //회원 전체 조회
    fun findMembers():List<Member> {
        return memberRepository.findAll()
    }

    fun findOne(id: Long):Member {
        return memberRepository.findOne(id)
    }



}