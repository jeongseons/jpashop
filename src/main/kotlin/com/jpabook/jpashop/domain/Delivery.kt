package com.jpabook.jpashop.domain

import javax.persistence.*

@Entity
data class Delivery(
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    var id:Long = 0L,

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    var order: Order,

    @Embedded
    var address: Address,

    @Enumerated(EnumType.STRING)
    var status:DeliveryStatus

) {

}
