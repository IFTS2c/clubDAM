package com.example.club.datos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.database.getDoubleOrNull

val bdCuota = "CuotaDB"

class BBDDcuota(contexto: Context): SQLiteOpenHelper(contexto, bdCuota,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaCuota = "CREATE TABLE CuotadDB(id_cuota INTEGER PRIMARY KEY AUTOINCREMENT, id_usuario INTEGER," +
                "fecha_vto VARCHAR(10), estado_de_pago BOOLEAN, deuda DOUBLE, " +
                "FOREIGN KEY (id_usuario) REFERENCES UsuarioDB(id))"
        db?.execSQL(crearTablaCuota)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertar(cuota: CuotaDB):Boolean{
        val db = this.writableDatabase
        val contenedorValores = ContentValues()

        contenedorValores.put("id_cuota", cuota.id_cuota)
        contenedorValores.put("id_usuario", cuota.id_usuario)
        contenedorValores.put("fecha_vto", cuota.fecha_vto)
        contenedorValores.put("estado_de_pago", cuota.estado_de_pago)
        contenedorValores.put("deuda", cuota.deuda)

        val resultado = db.insert("CuotaDB", null, contenedorValores)
        if (resultado == -1.toLong()) {
            Log.i("CRUD", "Falló insercion cuota: ${cuota.toString()}")
            return false
        } else {
            return true
        }
    }

    fun leerUnDato(id_cuota: Int): CuotaDB? {
        val db = this.readableDatabase
        var res = db.rawQuery("SELECT * FROM CuotaDB WHERE id_cuota = '${id_cuota}'", null)
        //res.close()
        return try {
            if (res.moveToFirst()) {
                val id_cuota = res.getInt(res.getColumnIndexOrThrow("id_cuota"))
                val id_usuario = res.getInt(res.getColumnIndexOrThrow("id_usuario"))
                val fecha_vto = res.getString(res.getColumnIndexOrThrow("fecha_vto"))
                val estado_de_pago = res.getString(res.getColumnIndexOrThrow("estado_de_pago")) == "true"
                val deuda = res.getDouble(res.getColumnIndexOrThrow("deuda"))
                CuotaDB(id_cuota, id_usuario, fecha_vto, estado_de_pago, deuda)
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

    fun leerDatos(): MutableList<CuotaDB> {
        val db = this.readableDatabase
        var res = db.rawQuery("SELECT * FROM CuotaDB", null)
        var listaCuotas:MutableList<CuotaDB> = mutableListOf()
        return try {
            if (res.moveToFirst()) {
                do {
                    val id_cuota = res.getInt(res.getColumnIndexOrThrow("id_cuota"))
                    val id_usuario = res.getInt(res.getColumnIndexOrThrow("id_usuario"))
                    val fecha_vto = res.getString(res.getColumnIndexOrThrow("fecha_vto"))
                    val estado_de_pago = res.getString(res.getColumnIndexOrThrow("estado_de_pago")) == "true"
                    val deuda = res.getDouble(res.getColumnIndexOrThrow("deuda"))
                    var cuota = CuotaDB(id_cuota, id_usuario, fecha_vto, estado_de_pago, deuda)
                    listaCuotas.add(cuota)
                } while (res.moveToNext())
                return listaCuotas
            } else {
                return listaCuotas
            }
        } catch (e: Exception) {
            Log.e ("CRUD","Error al leer ActividadDB ${e.message}")
            return listaCuotas
        } finally {
            res.close()
        }
    }

    fun actualizar(id_cuota: Int, newValues: Map<String, Any?>) : Boolean {
        val db = this.writableDatabase
        val contenedor = ContentValues()
        for ((columna, valor) in newValues) {
            when (valor) {
                is String -> contenedor.put(columna,valor)
                is Int -> contenedor.put(columna, valor)
                is Double -> contenedor.put(columna, valor)
                is Boolean -> contenedor.put(columna, valor)
            }
        }
        val res = db.update("CuotaDB", contenedor,
            "id_cuota = ?", arrayOf(id_cuota.toString()))
        if (res > 0) {
            Log.i("CRUD","Actualizacion exitosa cuota ${id_cuota}")
            return true
        } else {
            Log.i("CRUD","Actualizacion fallida")
            return false
        }
    }

}
