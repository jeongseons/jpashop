package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.repository.MemberRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import kotlin.IllegalStateException

@SpringBootTest
@Transactional //테스트에 있을 시 기본적으로 롤백 실행
class MemberServiceTest{

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var em:EntityManager

    @Test
    @DisplayName("회원가입")
    fun joinTest() {
        //given
        val member = Member()
        member.name = "KIM"

        //when
        val saveId = memberService.join(member)

        //then
        em.flush()
        assertEquals(member, memberRepository.findOne(saveId!!))
    }

    @Test()
    @DisplayName("중복회원예외")
    fun duplicateMemberTest() {
        //given
        val member1 = Member()
        member1.name = "KIM"

        val member2 = Member()
        member2.name = "KIM"

        //when
        memberService.join(member1)
        try {
            memberService.join(member2) // 예외 발생
        } catch (e:IllegalStateException) {
            return
        }

        assertThrows(IllegalStateException::class.java) {
            memberService.join(member2) }

        //then
        fail("예외가 발생해야 한다")
    }

}