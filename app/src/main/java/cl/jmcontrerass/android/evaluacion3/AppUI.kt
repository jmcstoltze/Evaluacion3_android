package cl.jmcontrerass.android.evaluacion3

import android.Manifest
import android.util.Log
import androidx.camera.view.CameraController
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AppUI(cameraController: CameraController) {
    val contexto = LocalContext.current
    val formRegistroVm:FormRegistroViewModel = viewModel()
    val cameraAppViewModel:CameraAppViewModel = viewModel()
    when(cameraAppViewModel.pantalla.value) {
        Pantalla.FORM -> {
            // Llamada a pantalla de formulario
            PantallaFormUI(
                formRegistroVm,
                // Lleva a la view de controlador de la cámara o pantalla FOTO
                tomarFotoOnClick = {
                    cameraAppViewModel.cambiarPantallaFoto()
                    // Solicita el permiso de uso de cámara
                    cameraAppViewModel.lanzadorPermisos?.launch(arrayOf(Manifest.permission.CAMERA))
                    // si se deniega redirige a pantalla de permiso denegado
                    cameraAppViewModel.onPermisoDenegado = {
                        cameraAppViewModel.permisoConcedido.value = false
                        cameraAppViewModel.cambiarPantallaDenegado()
                    }
                },
                // redirige a la pantalla para visualizar cada foto por separado
                visualizarFotoOnClick = {
                    cameraAppViewModel.cambiarPantallaFotoIndividual()
                    // almacena el índice de la imagen clickeada
                    formRegistroVm.indiceImagen.value = it
                },
                // almacena valores de la ubicación solicitando permisos
                actualizarUbicacionOnClick = {
                    // Solicita permisos para acceder al GPS
                    cameraAppViewModel.lanzadorPermisos?.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                    // Si se conceden se almacenan valores
                    cameraAppViewModel.onPermisoUbicacionOk = {
                        getUbicacion(contexto) {
                            formRegistroVm.latitud.value = it.latitude
                            formRegistroVm.longitud.value = it.longitude
                        }
                    }
                    // Si se rechazan cambia a pantalla de permiso denegado
                    cameraAppViewModel.onPermisoDenegado = {
                        cameraAppViewModel.permisoConcedido.value = false
                        cameraAppViewModel.cambiarPantallaDenegado()
                    }
                },
                // Redirige a la visualización del mapa en pantalla completa
                verMapaOnClick = {
                    cameraAppViewModel.cambiarPantallaMapa()
                }
            )
        }
        // Llamada a pantalla de captura de fotografía
        Pantalla.FOTO -> {
            PantallaFotoUI(formRegistroVm, cameraAppViewModel,
                cameraController)
        }
        // Llamada a pantalla de fotografía individual
        Pantalla.FOTOINDIVIDUAL -> {
            PantallaFotoIndividualUI(
                formRegistroVm,
                volverOnClick = {
                    cameraAppViewModel.cambiarPantallaForm()
                }
            )
        }
        // Llamada a pantalla del mapa
        Pantalla.MAPA -> {
            PantallaMapaUI(
                formRegistroVm,
                volverOnClick = {
                    cameraAppViewModel.cambiarPantallaForm()
                }
            )
        }
        // Llamada a pantalla de permiso denegado
        Pantalla.DENEGADO -> {
            PantallaDenegadoUI(
                volverOnClick = {
                    cameraAppViewModel.cambiarPantallaForm()
                }
            )
        }
        // Esta parte del código no se emplea (ejemplo de clases)
        else -> {
            Log.v("AppUI()", "when else, no debería entrar aquí")
        }
    }
}