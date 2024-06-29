package com.example.club.datos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.database.getDoubleOrNull
import java.time.LocalDate



class BBDDcuota(private val dbHelper: DataBaseHelper) {


    fun insertar(id_usuario:Int, fecha_vto:String, estado_de_pago:Boolean, deuda:Double):Boolean{
        val db = dbHelper.writableDatabase
        val contenedorValores = ContentValues()

        contenedorValores.put("id_usuario", id_usuario)
        contenedorValores.put("fecha_vto", fecha_vto)
        contenedorValores.put("estado_de_pago", estado_de_pago)
        contenedorValores.put("deuda", deuda)

        val resultado = db.insert("CuotaDB", null, contenedorValores)
        if (resultado == -1.toLong()) {
            Log.i("CRUD", "Falló insercion cuota ${id_usuario}")
            return false
        } else {
            return true
        }
    }

    fun leerUnDato(id_cuota: Int): CuotaDB? {
        val db = dbHelper.readableDatabase
        var res = db.rawQuery("SELECT * FROM CuotaDB WHERE id_cuota = '${id_cuota}'", null)
        return try {
            if (res.moveToFirst()) {
                val id_cuota = res.getInt(res.getColumnIndexOrThrow("id_cuota"))
                val id_usuario = res.getInt(res.getColumnIndexOrThrow("id_usuario"))
                val fecha_vto = res.getString(res.getColumnIndexOrThrow("fecha_vto"))
                val estado_de_pago = res.getInt(res.getColumnIndexOrThrow("estado_de_pago")) == 1
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
    fun buscarUltimaCuota(idUsuario: Int): CuotaDB? {
        val db = dbHelper.readableDatabase
        var res = db.rawQuery("SELECT * FROM CuotaDB WHERE id_usuario = '${idUsuario}' " +
                "ORDER By fecha_vto DESC LIMIT 1", null)
        return try {
            if (res.moveToFirst()) {
                val id_cuota = res.getInt(res.getColumnIndexOrThrow("id_cuota"))
                val id_usuario = res.getInt(res.getColumnIndexOrThrow("id_usuario"))
                val fecha_vto = res.getString(res.getColumnIndexOrThrow("fecha_vto"))
                val estado_de_pago = res.getInt(res.getColumnIndexOrThrow("estado_de_pago")) == 1
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
        val db = dbHelper.readableDatabase
        var res = db.rawQuery("SELECT * FROM CuotaDB", null)
        var listaCuotas:MutableList<CuotaDB> = mutableListOf()
        return try {
            if (res.moveToFirst()) {
                do {
                    val id_cuota = res.getInt(res.getColumnIndexOrThrow("id_cuota"))
                    val id_usuario = res.getInt(res.getColumnIndexOrThrow("id_usuario"))
                    val fecha_vto = res.getString(res.getColumnIndexOrThrow("fecha_vto"))
                    val estado_de_pago = res.getInt(res.getColumnIndexOrThrow("estado_de_pago")) == 1
                    Log.i("BBDDerror", "que devuelve el boolean ${id_usuario} eDP: ${estado_de_pago}")
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

    fun venceHoy(){
        val db = dbHelper.readableDatabase
        var hoy = LocalDate.now()
        try {
            var res = db.rawQuery(
                "SELECT * FROM CuotaDB as c INNER JOIN UsuarioDB as u ON c.id_usuario = u.id", null
            )
        } catch (e: Exception) {
            Log.e("TAG", "Error al ejecutar la consulta: ${e.message}")
        }
    }
    fun buscarDeudasYVtos(): MutableList<Deuda> {
        val db = dbHelper.readableDatabase
        var res = db.rawQuery(
            "SELECT u.id, u.nombreApellido, u.dni, u.email, c.deuda, c.fecha_vto " +
                    "FROM UsuarioDB as u " +
                    "JOIN CuotaDB as c ON u.id = c.id_usuario WHERE c.fecha_vto = (" +
                    "SELECT MAX(fecha_vto) FROM CuotaDB WHERE id_usuario = u.id) " +
                    "GROUP BY u.id, u.nombreApellido HAVING c.deuda > 0.0", null
        )

        var listaVto:MutableList<Deuda> = mutableListOf()
        if (res.moveToFirst()) {
            do {
                val userId = res.getInt(res.getColumnIndexOrThrow("id"))
                val nombreApellido = res.getString(res.getColumnIndexOrThrow("nombreApellido"))
                val dni = res.getString((res.getColumnIndexOrThrow("dni")))
                val email = res.getString(res.getColumnIndexOrThrow("email"))
                val deuda = res.getDouble(res.getColumnIndexOrThrow("deuda"))
                val fechaVto = res.getString(res.getColumnIndexOrThrow("fecha_vto"))
                var d = Deuda(userId, nombreApellido,dni,email,deuda,fechaVto)
                listaVto.add(d)
                Log.d(
                    "TAG",
                    "Id: $userId, Nombre y Apellido: $nombreApellido, Dni: $dni, Email: $email, Deuda: $deuda, Fecha Vto: $fechaVto"
                )
            } while (res.moveToNext())
        }
        res.close()
        return listaVto
    }
//        var listaCuotas:MutableList<CuotaDB> = mutableListOf()
//        return try {
//            if (res.moveToFirst()) {
//                do {
//                    val id_cuota = res.getInt(res.getColumnIndexOrThrow("id_cuota"))
//                    val id_usuario = res.getInt(res.getColumnIndexOrThrow("id_usuario"))
//                    val fecha_vto = res.getString(res.getColumnIndexOrThrow("fecha_vto"))
//                    val estado_de_pago = res.getInt(res.getColumnIndexOrThrow("estado_de_pago")) == 1
//                    Log.i("BBDDerror", "que devuelve el boolean ${id_usuario} eDP: ${estado_de_pago}")
//                    val deuda = res.getDouble(res.getColumnIndexOrThrow("deuda"))
//                    var cuota = CuotaDB(id_cuota, id_usuario, fecha_vto, estado_de_pago, deuda)
//                    listaCuotas.add(cuota)
//                } while (res.moveToNext())
//                return listaCuotas
//            } else {
//                return listaCuotas
//            }
//        } catch (e: Exception) {
//            Log.e ("CRUD","Error al leer ActividadDB ${e.message}")
//            return listaCuotas
//        } finally {
//            res.close()
//        }
//    }

    fun actualizar(id_cuota: Int, newValues: Map<String, Any?>) : Boolean {
        val db = dbHelper.writableDatabase
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
