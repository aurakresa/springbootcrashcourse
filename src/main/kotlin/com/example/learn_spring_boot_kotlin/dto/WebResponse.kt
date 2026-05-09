package com.example.learn_spring_boot_kotlin.dto

data class WebResponse<T>(
    val status: String,
    val message: String,
    val data: T? = null,
    val errors: Any? = null
)