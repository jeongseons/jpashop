package com.jpabook.jpashop.domain

import com.jpabook.jpashop.domain.item.Item
import javax.persistence.*

@Entity
data class OrderItem(
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    var id:Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order,

    var orderPrice:Int = 0, //주문 가격
    var count:Int = 0 //주문 수량

) {
}