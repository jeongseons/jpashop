package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.item.Book
import com.jpabook.jpashop.repository.ItemRepository
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension

class ItemServiceTest {

    @Mock
    lateinit var itemRepository: ItemRepository

    @InjectMocks
    lateinit var itemService: ItemService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    @DisplayName("상품등록")
    fun testSaveItem() {
        //given
        var item = createBook("책1",10000,2)

        //when
        doNothing().`when`(itemRepository).save(item)
        itemService.saveItem(item)

        //then
        verify(itemRepository, times(1)).save(item)
    }

    @Test
    @DisplayName("상품목록조회")
    fun testFindItem() {
        //given
        var item1 = createBook("책1",10000,2)

        var item2 = createBook("책2",5000,1)

        val itemList = listOf(item1,item2)

        //when
        `when`(itemRepository.findAll()).thenReturn(itemList)
        val result = itemService.findItems()

        //then
        assertEquals(itemList, result)
    }

    @Test
    @DisplayName("상품조회")
    fun testFindOne() {
        //given
        val itemId: Long = 1
        var item = createBook("책1",10000,2)

        //when
        `when`(itemRepository.findOne(itemId)).thenReturn(item)
        val result = itemService.findOne(itemId)

        //then
        assertEquals(item, result)
    }

    @Test
    @DisplayName("상품조회-BDDMockito")
    fun testFindItem2() {
        //given
        val itemId: Long = 1
        var item = createBook("책1", 10000, 2)

        given(itemService.findOne(itemId)).willReturn(item)

        //when
        itemService.saveItem(item)
        val result = itemService.findOne(itemId)

        //then
        assertThat(result).isEqualTo(item)
    }

    @Test
    @DisplayName("상품등록_가격0원이하")
    fun testSaveOne2() {
        //given
        var item = createBook("책1", -20000, 2)

        Assertions.assertThrows(IllegalArgumentException::class.java){
            item.price = -2000
        }

    }



    private fun createBook(name:String, price:Int, stockQuantity:Int): Book {
        val book = Book()
        book.name = name
        book.price = price
        book.stockQuantity = stockQuantity
        return book
    }

}

