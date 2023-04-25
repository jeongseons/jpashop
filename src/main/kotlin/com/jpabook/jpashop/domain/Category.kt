package com.jpabook.jpashop.domain

import com.jpabook.jpashop.domain.item.Item
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
data class Category(
    @Id @GeneratedValue
    @Column(name = "category_id")
    var id:Long = 0L,

    var name:String = "",

    var price:Int = 0,

    var stockQuantity:Int = 0,

    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    var items:MutableList<Item> = ArrayList(),

    @ManyToOne
    @JoinColumn(name = "parent_id")
    var parent:Category,

    @OneToMany(mappedBy = "parent")
    var child:List<Category> = ArrayList()

) {

}

