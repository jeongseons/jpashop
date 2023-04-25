package com.jpabook.jpashop.domain

import javax.persistence.*

@Entity
data class Member(
    @Id @GeneratedValue
    @Column(name = "user_id")
    var id:Long = 0L,

    var name:String = "",

    @Embedded
    var address: Address = Address(),

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    var orders:List<Order> = listOf()

) {

}