package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.Address
import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.domain.Order
import com.jpabook.jpashop.domain.OrderStatus
import com.jpabook.jpashop.domain.item.Book
import com.jpabook.jpashop.repository.OrderRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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
        var member = Member()
        member.name = "회원1"
        member.address = Address("서울","강가","123-123")
        em.persist(member)

        var book = Book()
        book.name = "시골 JPA"
        book.price = 10000
        book.stockQuantity = 10
        em.persist(book)

        val orderCount = 2

        //when
        val orderId = orderService.order(member.id,book.id,orderCount)

        //then
        val getOrder: Order = orderRepository.findOne(orderId!!)

        print("test:"+book.id)
        print("test:+${getOrder.orderItems}")
        assertEquals(OrderStatus.ORDER, getOrder.status) //상품 주문시 상태 ORDER
        assertEquals(8,book.stockQuantity) //주문 수량만큼 재고가 줄어야 함
        assertEquals(10000 * orderCount, getOrder.getTotalPrice()) //주문 가격은 가격 * 수량
        assertEquals(1, getOrder.orderItems!!.size) //주문한 상품 종류 수가 정확해야 함
    }

    @Test
    @DisplayName("주문취소")
    fun orderCancelTest() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("상품주문_재고수량초과")
    fun orderStockOverTest() {
        //given

        //when

        //then
    }

}