package com.jpabook.jpashop.domain

import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Member(
    @Id @GeneratedValue
    @Column(name = "user_id")
    var id:Long = 0L,

    var name:String = "",

    @Embedded
    var address: Address = Address(),

    @OneToMany(mappedBy = "member")
    var orders:List<Order> = listOf()

) {

}