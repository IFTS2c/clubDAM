package com.example.club.datos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

var bbdd="ActividadDB";

class BBDDactividad(contexto: Context): SQLiteOpenHelper(contexto, bbdd, null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        //db?.execSQL("drop table if exists UsuarioDB")
        val crearTablaUsr ="create table ActividadDB(cod_actividad INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre VARCHAR(16), dia VARCHAR(16), horario VARCHAR(5), " +
                "cupo INTEGER, precio_socio DOUBLE, precio_no_socio DOUBLE)"
        db?.execSQL(crearTablaUsr)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


}

