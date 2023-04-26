package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.Delivery
import com.jpabook.jpashop.domain.DeliveryStatus
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
    fun order(memberId: Long?, itemId: Long?, count:Int): Long? {

        //엔티티 조회
        val member = memberRepository.findOne(memberId!!)
        val item = itemRepository.findOne(itemId!!)

        //배송정보 생성
        var delivery = Delivery(0, null, member.address!!, DeliveryStatus.READY)

        //주문상품 생성
        val orderItem = OrderItem.createOrderItem(item, item.price, count)

        //주문 생성
        val order = Order.createOrder(member, delivery, orderItem)

        //주문 저장
        orderRepository.save(order)

        return order.id
    }

    //주문 취소
    @Transactional
    fun cancelOrder(orderId: Long) {
        //주문 엔티티 조회
        val order = orderRepository.findOne(orderId)
        //주문 취소
        order.cancel()
    }

    //검색
//    fun findOrders(orderSearch:OrderSearch):List<Order> {
//        return orderRepository.findAll(orderSearch)
//    }

}