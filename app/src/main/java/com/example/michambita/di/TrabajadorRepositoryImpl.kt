package com.example.michambita.di

import android.location.Location
import com.example.michambita.model.app.TrabajadorModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TrabajadorRepositoryImpl  @Inject constructor(private val firestore: FirebaseFirestore
) : TrabajadorRepository {
    override suspend fun obtenerTrabajadoresPorServicioYUbicacion(
        servicio: String,
        ubicacionCliente: Location,
        radioEnMetros: Float
    ): List<TrabajadorModel> {
        val snapshot = firestore
            .collection("Trabajadores")
            .whereGreaterThanOrEqualTo("servicio", servicio.lowercase())
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            val trabajador = doc.toObject(TrabajadorModel::class.java)
            trabajador?.takeIf {
                val distancia = FloatArray(1)
                Location("").apply {
                    latitude = it.latitud
                    longitude = it.longitud
                }.also { locTrabajador ->
                    Location.distanceBetween(
                        ubicacionCliente.latitude,
                        ubicacionCliente.longitude,
                        locTrabajador.latitude,
                        locTrabajador.longitude,
                        distancia
                    )
                }
                distancia[0] <= radioEnMetros
            }
        }
    }



}