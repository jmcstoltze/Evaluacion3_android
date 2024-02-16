package cl.jmcontrerass.android.evaluacion3

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.io.File
import java.time.LocalDateTime

// Crea un string con la fecha y hora actual quitando algunos caracteres
fun generarNombreSegunFechaHastaSegundo():String =
    LocalDateTime.now().toString().replace(
    Regex("[T:.-]"), "").substring(0, 14)

// Genera el archivo en base al string y la extensión .jpg
fun crearArchivoImagen(contexto: Context): File = File(
    contexto.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
    "${generarNombreSegunFechaHastaSegundo()}.jpg"
)

// Función para rotar la imagen bitmap en 90 grados sentido horario
fun rotateBitmap(imagenOriginal: Bitmap): Bitmap {
    val matrix = android.graphics.Matrix()
    matrix.postRotate(90f)
    return Bitmap.createBitmap(
        imagenOriginal,
        0,
        0,
        imagenOriginal.width,
        imagenOriginal.height,
        matrix,
        true
    )
}

// Convierte una URI de imagen en un objeto ImageBitmap rotado
fun uri2imageBitmap(uri: Uri, contexto: Context): ImageBitmap {
    // Toma la imagen y la decodifica a bitmap
    val imagenOriginal = BitmapFactory.decodeStream(contexto.contentResolver.openInputStream(uri))

    // Rota la imagen invocando la función correspondiente
    val imagenRotada = rotateBitmap(imagenOriginal)

    // retorna el resultado como una imagen de bitmap
    return imagenRotada.asImageBitmap()
}

// Captura de la imagen y almacenamiento de la misma en una lista
fun tomarFotografia(
    cameraController:CameraController,
    formRegistroVm:FormRegistroViewModel,
    archivo:File,
    contexto:Context,
    imagenGuardadaOk:(uri:Uri)->Unit)
{
    val outputFileOptions = ImageCapture.OutputFileOptions.Builder(archivo).build()
    cameraController.takePicture(
        outputFileOptions, ContextCompat.getMainExecutor(contexto),
        object: ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                outputFileResults.savedUri?.also {
                    Log.v("tomarFotografia()::onImageSaved", "Foto guardada en ${it.toString()}")

                    // Llama al dato de función con la uri que recibe
                    imagenGuardadaOk(it)

                    // Agrega la imagen a la lista de fotos capturadas
                    formRegistroVm.fotosCapturadas.value =
                        formRegistroVm.fotosCapturadas.value.toMutableStateList().apply {
                            add(it)
                        }
                }
            }
            // Manejo de excepción
            override fun onError(exception: ImageCaptureException) {
                Log.e("tomarFotografia()", "Error: ${exception.message}")
            }
        })
}

// Obtiene la ubicación en coordenadas
fun getUbicacion(contexto: Context, onUbicacionOk:(location: Location) -> Unit):Unit {
    try {
        val servicio = LocationServices.getFusedLocationProviderClient(contexto)
        val tarea = servicio.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
        tarea.addOnSuccessListener {
            onUbicacionOk(it)
        }
    } catch (e:SecurityException) {
        throw SinPermisoException(e.message?:"No tiene permisos para conseguir la ubicación")
    }
}