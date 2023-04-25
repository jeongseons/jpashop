package com.jpabook.jpashop.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue
    @Column(name = "order_id")
    var id:Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderItems:List<OrderItem> = ArrayList(),

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name="delivery_id")
    var delivery:Delivery,

    var orderDate:LocalDateTime,

    @Enumerated(EnumType.STRING)
    var status:OrderStatus

) {
    //==연관관계 메서드==//
    fun setMember(member: Member) {
        this.member = member
        member.orders.plus(this)
    }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.plus(orderItem)
        orderItem.order = this
    }

    fun setDelivery(delivery: Delivery) {
        this.delivery = delivery
        delivery.order = this
    }

}