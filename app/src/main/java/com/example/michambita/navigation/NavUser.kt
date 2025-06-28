package com.example.michambita.navigation

import androidx.navigation.NavController
import com.example.michambita.model.EstadoLogin

fun NavUser(estado : EstadoLogin,navController: NavController){
    when(estado){
        is EstadoLogin.exitoCliente -> {
            navController.navigate("HomeCliente"){ popUpTo(0)}

        }
        is EstadoLogin.exitoTrabajador -> {
            navController.navigate("HomeTrabajador"){popUpTo(0)}
        }
        is EstadoLogin.exitoAmbos -> {
            navController.navigate("HomeAmbos"){popUpTo(0)}
        }
        else -> Unit
    }
}