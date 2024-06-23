package com.example.club.datos

data class ActividadDB (
    var cod_actividad : Int,
    var nombre : String,
    var dia : String,
    var horario : String,
    var cupo : Int,
    var precio_Socio : Double,
    var precio_No_Socio : Double
)