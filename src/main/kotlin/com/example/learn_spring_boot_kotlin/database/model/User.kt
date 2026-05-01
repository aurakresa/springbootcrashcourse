package com.example.learn_spring_boot_kotlin.database.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Users")
data class User(
    @Id val id: ObjectId = ObjectId.get(),
    val hashedPassword: String,
    val email: String
)
