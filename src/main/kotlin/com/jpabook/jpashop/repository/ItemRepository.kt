package com.jpabook.jpashop.repository

import com.jpabook.jpashop.domain.item.Item
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class ItemRepository (@PersistenceContext val em:EntityManager) {

    fun save(item: Item) {
        if(item.id==null){
            em.persist(item) //아이디값이 없을때 - 신규 객체 등록
        }else{
            em.merge(item) //아이디가 있을 때 - 이미 db에 등록된 것 가져와 업데이트하는 걸로 간주
        }
    }

    fun findOne(id:Long):Item {
        return em.find(Item::class.java, id)
    }

    fun findAll():List<Item> {
        return em.createQuery("select i from Item i", Item::class.java)
            .resultList
    }

}