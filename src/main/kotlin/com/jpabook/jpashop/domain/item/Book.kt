package com.jpabook.jpashop.domain.item

import java.time.LocalDateTime
import javax.persistence.*


@Entity
@DiscriminatorValue("B")
data class Book(
    var author:String = "",
    var isbn:String = ""): Item() {
}
