package com.example.michambita.model

fun RolUsuario.toEstadoExito(): EstadoLogin{
    return when (this){
        RolUsuario.Cliente -> EstadoLogin.exitoCliente
        RolUsuario.Trabajador -> EstadoLogin.exitoTrabajador
        RolUsuario.Ambos -> EstadoLogin.exitoAmbos
    }
}