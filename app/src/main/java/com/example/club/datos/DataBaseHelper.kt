package com.example.club.datos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.time.LocalDate


class DataBaseHelper (contexto: Context): SQLiteOpenHelper(contexto, "database", null, 1 ){
    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaCuota = "CREATE TABLE CuotaDB(id_cuota INTEGER PRIMARY KEY AUTOINCREMENT, id_usuario INTEGER," +
                "fecha_vto VARCHAR(10), estado_de_pago INTEGER, deuda DOUBLE, " +
                "FOREIGN KEY (id_usuario) REFERENCES UsuarioDB(id))"
        db?.execSQL(crearTablaCuota)

        val crearTablaUsr ="create table UsuarioDB(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username VARCHAR(16), password VARCHAR(16), nombreApellido VARCHAR(30), " +
                "dni VARCHAR(9), email VARCHAR(30), asociado INTEGER, codAct INTEGER, " +
                "categoria VARCHAR(1), FOREIGN KEY (codAct) REFERENCES ActividadDB (cod_actividad))"
        db?.execSQL(crearTablaUsr)

        val crearTablaActividad ="create table ActividadDB(cod_act INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre VARCHAR(16), dia VARCHAR(16), horario VARCHAR(5), " +
                "cupo INTEGER, precio_socio DOUBLE, precio_no_socio DOUBLE)"
        db?.execSQL(crearTablaActividad)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}