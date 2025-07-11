package com.example.michambita.views.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.michambita.viewModels.LoginViewModel
/*
@Composable
fun TabsView(navController: NavHostController, loginVM: LoginViewModel) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Iniciar sesión", "Registrarse")
    Column {
        TabRow(
            selectedTabIndex = selectedTab,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab])
                )
            }) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(text = title) })
            }
            when (selectedTab){
                0 -> LoginView(navController,loginVM) // No olvidar agregar los parametros
                1 -> RegisterView(navController,loginVM)

            }

        }
    }
}



 */


@Composable
fun TabsView(navController: NavController, loginVM: LoginViewModel) {

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Iniciar Sesion", "Registrarse")

    Column(Modifier.padding(16.dp)) {
        TabRow(
            selectedTabIndex = selectedTab,
            contentColor = Color.Black,
            indicator = { tabPosition ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPosition[selectedTab])
                )
            }
        )
        {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(text = title) }
                )
            }
        }














        when (selectedTab) {
            0 -> LoginView(navController, loginVM)
            1 -> RegisterView(navController, loginVM)
        }
    }
}