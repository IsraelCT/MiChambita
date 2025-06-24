package com.example.michambita.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.michambita.viewModels.LoginViewModel
import com.example.michambita.views.job.HomeView
import com.example.michambita.views.login.BlankView
import com.example.michambita.views.login.TabsView

@Composable
fun NavManager(loginVM: LoginViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Blank") {
        composable("Blank") {
            BlankView(navController)
        }
        composable("Login") {
            TabsView(navController, loginVM)
        }
        composable("Home") {
            HomeView(navController)
        }
    }
}

