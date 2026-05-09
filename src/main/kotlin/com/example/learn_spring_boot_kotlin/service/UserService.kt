package com.example.learn_spring_boot_kotlin.service

import com.example.learn_spring_boot_kotlin.database.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val userRepository: UserRepository
) {
    data class UserProfileResponse(
        val id: String,
        val email: String
    )

    fun getMyProfile(id: String) : UserProfileResponse{
        val user = userRepository.findById(ObjectId(id)).orElseThrow{
            ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }

        return UserProfileResponse(
            id = user.id.toHexString(),
            email = user.email
        )
    }
}