package com.example.club.datos

data class CuotaDB (
    val id_cuota:Int,
    val id_usuario:Int,
    val fecha_vto:String,
    val estado_de_pago:Boolean,
    val deuda:Double
)