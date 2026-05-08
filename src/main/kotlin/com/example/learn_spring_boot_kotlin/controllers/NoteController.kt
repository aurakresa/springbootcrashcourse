package com.example.learn_spring_boot_kotlin.controllers

import com.example.learn_spring_boot_kotlin.service.NoteService
import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

// POST http://localhost:8085/notes
// GET http://localhost:8085/notes?ownerId=123
// DELETE http://localhost:8085/notes/123

@RestController
@RequestMapping("/notes")
class NoteController(
    private val noteService: NoteService
) {

    @PostMapping
    fun save(
        @Valid @RequestBody body: NoteService.NoteRequest
    ): NoteService.NoteResponse {
        val ownerId = SecurityContextHolder.getContext().authentication?.principal as String
        return noteService.save(
            body = body,
            ownerId = ownerId
        )
    }

    @GetMapping
    fun findByOwnerId(): List<NoteService.NoteResponse> {
        val ownerId = SecurityContextHolder.getContext().authentication?.principal as String
        return noteService.findByOwnerId(ownerId = ownerId)
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: String) {
        val ownerId = SecurityContextHolder.getContext().authentication?.principal as String
    noteService.deleteById(id = id, ownerId = ownerId)
    }
}