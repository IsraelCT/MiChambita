package com.example.michambita.components

import android.R
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.michambita.model.RolUsuario

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RolDropdown(
    rolSeleccionado: RolUsuario,
    onRolSeleccionado: (RolUsuario) -> Unit,
    modifier: Modifier = Modifier
) {
    //DrowndownMenuBox

    var expanted by remember { mutableStateOf(false) }

    val roles = RolUsuario.entries.toList()


    ExposedDropdownMenuBox(
        expanded = expanted,
        onExpandedChange = { expanted = !expanted }) {
        OutlinedTextField(
            value = "${rolSeleccionado.name}",
            onValueChange = {},
            readOnly = true,
            label = { Text("Selecciona el rol") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanted)

            },
            modifier = modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
        )

        ExposedDropdownMenu(
            expanded = expanted,
            onDismissRequest = { expanted = false },modifier = Modifier.background(Color.White)) {
            roles.forEach { rol ->
                DropdownMenuItem(
                    text = {
                        Text(
                            rol.name,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    },
                    onClick = {
                        onRolSeleccionado(rol)
                        expanted = false
                    }
                )
            }
        }
    }

}