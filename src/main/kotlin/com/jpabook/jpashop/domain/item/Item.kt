package com.jpabook.jpashop.domain.item

import com.jpabook.jpashop.domain.Category
import com.jpabook.jpashop.exception.NotEnoughStockException
import com.jpabook.jpashop.utill.AllOpen
import javax.persistence.*


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@AllOpen
abstract class Item {

        @Id
        @GeneratedValue
        @Column(name = "item_id")
        var id: Long? = null

        var name: String = ""

        var price = 0

        var stockQuantity = 0

        @ManyToMany(mappedBy = "items")
        private val categories: List<Category> = ArrayList<Category>()


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

