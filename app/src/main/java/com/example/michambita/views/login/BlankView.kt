package com.example.michambita.views.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.michambita.model.RolUsuario
import com.example.michambita.model.toEstadoExito
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


@Composable
fun BlankView(navController: NavController) {
    LaunchedEffect(Unit) {
        val usuario = FirebaseAuth.getInstance().currentUser

        if (usuario != null) {
            try {
                val uid = usuario.uid
                val documento = Firebase.firestore
                    .collection("Users")
                    .document(uid)
                    .get()
                    .await()

                val rol = try {
                    val rolString = documento.getString("rol") ?: "Cliente"
                    RolUsuario.valueOf(rolString)
                } catch (e: Exception) {
                    RolUsuario.Cliente
                }

                rol.toEstadoExito()

            } catch (e: Exception) {
                navController.navigate("Login") { popUpTo(0) }
            }
        } else {
            navController.navigate("Login") { popUpTo(0) }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
