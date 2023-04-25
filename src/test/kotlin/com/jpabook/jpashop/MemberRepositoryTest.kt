package com.jpabook.jpashop

import com.jpabook.jpashop.domain.Member
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class MemberRepositoryTest{

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    @DisplayName("테스트1")
    @Transactional //test이후에 롤백
    @Rollback(false)
    fun testMember(){
        //given
        val member: Member = Member()
        member.name = "memberA"
        memberRepository.save(member)

        //when
        val saveId = memberRepository.save(member)
        val findMember = memberRepository.find(saveId)

        //then
        Assertions.assertThat(findMember.id).isEqualTo(member.id)
        Assertions.assertThat(findMember.name).isEqualTo(member.name)
        Assertions.assertThat(findMember).isEqualTo(member) //JPA 엔티티 동일성 보장
        print("findMember == member: " + {findMember == member})
    }

}