package com.jpabook.jpashop.domain

import com.jpabook.jpashop.domain.item.Item
import javax.persistence.*

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent:Category,

    @OneToMany(mappedBy = "parent")
    var child:List<Category> = ArrayList()

) {

    fun addChildCategory(child:Category) {
        this.child.plus(child)
        child.parent = this
    }

}

