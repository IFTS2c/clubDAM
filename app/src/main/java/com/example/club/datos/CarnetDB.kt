package com.example.club.datos

import java.sql.Blob

data class CarnetDB(
    val id_usuario : Int,
    var imagenQR : Blob,
)
