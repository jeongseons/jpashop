package com.jpabook.jpashop.domain.item

import com.jpabook.jpashop.domain.Delivery
import com.jpabook.jpashop.domain.OrderItem
import com.jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@DiscriminatorValue("B")
data class Book(
    var author:String = "",
    var isbn:String = "" ): Item() {

}
