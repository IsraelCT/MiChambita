 package com.example.michambita.model


data class UserModel(
    val userId: String,
    val email: String,
    val username: String,
   val rol : RolUsuario = RolUsuario.Cliente
)
 /*{
    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "userId" to this.userId,
            "email" to this.email,
            "username" to this.username,

        )
    }
}


  */


enum class RolUsuario {
    Cliente,
    Trabajador,
    Ambos
}
