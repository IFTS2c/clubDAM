package com.example.club.datos

import UsuarioDB
import android.content.Context
import android.util.Log
import com.example.club.BBDDusuario

class CargaInicialBD (context: Context){

    val BDusr = BBDDusuario(context)
    val BDact = BBDDactividad(context)

    var buscarUsuarios:MutableList<UsuarioDB> = BDusr.leer()
    var buscarActividades:MutableList<ActividadDB> = BDact.leerDatos()

    fun hayUsuariosOCargar(res : MutableList<UsuarioDB>){
        if (res.isEmpty()) {
            crearDatos("tiagomar", "tiagomar", "Tiago Martin", "13333334", "tianro@gmail.com", false,"c")
            crearDatos("maridelro", "maridelro", "Maria del Rosario", "13333334", "mariarosario@gmail.com", true,"c")
            crearDatos("administrador","admini","Jose Alvarez", "12123123", "jesalv@gmail.com", false, "e")
        }
    }

    fun hayActividadesOCargar(res : MutableList<ActividadDB>){
        if (res.isEmpty()) {
            BDact.insertar("no hay actividad asignada"," "," ",0,0.0,0.0)
            BDact.insertar("Funcional", "lu-mi-vi", "18:00",2, 3000.0, 1000.0)
            BDact.insertar("Yoga", "ma-ju-sa", "19:00", 1, 3500.0, 1200.0)
            BDact.insertar("Pilates", "lu-mi-vi", "10:00", 2, 3500.0, 1200.0)
            BDact.insertar("Spinning", "ma-ju-sa", "9:00", 1, 3000.0, 1000.0)
            BDact.insertar("Dance", "lu-mi-vi", "20:00", 1, 3000.0, 1000.0)
            BDact.insertar("Streaching", "ma-ju-sa", "11:00", 0, 3700.0, 1300.0)
        }
    }

    fun iniciarOnoBBDD(){
        Log.i("CRUD", "[[CargaInicial desde MAIN]]")
        hayUsuariosOCargar(buscarUsuarios)
        hayActividadesOCargar(buscarActividades)
        //buscarActividades?.let { hayActividadesOCargar(it) }
    }

    private fun crearDatos(username:String, password:String, nombreApellido:String, dni:String, email:String, asociado:Boolean, categoria:String):Boolean{
        val usr = UsuarioDB(username,password,nombreApellido, dni, email, asociado, categoria)
        val res = BDusr.insertar(usr)
        Log.i("CRUD", "[[CargaInicial]] ${res.toString()}")
        return res
    }
}