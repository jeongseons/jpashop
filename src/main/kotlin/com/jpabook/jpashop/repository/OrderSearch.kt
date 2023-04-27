package com.jpabook.jpashop.repository

import com.jpabook.jpashop.domain.OrderStatus

data class OrderSearch(
    val memberName: String, //회원 이름
    val orderStatus: OrderStatus //주문 상태[ORDER, CANCEL]
) {
}