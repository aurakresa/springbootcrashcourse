package com.example.learn_spring_boot_kotlin.database.repository

import com.example.learn_spring_boot_kotlin.database.model.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository


interface UserRepository : MongoRepository<User, ObjectId>{
    fun findByEmail(email: String): User?
}