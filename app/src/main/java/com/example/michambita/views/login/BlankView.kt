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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun BlankView(navController: NavController) {

    LaunchedEffect(Unit) {
        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario != null) {
            val uid = usuario.uid
            Firebase.firestore.collection("Users").document(uid).get()
                .addOnSuccessListener { document ->
                    val rol = try {
                        val rolString = document.getString("rol") ?: "Cliente"
                        RolUsuario.valueOf(rolString)
                    } catch (e: Exception) {
                        RolUsuario.Cliente
                    }
                    when (rol) {
                        RolUsuario.Cliente -> navController.navigate("HomeCliente") { popUpTo(0) }
                        RolUsuario.Trabajador -> navController.navigate("HomeTrabajador") {
                            popUpTo(
                                0
                            )
                        }

                        RolUsuario.Ambos -> navController.navigate("HomeAmbos") { popUpTo(0) }

                    }



                }.addOnFailureListener {
                    navController.navigate("Login"){popUpTo(0)}
                }

            }
        else{
            navController.navigate("login"){popUpTo(0)}
        }
        }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        CircularProgressIndicator()
        }
    }


