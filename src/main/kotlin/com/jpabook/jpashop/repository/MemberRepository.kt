package com.jpabook.jpashop.repository

import com.jpabook.jpashop.domain.Member
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class MemberRepository(@PersistenceContext val em:EntityManager) {

    fun save(member:Member) {
        em.persist(member)
    }

    //단건 조회
    fun findOne(id:Long):Member {
        return em.find(Member::class.java, id)
    }

    //전체 조회
    fun findAll():List<Member> {
        return em.createQuery("SELECT M FROM Member M", Member::class.java)
            .resultList
    }

    //이름별 조회
    fun findByName(name:String):List<Member>{
        return em.createQuery("select m from Member m where m.name = :name", Member::class.java)
            .setParameter("name",name)
            .resultList
    }



}