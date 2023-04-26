package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.Delivery
import com.jpabook.jpashop.domain.Order
import com.jpabook.jpashop.domain.OrderItem
import com.jpabook.jpashop.repository.ItemRepository
import com.jpabook.jpashop.repository.MemberRepository
import com.jpabook.jpashop.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService @Autowired constructor(val orderRepository: OrderRepository,
                                          val memberRepository: MemberRepository,
                                          val itemRepository: ItemRepository) {

    //주문
    @Transactional
    fun order(memberId:Long, itemId:Long, count:Int):Long {

        //엔티티 조회
        val member = memberRepository.findOne(memberId)
        val item = itemRepository.findOne(itemId)

        //배송정보 생성
        lateinit var delivery: Delivery
        delivery.address = member.address

        //주문상품 생성
        val orderItem = OrderItem.createOrderItem(item, item.price, count)

        //주문 생성
        val order = Order.createOrder(member, delivery, orderItem)

        //주문 저장
        orderRepository.save(order)

        return order.id
    }


    //취소

    //검색

}