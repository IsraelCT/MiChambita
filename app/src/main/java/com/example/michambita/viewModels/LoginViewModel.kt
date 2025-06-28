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

    private val auth: FirebaseAuth = Firebase.auth
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
                _estado.value =when(rol){
                    RolUsuario.Cliente -> EstadoLogin.exitoCliente
                    RolUsuario.Trabajador -> EstadoLogin.exitoTrabajador
                    RolUsuario.Ambos -> EstadoLogin.exitoAmbos
                }
             /*
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val uid = auth.currentUser?.uid
                            if (uid != null) {
                                Firebase.firestore.collection("Users").document(uid).get()
                                    .addOnSuccessListener { doc ->
                                        val rolString = doc.getString("rol") ?: "Cliente"
                                        val rol = try {
                                            RolUsuario.valueOf(rolString)
                                        } catch (e: IllegalStateException) {
                                            RolUsuario.Cliente
                                        }
                                        onSuccess(rol)
                                    }


                            } else {
                                Log.d("FIREBASE", "Login fallido")
                                showAlert = true

                            }
                        } else {
                            Log.d("ERROR EN FIREBASE", "Usuario y contrasena incorrectos")
                            showAlert = true
                        }
                    }

              */
            } catch (e: java.lang.Exception) {
                _estado.value = EstadoLogin.Error("Error de inicio de sesion  : ${e.localizedMessage ?: "desconocido"}")
               // Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")
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
                    _estado.value =when(rol){
                        RolUsuario.Cliente -> EstadoLogin.exitoCliente
                        RolUsuario.Trabajador -> EstadoLogin.exitoTrabajador
                        RolUsuario.Ambos -> EstadoLogin.exitoAmbos
                    }
                     /*   .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                saveUser(username)
                                onSuccess()
                            } else {
                                Log.d("ERROR EN FIREBASE", "Error al crear usuario")
                                showAlert = true
                            }
                        }


                      */
            } catch (e: Exception) {
                _estado.value = EstadoLogin.Error("Error : ${e.localizedMessage ?: "desconocido"}")
                //Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")
            }
        }
    }

        /*
    private fun saveUser(username: String) {
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            val user = UserModel(
                userId = id.toString(),
                email = email.toString(),
                username = username,
                rol = RolUsuario.Trabajador
            )

            FirebaseFirestore.getInstance().collection("Users")
                .add(user)
                .addOnSuccessListener {
                    Log.d("GUARDO", "Guardo correctamente")
                }.addOnFailureListener {
                    Log.d("ERROR AL GUARDAR", "ERROR al guardar en firestore")
                }
        }


    }


         */

    fun closeAlert() {
        showAlert = false
    }
}

