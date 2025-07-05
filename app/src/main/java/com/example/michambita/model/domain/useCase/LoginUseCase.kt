package com.example.michambita.model.domain.useCase

import com.example.michambita.model.RolUsuario
import com.example.michambita.model.domain.repository.AuthRepository
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<RolUsuario> {
        return authRepository.login(email, password)
    }
}