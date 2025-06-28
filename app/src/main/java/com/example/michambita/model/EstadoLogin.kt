package com.example.michambita.model

sealed  class EstadoLogin {
    object Idle  : EstadoLogin()
    object cargando : EstadoLogin()
    object exitoCliente : EstadoLogin()
    object exitoTrabajador : EstadoLogin()
    object exitoAmbos : EstadoLogin()
    data class Error (val mensaje : String) : EstadoLogin()
}