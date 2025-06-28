package com.example.michambita.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.michambita.viewModels.LoginViewModel

import com.example.michambita.views.job.HomeAmbosView
import com.example.michambita.views.job.HomeClienteView
import com.example.michambita.views.job.HomeTrabajadorView



import com.example.michambita.views.login.BlankView
import com.example.michambita.views.login.LoginView
import com.example.michambita.views.login.RegisterView

import com.example.michambita.views.login.TabsView

@Composable
fun NavManager(loginVM: LoginViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Blank") {
        composable("Blank") {
            BlankView(navController)
        }
       composable("Login") { LoginView(navController,loginVM) }
        composable ("Register"){ RegisterView(navController,loginVM) }
        composable("HomeTrabajador") { HomeTrabajadorView(navController=navController) }
        composable ("HomeCliente"){ HomeClienteView(navController=navController) }
        composable("HomeAmbos") { HomeAmbosView(navController=navController) }



    }
}

