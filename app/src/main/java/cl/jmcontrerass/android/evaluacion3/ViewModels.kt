package cl.jmcontrerass.android.evaluacion3

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// Modelo de vista para valores de pantalla y permisos de la interfaz
class CameraAppViewModel : ViewModel() {
    val pantalla = mutableStateOf(Pantalla.FORM)

    // Callbacks
    var onPermisoCamaraOk : () -> Unit = {}
    var onPermisoUbicacionOk: () -> Unit = {}
    var onPermisoDenegado: () -> Unit = {}

    // Lanzador permisos
    var lanzadorPermisos: ActivityResultLauncher<Array<String>>? = null

    // Valor mutable para gestionar la denegación de permisos
    val permisoConcedido = mutableStateOf(true)

    // Cambia los valores de pantalla
    fun cambiarPantallaFoto(){ pantalla.value = Pantalla.FOTO }
    fun cambiarPantallaFotoIndividual(){ pantalla.value = Pantalla.FOTOINDIVIDUAL }
    fun cambiarPantallaForm(){ pantalla.value = Pantalla.FORM }
    fun cambiarPantallaMapa(){ pantalla.value = Pantalla.MAPA }
    fun cambiarPantallaDenegado() { pantalla.value = Pantalla.DENEGADO }

}

// Modelo de vista para los atributos del formulario
class FormRegistroViewModel : ViewModel() {

    // Subtítulo del álbum
    val lugar = mutableStateOf("")

    // Foto individual, lista de fotos e índice de la foto en la lista
    val fotoCapturada = mutableStateOf<Uri?>(null)
    val fotosCapturadas = mutableStateOf<List<Uri>>(emptyList())
    val indiceImagen = mutableStateOf<Int?>(null)

    // Relacionado con coordenadas del mapa
    val latitud = mutableStateOf(0.0)
    val longitud = mutableStateOf(0.0)
}