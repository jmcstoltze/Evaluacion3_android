package cl.jmcontrerass.android.evaluacion3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

// Composable para la visualización del mapa
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapaUI(
    formRegistroVm:FormRegistroViewModel,
    volverOnClick:() -> Unit = {}
) {
    // Valores de latitud y longitud actuales en la lista
    val lat = formRegistroVm.latitud.value
    val long = formRegistroVm.longitud.value

    // Si se carga no se carga la ubicación previamente, esto determina un texto
    val texto = if (lat == 0.0 && long == 0.0) {
        "Debe cargar la ubicación"
    } else {
        "Volver"
    }

    Scaffold(
        // Botón flotante para volver a la pantalla principal
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 25.dp, end = 15.dp),
                contentAlignment = androidx.compose.ui.Alignment.TopEnd
            )  {
                ExtendedFloatingActionButton(
                    modifier = Modifier.width(300.dp),
                    onClick = { volverOnClick() },
                    icon = {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver Pantalla principal"
                        )
                    },
                    // Texto que va a depender del if else previo
                    text = { Text(texto) }
                )
            }

        }
    ) { paddingValues -> // Macado en rojo, pero compila

        // Llamado a la función encargada de obtener y mostrar el mapa de acuerdo a lat y long
        MapaOsmUI(lat, long)
    }
}

// Muestra el mapa utilizando la biblioteca OSMDroid de acuerdo a los parámetros lat y long
@Composable
fun MapaOsmUI(latitud:Double, longitud:Double) {
    val contexto = LocalContext.current
    AndroidView(
        factory = {
            MapView(it).also {
                it.setTileSource(TileSourceFactory.MAPNIK)
                Configuration.getInstance().userAgentValue = contexto.packageName
            }
        }, update = {
            it.overlays.removeIf { true }
            it.invalidate()
            it.controller.setZoom(18.0)
            val geoPoint = GeoPoint(latitud, longitud)
            it.controller.animateTo(geoPoint)
            val marcador = Marker(it)
            marcador.position = geoPoint
            marcador.setAnchor(
                Marker.ANCHOR_CENTER,
                Marker.ANCHOR_CENTER)
            it.overlays.add(marcador)
        }
    )
}