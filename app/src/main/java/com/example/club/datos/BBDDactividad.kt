package com.example.club.datos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.database.getDoubleOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull

var bbdd="ActividadDB";

class BBDDactividad(contexto: Context): SQLiteOpenHelper(contexto, bbdd, null,2) {
    override fun onCreate(db: SQLiteDatabase?) {
        //db?.execSQL("drop table if exists UsuarioDB")
        val crearTablaUsr ="create table ActividadDB(cod_act INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre VARCHAR(16), dia VARCHAR(16), horario VARCHAR(5), " +
                "cupo INTEGER, precio_socio DOUBLE, precio_no_socio DOUBLE)"
        db?.execSQL(crearTablaUsr)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertar (nombre:String, dia:String, horario:String, cupo:Int,
                  precio_socio:Double, precio_no_socio:Double): Boolean{
        val db = this.writableDatabase
        var contenedor = ContentValues()
        contenedor.put("nombre", nombre)
        contenedor.put("dia", dia)
        contenedor.put("horario",horario)
        contenedor.put("cupo", cupo)
        contenedor.put("precio_socio",precio_socio)
        contenedor.put("precio_no_socio",precio_no_socio)
        var res = db.insert("ActividadDB",null,contenedor)
        if (res == -1.toLong()){
            Log.i("CRUD", "Falló insert actividad: ${nombre}")
            return false
        } else {
            return true
        }
    }

    fun leerUnDato(codAct: Int): ActividadDB? {
        val db = this.readableDatabase
        var res = db.rawQuery("SELECT * FROM ActividadDB WHERE cod_act = '${codAct}'", null)
        //res.close()
        return try {
            if (res.moveToFirst()) {
                val codAct = res.getInt(res.getColumnIndexOrThrow("cod_act"))
                val nombre = res.getString(res.getColumnIndexOrThrow("nombre"))
                val dia = res.getString(res.getColumnIndexOrThrow("dia"))
                val horario = res.getString(res.getColumnIndexOrThrow("horario"))
                val cupo = res.getInt(res.getColumnIndexOrThrow("cupo"))
                val precio_socio = res.getDouble(res.getColumnIndexOrThrow("precio_socio"))
                val precio_no_socio = res.getDouble(res.getColumnIndexOrThrow("precio_no_socio"))
                ActividadDB(codAct, nombre, dia, horario, cupo, precio_socio, precio_no_socio)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e ("CRUD","Error al leer ActividadDB ${e.message}")
            null
        } finally {
            res.close()
        }
    }

    fun leerDatos(): MutableList<ActividadDB> {
        val db = this.readableDatabase
        var res = db.rawQuery("SELECT * FROM ActividadDB", null)
        var listaActividades:MutableList<ActividadDB> = mutableListOf()
        return try {
            if (res.moveToFirst()) {
                do {
                    val codAct = res.getInt(res.getColumnIndexOrThrow("cod_act"))
                    val nombre = res.getString(res.getColumnIndexOrThrow("nombre"))
                    val dia = res.getString(res.getColumnIndexOrThrow("dia"))
                    val horario = res.getString(res.getColumnIndexOrThrow("horario"))
                    val cupo = res.getInt(res.getColumnIndexOrThrow("cupo"))
                    val precio_socio = res.getDouble(res.getColumnIndexOrThrow("precio_socio"))
                    val precio_no_socio = res.getDouble(res.getColumnIndexOrThrow("precio_no_socio"))
                    val Actividad = ActividadDB(codAct, nombre, dia, horario, cupo, precio_socio, precio_no_socio)
                    listaActividades.add(Actividad)
                } while (res.moveToNext())
                return listaActividades
            } else {
                return listaActividades
            }
        } catch (e: Exception) {
            Log.e ("CRUD","Error al leer ActividadDB ${e.message}")
            return listaActividades
        } finally {
            res.close()
        }
    }

    fun actualizar(codAct:Int, newValues: Map<String, Any?>) : Boolean {
        val db = this.writableDatabase
        val contenedor = ContentValues()
        for ((columna, valor) in newValues) {
            when (valor) {
                is String -> contenedor.put(columna,valor)
                is Int -> contenedor.put(columna, valor)
                is Double -> contenedor.put(columna, valor)
            }
        }
        val res = db.update("ActividadDB", contenedor,
            "cod_act = ?", arrayOf(codAct.toString()))
        if (res > 0) {
            Log.i("CRUD","Actualizacion exitosa actividad ${codAct}")
            return true
        } else {
            Log.i("CRUD","Actualizacion fallida")
            return false
        }
    }

    fun borrar( codAct:Int): Boolean {
        val db = this.writableDatabase
        if (codAct > 0) {
            var res = db.delete("UsuarioDB", "id=?", arrayOf(codAct.toString()))
            if (res > 0) {
                Log.i("CRUD", "Borrado exitoso, actividad ${codAct}")
                return true
            } else {
                Log.i("CRUD", "Borrado fallido")
                return false
            }
        } else {
            return false
        }
    }
}

