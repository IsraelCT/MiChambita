package com.example.michambita.model.di

import android.location.Location
import com.example.michambita.model.app.TrabajadorModel

interface TrabajadorRepository {
    suspend fun obtenerTrabajadoresPorServicioYUbicacion(
        servicio: String,
        ubicacionCliente: Location,
        radioEnMetros: Float = 5000f
    ): List<TrabajadorModel>

}