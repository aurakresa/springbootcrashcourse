package com.example.learn_spring_boot_kotlin.controllers

import com.example.learn_spring_boot_kotlin.dto.WebResponse
import com.example.learn_spring_boot_kotlin.service.NoteService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

// POST http://localhost:8085/notes
// GET http://localhost:8085/notes?ownerId=123
// DELETE http://localhost:8085/notes/123
// PUT http://localhost:8085/notes/123

@RestController
@RequestMapping("/notes")
class NoteController(
    private val noteService: NoteService
) {

    @PostMapping
    fun save(
        @Valid @RequestBody body: NoteService.NoteRequest, principal: Principal
    ): ResponseEntity<WebResponse<NoteService.NoteResponse>> {
        val ownerId = principal.name
        val saveNotes = noteService.save(
            body = body,
            ownerId = ownerId
        )

        val response = WebResponse(
            status = "success",
            message = "succeed add note",
            data = saveNotes
        )

        return ResponseEntity.ok(response)
    }

    @PutMapping(path = ["/{id}"])
    fun update(
        @Valid @RequestBody body: NoteService.NoteRequest, @PathVariable id: String, principal: Principal
    ): ResponseEntity<WebResponse<NoteService.NoteResponse>>{
        val ownerId = principal.name
        val saveNote = noteService.update(
            id = id,
            body = body,
            ownerId = ownerId
        )

        val response = WebResponse(
            status = "success",
            message = "success update note",
            data = saveNote
        )

        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun findByOwnerId(principal: Principal): ResponseEntity<WebResponse<List<NoteService.NoteResponse>>> {
        val ownerId = principal.name
        val notes = noteService.findByOwnerId(ownerId = ownerId)

        val response = WebResponse(
            status = "success",
            message = "Success retrieve notes",
            data = notes
        )

        return ResponseEntity.ok(response)
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: String, principal: Principal) : ResponseEntity<WebResponse<Any>>{
        val ownerId = principal.name
        noteService.deleteById(id = id, ownerId = ownerId)

        val response = WebResponse<Any>(
            status = "success",
            message = "Note deleted successfully",
            data = null
        )

        return ResponseEntity.ok(response)
    }
}