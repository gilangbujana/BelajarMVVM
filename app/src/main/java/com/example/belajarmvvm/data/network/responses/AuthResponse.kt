package com.example.belajarmvvm.data.network.responses

import com.example.belajarmvvm.data.db.entities.User

data class AuthResponse (
    val status: Int?,
    val pesan: String?,
    val user: User?
)