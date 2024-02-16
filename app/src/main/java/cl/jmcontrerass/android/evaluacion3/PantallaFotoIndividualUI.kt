package cl.jmcontrerass.android.evaluacion3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

// Representa la pantalla de visualización de imágenes de manera individual
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFotoIndividualUI(
    formRegistroVm:FormRegistroViewModel,
    volverOnClick:() -> Unit = {}
) {
    // Valores que contienen: el índice de la imagen y la imagen
    val indice = formRegistroVm.indiceImagen.value
    val fotoSeleccionada = formRegistroVm.fotosCapturadas.value[indice!!]

    // Crea un image painter para cargar y mostrar la imagen
    val painter = rememberImagePainter(data = fotoSeleccionada)

    Scaffold(
        // Botón flotante para volver a la pantalla principal
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.width(130.dp),
                onClick = { volverOnClick() },
                icon = {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Volver Pantalla principal"
                    )
                },
                text = {Text("Volver")}
            )
        }
    ) { paddingValues -> // Marca en rojo, pero compila
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                // Muestra la imagen seleccionada
                painter = painter,
                contentDescription = "Imagen capturada",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}