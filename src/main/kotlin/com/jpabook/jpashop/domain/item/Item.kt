package com.jpabook.jpashop.domain.item

import com.jpabook.jpashop.domain.Category
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
abstract class Item(

    @Id @GeneratedValue
    @Column(name = "item_id")
    open var id:Long = 0L,

    open var name:String = "",

    open var price:Int = 0,

    open var stockQuantity:Int = 0,

    @ManyToMany(mappedBy = "items")
    open var categories:MutableList<Category> = ArrayList()

) {

}
