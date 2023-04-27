package com.jpabook.jpashop.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
data class Delivery(
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    var id:Long? = 0L,

    @JsonBackReference
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    var order: Order?,

    @Embedded
    var address: Address,

    @Enumerated(EnumType.STRING)
    var status:DeliveryStatus = DeliveryStatus.READY

) {

}
