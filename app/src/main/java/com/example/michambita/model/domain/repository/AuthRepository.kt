package com.example.michambita.model.domain.repository

import com.example.michambita.model.RolUsuario

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<RolUsuario>
    suspend fun register(email: String, password: String, username: String, rol: RolUsuario): Result<RolUsuario>
}