package com.example.learn_spring_boot_kotlin.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collation = "Notes")
data class Note(
    @Id val id: ObjectId? = ObjectId.get(),
    val tittle: String,
    val content: String,
    val color: Long,
    val createdAt: Instant
)
