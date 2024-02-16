package cl.jmcontrerass.android.evaluacion3

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Representa la pantalla principal de la app (formulario)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormUI(
    formRegistroVm:FormRegistroViewModel,
    tomarFotoOnClick:() -> Unit = {},
    visualizarFotoOnClick:(Int) -> Unit = {},
    actualizarUbicacionOnClick:() -> Unit = {},
    verMapaOnClick:() -> Unit = {}
) {

    val contexto = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(30.dp))

            // Título de la Pantalla
            Text(
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                text = "Álbum de Fotos"
            )
            Divider(modifier = Modifier.height(3.dp))
            Spacer(modifier = Modifier.height(30.dp))
        }
        item {
            Row {
                // Cuadro de texto donde se ingresa el nombre del lugar
                TextField(
                    label = { Text("Nombre del lugar") },
                    value = formRegistroVm.lugar.value,
                    onValueChange = { formRegistroVm.lugar.value = // it

                        // Le da formato al texto con la primera letra mayúscula
                        it.lowercase().replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase()
                            else it.toString() }
                                    },
                    modifier = Modifier.width(320.dp).padding(horizontal = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
        item {
            // Listado de fotografías tomadas
            Text("Imágenes capturadas:")
            Spacer(modifier = Modifier.height(10.dp))

            // Muestra el texto ingresado en el campo de texto
            Text(
                fontWeight = FontWeight.Bold,
                text = formRegistroVm.lugar.value
            )
            Spacer(modifier = Modifier.height(10.dp))

            //
            formRegistroVm.fotosCapturadas.value?.forEachIndexed() { indice, uri ->

                Spacer(modifier = Modifier.height(5.dp))
                // Caja que contiene las imagenes
                Box(Modifier.size(300.dp, 150.dp)) {
                    Image(
                        // Rescata la imagen de bitmap a través del URI y el contexto
                        painter = BitmapPainter(uri2imageBitmap(uri, contexto)),
                        contentDescription = "Imagen capturada",
                        modifier = Modifier.fillMaxWidth().clickable {
                                // Visualiza foto de acuerdo con el índice de la lista
                                visualizarFotoOnClick(indice)
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Botón para tomar la fotografía
            Button(
                modifier = Modifier.width(270.dp).height(60.dp),
                onClick = {
                    // Llama a la función tomar foto
                    tomarFotoOnClick()
                }) {
                Text(
                    text = "Nueva foto"
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(modifier = Modifier.height(3.dp))
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            // Botón para obtener la ubicación
            Button(
                // Tamaño del botón
                modifier = Modifier.width(270.dp).height(60.dp),
                onClick = {
                    // Llama a función actualizar ubicación
                    actualizarUbicacionOnClick()
                }) {

                // Texto del botón de la ubicación
                Text(
                    text = "Cargar ubicación"
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Imprime en pantalla la ubicación em latitud y longitud
            Text("Lat: ${formRegistroVm.latitud.value} / Long: ${formRegistroVm.longitud.value}")

            Spacer(Modifier.height(60.dp))
            Divider(modifier = Modifier.height(3.dp))
            Spacer(Modifier.height(60.dp))

            // Botón para visualizar el mapa
            Button(
                modifier = Modifier.width(270.dp).height(60.dp),
                onClick = {
                    // Llama a la función ver mapa
                    verMapaOnClick()
                }) {
                Text(
                    text = "Ver mapa"
                )
            }
            Spacer(Modifier.height(100.dp))
        }
    }
}