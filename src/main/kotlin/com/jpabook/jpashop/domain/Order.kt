package com.jpabook.jpashop.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue
    @Column(name = "order_id")
    var id:Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderItems:List<OrderItem>? = ArrayList(),

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="delivery_id")
    var delivery:Delivery? = null,

    var orderDate:LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var status:OrderStatus = OrderStatus.ORDER

) {

    //==연관관계 메서드==//
    @JvmName("setMember2")
    fun setMember(member: Member) {
        this.member = member
        member.orders.plus(this)
    }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems?.plus(orderItem)
        orderItem.order = this
    }

    @JvmName("setDelivery2")
    fun setDelivery(delivery: Delivery) {
        this.delivery = delivery
        delivery.order = this
    }

    //==생성 메서드==//
    companion object {
        @JvmStatic
        fun createOrder(
            member: Member, delivery: Delivery,
            vararg orderItems: OrderItem
        ): Order {
            var order = Order()
            order.member = member
            order.delivery = delivery
            for (orderItem in orderItems) {
                order.addOrderItem(orderItem!!)
            }
            order.status = OrderStatus.ORDER
            order.orderDate = LocalDateTime.now()
            return order
        }
    }

    //==비즈니스 로직==//
    //주문 취소
    fun cancel() {
        if (delivery!!.status == DeliveryStatus.COMP) {
            throw IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.")
        }

        this.status = OrderStatus.CANCEL
        for (orderItem:OrderItem in orderItems!!) {
            orderItem.cancel()
        }
    }

    //==조회 로직--//
    //전체 주문 가격 조회
    fun getTotalPrice():Int {
        var totalPrice = 0
        for (orderItem:OrderItem in orderItems!!) {
            totalPrice += orderItem.getTotalPrice()
        }
        return totalPrice

//        return orderItems.stream()
//            .mapToInt(OrderItem::getTotalPrice)
//            .sum()
    }



}


