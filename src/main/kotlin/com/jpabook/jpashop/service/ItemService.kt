package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.item.Item
import com.jpabook.jpashop.repository.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemService(val itemRepository: ItemRepository) {

    @Transactional
    fun saveItem(item: Item) {
        itemRepository.save(item)
    }

    @Transactional
    fun updateItem(itemId: Long, item: Item) {
        var updateItem = itemRepository.findOne(itemId!!)
        updateItem.name = item.name
        updateItem.price = item.price
        updateItem.stockQuantity = item.stockQuantity
    }

    fun findItems():List<Item> {
        return itemRepository.findAll()
    }

    fun findOne(itemId:Long):Item {
        return itemRepository.findOne(itemId)
    }

}