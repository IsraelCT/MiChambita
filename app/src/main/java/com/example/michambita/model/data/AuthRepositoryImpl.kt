package com.example.michambita.model.data

import com.example.michambita.model.RolUsuario
import com.example.michambita.model.UserModel
import com.example.michambita.model.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<RolUsuario> = try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val uid = result.user?.uid ?: return Result.failure(Exception("UID nulo"))

        val document = firestore.collection("Users").document(uid).get().await()
        val rolStr = document.getString("rol") ?: "Cliente"
        val rol = try {
            RolUsuario.valueOf(rolStr)
        } catch (e: Exception) {
            RolUsuario.Cliente
        }

        Result.success(rol)

    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun register(email: String, password: String, username: String, rol: RolUsuario): Result<RolUsuario> = try {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val uid = result.user?.uid ?: return Result.failure(Exception("UID nulo"))

        val nuevoUsuario = UserModel(uid, username, email, rol)
        firestore.collection("Users").document(uid).set(nuevoUsuario).await()

        Result.success(rol)

    } catch (e: Exception) {
        Result.failure(e)
    }
}