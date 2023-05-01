package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.Address
import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.domain.Order
import com.jpabook.jpashop.domain.OrderStatus
import com.jpabook.jpashop.domain.item.Book
import com.jpabook.jpashop.domain.item.Item
import com.jpabook.jpashop.exception.NotEnoughStockException
import com.jpabook.jpashop.repository.OrderRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    lateinit var em:EntityManager

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Test
    @DisplayName("상품주문")
    fun orderTest() {
        //given
        var member = createMember()
        var book:Item = createBook("시골 JPA", 10000, 10)
        val orderCount = 2

        //when
        val orderId = orderService.order(member.id,book.id,orderCount)

        //then
        val getOrder: Order = orderRepository.findOne(orderId!!)

        println("test:"+book.id)
        println("test:$orderId")
        println("test:${getOrder.orderItems}")
        println("test:${getOrder.orderDate}")
        println("test:${getOrder.member}")

        assertEquals(OrderStatus.ORDER, getOrder.status) //상품 주문시 상태 ORDER
//        assertEquals(8,book.stockQuantity) //주문 수량만큼 재고가 줄어야 함
//        assertEquals(10000 * orderCount, getOrder.getTotalPrice()) //주문 가격은 가격 * 수량
//        assertEquals(1, getOrder.orderItems.size) //주문한 상품 종류 수가 정확해야 함
    }

    @Test
    @DisplayName("주문취소")
    fun orderCancelTest() {
        //given
        val member = createMember()
        val item:Book = createBook("시골 JPA", 10000, 10)

        val orderCount = 2

        val orderId = orderService.order(member.id,item.id,orderCount)

        //when
        orderService.cancelOrder(orderId!!)

        //then
        val getOrder = orderRepository.findOne(orderId)

        assertEquals(OrderStatus.CANCEL,getOrder.status) //주문 취소시 상태 CANCEL
//        assertEquals(10, item.stockQuantity) //주문이 취소된 상품은 그만큼 재고가 증가한다

    }

    @Test
    @DisplayName("상품주문_재고수량초과")
    fun orderStockOverTest() {
        //given
        val member = createMember()
        var item: Item = createBook("시골 JPA", 10000, 10)

        val orderCount = 11

        //when
        //then
        Assertions.assertThrows(NotEnoughStockException::class.java) {
            orderService.order(member.id, item.id, orderCount)
            fail("재고 수량 초과 예외가 발생해야 한다")
        }

    }

    private fun createMember(): Member {
        var member = Member()
        member.name = "회원1"
        member.address = Address("서울", "강가", "123-123")
        em.persist(member)
        return member
    }

    private fun createBook(name:String, price:Int, stockQuantity:Int): Book {
        val book = Book()
        book.name = name
        book.price = price
        book.stockQuantity = stockQuantity
        em.persist(book)
        return book
    }

}