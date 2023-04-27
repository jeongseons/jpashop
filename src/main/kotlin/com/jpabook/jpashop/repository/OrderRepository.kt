package com.jpabook.jpashop.repository

import com.jpabook.jpashop.domain.Order
import com.jpabook.jpashop.domain.OrderStatus
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils
import java.util.Objects
import javax.persistence.EntityManager
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Predicate

@Repository
class OrderRepository(val em:EntityManager) {

    fun save(order: Order){
        em.persist(order)
    }

    fun findOne(id:Long):Order {
        return em.find(Order::class.java, id)
    }

    fun findAllByString(orderSearch:OrderSearch):List<Order> {
        //language=JPAQL
        var jpql = "select o From Order o join o.member m"
        var isFirstCondition = true

        //주문 상태 검색
        if (orderSearch.orderStatus != null) {
            if (isFirstCondition) {
                jpql += " where"
                isFirstCondition = false
            } else {
                jpql += " and"
            }
            jpql += " o.status = :status"
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.memberName)) {
            if (isFirstCondition) {
                jpql += " where"
                isFirstCondition = false
            } else {
                jpql += " and"
            }
            jpql += " m.name like :name"
        }

        var query = em.createQuery(jpql, Order::class.java)
            .setMaxResults(1000) //최대 1000건

        if (orderSearch.orderStatus != null) {
            query = query.setParameter("status", orderSearch.orderStatus)
        }

        if (StringUtils.hasText(orderSearch.memberName)) {
            query = query.setParameter("name", orderSearch.memberName)
        }

        return query.resultList
    }

    fun findAllByCriteria(orderSearch: OrderSearch): MutableList<Order.Companion>? {
        val cb = em.criteriaBuilder
        val cq = cb.createQuery(Order.javaClass)
        val o = cq.from(Order.javaClass)
        var m = o.join<Objects, Objects>("member", JoinType.INNER)

        val criteria:List<Predicate> = ArrayList()

        //주문 상태 검색
        if(orderSearch.orderStatus != null) {
            val status:Predicate = cb.equal(o.get<OrderStatus>("status"),orderSearch.orderStatus)
            criteria.plus(status)
        }

        //회원 이름 검색
        if(StringUtils.hasText(orderSearch.memberName)){
            val name:Predicate = cb.like(m.get("name"), "%" + orderSearch.memberName + "%")
            criteria.plus(name)
        }

        cq.where(cb.and(*criteria.toTypedArray()))

        val query = em.createQuery(cq).setMaxResults(1000) //최대 1000건

        return query.resultList

    }


}

