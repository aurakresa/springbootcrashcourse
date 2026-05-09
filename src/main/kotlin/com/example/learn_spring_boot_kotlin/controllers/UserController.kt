package com.example.learn_spring_boot_kotlin.controllers

import com.example.learn_spring_boot_kotlin.dto.WebResponse
import com.example.learn_spring_boot_kotlin.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/users")
class UserController (
    private val userService: UserService
){
    @GetMapping("/me")
    fun getMyProfile(principal: Principal) : ResponseEntity<WebResponse<UserService.UserProfileResponse>>{
        val id = principal.name

        val profileData = userService.getMyProfile(id = id)

        val response = WebResponse(
            status = "success",
            message = "succes get profile",
            data = profileData
        )

        return ResponseEntity.ok(response)
    }
}