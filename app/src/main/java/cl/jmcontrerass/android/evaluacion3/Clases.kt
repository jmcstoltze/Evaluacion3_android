package cl.jmcontrerass.android.evaluacion3

// Constantes que representan las pantallas de la app
enum class Pantalla {
    FORM,
    FOTO,
    FOTOINDIVIDUAL,
    MAPA,
    DENEGADO
}

// Gestión de excepción
class SinPermisoException(mensaje:String) : Exception(mensaje)