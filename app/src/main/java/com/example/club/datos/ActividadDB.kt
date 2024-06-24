package com.example.club.datos

data class ActividadDB (
    val cod_act : Int,
    val nombre : String,
    var dia : String,
    var horario : String,
    var cupo : Int,
    var precio_socio : Double,
    var precio_no_socio : Double
)