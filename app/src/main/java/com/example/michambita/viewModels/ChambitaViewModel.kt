package com.example.michambita.viewModels


import android.location.Location

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.michambita.di.TrabajadorRepository
import com.example.michambita.model.app.TrabajadorModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class ChambitaViewModel @Inject constructor(private val repo: TrabajadorRepository) : ViewModel() {

    private val _trabajadores = MutableStateFlow<List<TrabajadorModel>>(emptyList())
    val trabajadores: StateFlow<List<TrabajadorModel>> = _trabajadores

    fun buscar(servicio: String, ubicacion: Location) {
        viewModelScope.launch {
            try { // pasamos el repo ya usando la DI
                val lista = repo.obtenerTrabajadoresPorServicioYUbicacion(servicio, ubicacion)
                _trabajadores.value = lista

                /*
                val snapshot = Firebase.firestore
                    .collection("Trabajadores")
                    .whereGreaterThanOrEqualTo("servicio", keyword.lowercase())
                    .get()
                    .await()

                val filtrados = snapshot.documents.mapNotNull { doc ->
                    val trabajador = doc.toObject(TrabajadorModel::class.java)
                    trabajador?.takeIf {
                        val distancia = FloatArray(1)
                        Location("").apply {
                            latitude = it.latitud
                            longitude = it.longitud
                        }.also { locTrabajador ->
                            Location.distanceBetween(
                                ubicacion.latitude,
                                ubicacion.longitude,
                                locTrabajador.latitude,
                                locTrabajador.longitude,
                                distancia
                            )
                        }
                        distancia[0] <= 5000
                    }
                }

                _trabajadores.value = filtrados


               */


            } catch (e: Exception) {
                Log.e("ChambitaVM", "Error al buscar trabajadores: ${e.message}")
            }
        }
    }
}
