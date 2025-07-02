package com.example.michambita.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.michambita.model.EstadoLogin
import com.example.michambita.model.RolUsuario
import com.example.michambita.model.UserModel
import com.example.michambita.model.toEstadoExito
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import kotlin.jvm.Throws

class LoginViewModel : ViewModel() {


    var showAlert by mutableStateOf(false)
    private val _estado = MutableStateFlow<EstadoLogin>(EstadoLogin.Idle)
    val estado: StateFlow<EstadoLogin> = _estado

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _estado.value = EstadoLogin.cargando
            try {
                val result = FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).await()

                val uid= result?.user?.uid ?: throw Exception("Uid no está disponible")

                val documento = Firebase.firestore.collection("Users").document(uid).get().await()
                val rolStr = documento.getString("rol") ?: "Cliente"

                val rol =try {
                    RolUsuario.valueOf(rolStr)}
                catch (e : Exception){
                    RolUsuario.Cliente
                }
                _estado.value = rol.toEstadoExito()
             /*

              */
            } catch (e: java.lang.Exception) {
                _estado.value = EstadoLogin.Error("Error de inicio de sesion  : ${e.localizedMessage ?: "desconocido"}")

            }
        }
    }

    fun RegisterUser(email: String, password: String, username: String, rol: RolUsuario) {
        viewModelScope.launch {
            _estado.value = EstadoLogin.cargando
            try {
                val authResult =
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .await()
                val uid = authResult.user?.uid ?: throw Exception("UID nulo")
                    val nuevoUsuario = UserModel(uid,username,email,rol)
                Firebase.firestore.collection("Users").document(uid).set(nuevoUsuario).await()
                    _estado.value =rol.toEstadoExito()

            } catch (e: Exception) {
                _estado.value = EstadoLogin.Error("Error : ${e.localizedMessage ?: "desconocido"}")

            }
        }
    }



    fun closeAlert() {
        showAlert = false
    }
    fun activarAlerta(){
        showAlert = true
    }
}

