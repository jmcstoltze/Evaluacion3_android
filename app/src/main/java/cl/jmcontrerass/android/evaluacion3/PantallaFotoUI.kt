package cl.jmcontrerass.android.evaluacion3

import androidx.camera.view.CameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

// Representa la pantalla de captura de fotografía
@Composable
fun PantallaFotoUI(
    formRegistroVm:FormRegistroViewModel,
    cameraViewModel:CameraAppViewModel,
    cameraController:CameraController
) {
    val contexto = LocalContext.current

    // Vista previa de la cámara vinculada al controlador de la cámara
    AndroidView(
        factory = {
            PreviewView(it).apply {
                controller = cameraController
            }
        },
        modifier = Modifier.fillMaxSize()
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ){

        // Botón que invoca la función tomar fotografía
        Button(
            modifier = Modifier.height(120.dp).width(120.dp),
            onClick = {
            tomarFotografia(cameraController, formRegistroVm, crearArchivoImagen(contexto), contexto) {

                // Almacena la imagen en la instancia de la vista
                formRegistroVm.fotoCapturada.value = it
                // Llama a la función encargada de volver a pantalla principal
                cameraViewModel.cambiarPantallaForm()
            }
        }) {
            Text(
                fontWeight = FontWeight.Bold, fontSize = 15.sp, text ="Capturar")
        }
    }
}