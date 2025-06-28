package com.example.michambita.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.michambita.model.EstadoLogin

import com.example.michambita.viewModels.LoginViewModel
@Composable
fun LoginFeedBack(estado : EstadoLogin, onNavigateToRegister : () -> Unit){
    when (estado){
        is EstadoLogin.cargando -> {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
        is EstadoLogin.Error -> {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "${estado.mensaje}")
        }
        else -> Unit

    }
    Spacer(modifier = Modifier.height(24.dp))

    TextButton(onClick = onNavigateToRegister) {
        Text("No tienes cuenta? Registrate")
    }

}