package com.jpabook.jpashop.domain

import com.jpabook.jpashop.domain.item.Item
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class OrderItem(
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    var id:Long = 0L,

    @ManyToOne
    @JoinColumn(name = "item_id")
    var item: Item,

    @ManyToOne
    @JoinColumn(name = "order_id")
    var order: Order,

    var orderPrice:Int = 0, //주문 가격
    var count:Int = 0 //주문 수량

) {
}