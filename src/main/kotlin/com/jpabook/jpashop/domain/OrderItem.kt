package com.jpabook.jpashop.domain

import com.jpabook.jpashop.domain.item.Item
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class OrderItem(
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    var id:Long? = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null,

    var orderPrice:Int = 0, //주문 가격
    var count:Int = 0 //주문 수량

) {
    //==생성 메서드==//
    companion object {
        @JvmStatic
        fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
            var orderItem = OrderItem()
            orderItem.item = item
            orderItem.orderPrice = orderPrice
            orderItem.count = count

            item.removeStock(count)
            return orderItem
    }
}

    //==비즈니스 로직==//
    fun cancel() {
        item?.addStock(count)
    }

    //==조회 로직==//
    //주문상품 전체 가격 조회
    fun getTotalPrice():Int {
        return orderPrice * count
    }

}