package com.example.michambita.views.job

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.location.LocationServices
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.michambita.viewModels.ChambitaViewModel


@SuppressLint("MissingPermission")
@Composable
fun HomeClienteView(navController: NavController,chambitaVM : ChambitaViewModel) {
    val listaTrabajadores by chambitaVM.trabajadores.collectAsState()
    var palabraClave by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(Modifier.fillMaxSize().padding(16.dp)) {

        OutlinedTextField(
            value = palabraClave,
            onValueChange = { palabraClave = it },
            label = { Text("Buscar servicio") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val fusedLocation = LocationServices.getFusedLocationProviderClient(context)
                fusedLocation.lastLocation.addOnSuccessListener { ubicacionActual ->
                    if (ubicacionActual != null) {
                        chambitaVM.buscar(palabraClave, ubicacionActual)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Buscar")
        }

        LazyColumn {
            items(listaTrabajadores) { trabajador ->
                Card(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Column(Modifier.padding(12.dp)) {
                        Text("👷 ${trabajador.username}", fontWeight = FontWeight.Bold)
                        Text("🔧 Servicio: ${trabajador.servicio}")
                    }
                }
            }
        }
    }
}
