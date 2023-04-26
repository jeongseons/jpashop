package com.jpabook.jpashop.domain.item

import com.jpabook.jpashop.domain.Category
import com.jpabook.jpashop.exception.NotEnoughStockException
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
    //비즈니스 로직 - 이경우 도메인에 있는 게 관리 쉬움 + 응집력

    //stock 증가
    fun addStock(quantity:Int) {
        this.stockQuantity += quantity
    }

    //stock 감소

    fun removeStock(quantity: Int) {
        var restStock = this.stockQuantity - quantity
        if (restStock < 0 ) {
            throw NotEnoughStockException("need more stock")
        }
        this.stockQuantity = restStock
    }



}
