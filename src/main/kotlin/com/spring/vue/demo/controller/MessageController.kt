package com.spring.vue.demo.controller

import com.spring.vue.demo.exceptions.NotFoundException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("message")
class MessageController {
    private var counter = 4
    private var messages = arrayListOf(
            hashMapOf("id" to "1", "text" to "First message"),
            hashMapOf("id" to "2", "text" to "Second message"),
            hashMapOf("id" to "3", "text" to "Third message")
    )

    @GetMapping
    fun list(): ArrayList<HashMap<String, String>> {
        return messages
    }

    @GetMapping("{id}")
    fun getMessage(@PathVariable id: String): HashMap<String, String> {
        return findMessageById(id)
    }

    private fun findMessageById(id: String): HashMap<String, String> {
        return messages.stream()
                .filter { message -> message["id"].equals(id) }
                .findFirst()
                .orElseThrow(({ NotFoundException() }))
    }

    @PostMapping
    fun creatMessage(@RequestBody message: HashMap<String,String>): HashMap<String,String>{
        message.put("id", (counter++).toString())
        messages.add(message)
        return message
    }

    @PutMapping("{id}")
    fun updateMessage(@PathVariable id: String, @RequestBody message: HashMap<String, String>): HashMap<String, String>{
        val messageFromDb = findMessageById(id)
        messageFromDb.putAll(message)
        messageFromDb.put("id", id)
        return messageFromDb
    }

    @DeleteMapping("{id}")
    fun deleteMessage(@PathVariable id: String){
        val message = findMessageById(id)
        messages.remove(message)
    }
}