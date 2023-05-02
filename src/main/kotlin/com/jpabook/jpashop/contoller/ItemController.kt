package com.jpabook.jpashop.contoller

import com.jpabook.jpashop.domain.item.Item
import com.jpabook.jpashop.service.ItemService
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RestController
class ItemController(private val itemService: ItemService) {

    @GetMapping("/items")
    fun list(itemId: Long?): List<Item> {
        return itemService.findItems()
    }

    @PostMapping("items/{itemId}/edit")
    fun updateItem(@PathVariable("itemId") itemId: Long, @RequestBody item: Item) {
        itemService.updateItem(itemId, item)
    }

}

