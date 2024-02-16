package cl.jmcontrerass.android.evaluacion3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// Representa la pantalla de acceso denegado
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDenegadoUI(
    volverOnClick:() -> Unit = {}
) {

    val texto = "Debe otorgar permisos para acceder"

    Scaffold(
        // Botón flotante para volver a la pantalla principal
        floatingActionButton = {

                ExtendedFloatingActionButton(
                    modifier = Modifier.width(350.dp),
                    onClick = { volverOnClick() },
                    icon = {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver Pantalla principal"
                        )
                    },
                    text = { Text(texto) }
                )
        }
    ) { paddingValues -> // Marcado en rojo, pero compila
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ){
            // Muestra una imagen al centro de la pantalla
            Image(
                modifier = Modifier.size(300.dp, 300.dp),
                painter = painterResource(id = R.drawable.denegado),
                contentDescription = "Permiso Denegado"
            )
        }
    }
}

