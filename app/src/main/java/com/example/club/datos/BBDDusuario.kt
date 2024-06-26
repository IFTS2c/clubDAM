package com.example.club

import UsuarioDB
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.ArrayList


var bbdd="UsuarioDB";
//val usr:UsuarioDB= UsuarioDB()

class BBDDusuario(contexto: Context): SQLiteOpenHelper(contexto, bbdd, null,6) {
    override fun onCreate(db: SQLiteDatabase?) {
        //db?.execSQL("drop table if exists UsuarioDB")
        val crearTablaUsr ="create table UsuarioDB(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username VARCHAR(16), password VARCHAR(16), nombreApellido VARCHAR(30), " +
                "dni VARCHAR(9), email VARCHAR(30), asociado INTEGER, codAct INTEGER, " +
                "categoria VARCHAR(1), FOREIGN KEY (codAct) REFERENCES ActividadDB (cod_actividad))"
        db?.execSQL(crearTablaUsr)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //db?.execSQL("alter table UsuarioDB add column nombreApellido varchar(30)")
    }

    fun insertar(usr: UsuarioDB):Boolean{
        val db = this.writableDatabase
        var contenedorValores = ContentValues()

        contenedorValores.put("username", usr.username)
        contenedorValores.put("password", usr.password)
        contenedorValores.put("nombreApellido", usr.nombreApellido)
        contenedorValores.put("dni", usr.dni)
        contenedorValores.put("email", usr.email)
        contenedorValores.put("asociado", usr.asociado)
        contenedorValores.put("codAct", usr.codAct)
        contenedorValores.put("categoria",usr.categoria)

        var resultado = db.insert("UsuarioDB", null, contenedorValores)
        if (resultado == -1.toLong()) {
            Log.i("CRUD", "Falló: ${usr.toString()}")
            return false
        } else {
            return true
        }
    }

    fun leer():MutableList<UsuarioDB>{
        var lista:MutableList<UsuarioDB> = ArrayList()
        var db =  this.readableDatabase
        val sql = "select * from UsuarioDB"
        var resultado = db.rawQuery(sql,null)

        if(resultado.moveToFirst()) {
            do{
                var usr = UsuarioDB()
                usr.id = resultado.getInt(0)
                usr.username = resultado.getString(1)
                usr.password = resultado.getString(2)
                usr.nombreApellido = resultado.getString(3)
                usr.dni = resultado.getString(4)
                usr.email = resultado.getString(5)
                usr.asociado = resultado.getInt(6) == 1
                usr.codAct = resultado.getInt(7)
                usr.categoria = resultado.getString(8)
                lista.add(usr)
            }while(resultado.moveToNext())
            resultado.close()
            db.close()
            return lista
        }
        return lista
    }

    fun leerUnDato(userName:String):UsuarioDB{
        var usRes:UsuarioDB = UsuarioDB()
        val db =  this.readableDatabase
        val sql = "select * from UsuarioDB where username = '${userName}'"
        var resultado = db.rawQuery(sql,null)
        if (resultado.moveToFirst()) {
            usRes.id = resultado.getInt(0)
            usRes.username =  resultado.getString(1)
            usRes.password = resultado.getString(2)
            usRes.nombreApellido = resultado.getString(3)
            usRes.dni = resultado.getString(4)
            usRes.email = resultado.getString(5)
            usRes.asociado = resultado.getInt(6)==1
            usRes.codAct = resultado.getInt(7)
            usRes.categoria = resultado.getString(8)
            Log.i("modulo1","LeerUno => id: ${usRes.id} username: ${usRes.username} pass: ${usRes.password} nomAp: ${usRes.nombreApellido}. dni: ${usRes.dni}, email: ${usRes.email}, asoc: ${usRes.asociado}, codAct: ${usRes.codAct}, categ: ${usRes.categoria}")
            resultado.close()
            return usRes
        }
        Log.i("modulo1","NO encontro nada")
        return usRes
    }
    fun existeUsrName(username:String): Boolean{
        val db =  this.readableDatabase
        val sql = "SELECT * FROM UsuarioDB WHERE username = '${username}'"
        var res: Cursor = db.rawQuery(sql,null)
        //var filasEncontradas:Int = db.count("UsuarioDB","username = ?", arrayOf(username))
        if (res.moveToFirst()) {
            res.close()
            return true
        } else {
            res.close()
            return false
        }
    }
    fun existeEmail(email:String): Boolean{
        val db =  this.readableDatabase
        val sql = "SELECT * FROM UsuarioDB WHERE email = '${email}'"
        var res: Cursor = db.rawQuery(sql,null)
        if (res.moveToFirst()) {
            res.close()
            return true
        } else {
            res.close()
            return false
        }
    }

    fun actualizar(id:String,username:String,password:String):String{
        val db = this.writableDatabase
        var contenedorValores = ContentValues()
        contenedorValores.put("username", username)
        contenedorValores.put("password", password)
        var resultado = db.update("UsuarioDB", contenedorValores, "id=?", arrayOf(id))

        if (resultado>0) {
            return "Actualizacion realizada"
        } else {
            return "No se realizó la actualización"
        }
    }

    fun borrar( id:Int){
        val db = this.writableDatabase
        if (id>0) {
            db.delete("UsuarioDB","id=?", arrayOf(id.toString()))
        }
    }


}