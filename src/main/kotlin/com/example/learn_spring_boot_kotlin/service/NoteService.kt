package com.example.learn_spring_boot_kotlin.service

import com.example.learn_spring_boot_kotlin.database.model.Note
import com.example.learn_spring_boot_kotlin.database.repository.NoteRepository
import jakarta.validation.constraints.NotBlank
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class NoteService(
    private val noteRepository: NoteRepository
){
    data class NoteRequest(
        val id: String?,
        @field:NotBlank("Title can't be blank")
        val title: String,
        val content: String,
        val color: Long
    )

    data class NoteResponse(
        val id: String,
        val title: String,
        val content: String,
        val color: Long,
        val createdAt: Instant
    )

    fun save(body: NoteRequest, ownerId: String): NoteResponse {
        val note = noteRepository.save(
            Note(
                id = body.id?.let { ObjectId(it) } ?: ObjectId.get(),
                title = body.title,
                content = body.content,
                color = body.color,
                createdAt = Instant.now(),
                ownerId = ObjectId(ownerId)
            )
        )
        return noteRepository.save(note).toResponse()
    }

    fun findByOwnerId(ownerId: String): List<NoteResponse>{
        return noteRepository.findByOwnerId(ObjectId(ownerId)).map {
            it.toResponse()
        }
    }

    fun deleteById(id: String, ownerId: String){
        val note = noteRepository.findById(ObjectId(id)).orElseThrow{
            IllegalArgumentException("Note not found")
        }

        if (note.ownerId.toHexString() == ownerId){
            noteRepository.deleteById(ObjectId(id))
        }
    }
}

private fun Note.toResponse(): NoteService.NoteResponse {
    return NoteService.NoteResponse(
        id = id.toHexString(),
        title = title,
        content = content,
        color = color,
        createdAt = createdAt
    )
}