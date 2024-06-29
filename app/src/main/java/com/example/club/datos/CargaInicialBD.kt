package com.example.club.datos

import UsuarioDB
import android.content.Context
import android.util.Log
import com.example.club.BBDDusuario

class CargaInicialBD (context: Context){

    val BDusr = BBDDusuario(context)
    val BDact = BBDDactividad(context)
    val BDcuot = BBDDcuota(context)

    var buscarUsuarios:MutableList<UsuarioDB> = BDusr.leer()
    var buscarActividades:MutableList<ActividadDB> = BDact.leerDatos()
    var buscarCuotas:MutableList<CuotaDB> = BDcuot.leerDatos()

    fun hayUsuariosOCargar(res : MutableList<UsuarioDB>){
        if (res.isEmpty()) {
            crearDatosUs("tiagomar", "tiagomar", "Tiago Martin", "13333334", "tianro@gmail.com", false,"c")
            crearDatosUs("maridelro", "maridelro", "Maria del Rosario", "13333334", "mariarosario@gmail.com", true,"c")
            crearDatosUs("clarui", "clarui", "Clara Ruiz", "13333334", "clararuiz@gmail.com", true,"c")
            crearDatosUs("julrom", "julrom", "Julio Romero", "13333334", "julro@gmail.com", true,"c")
            crearDatosUs("carlala", "carlala", "Carla Latorre", "13333334", "carlala@gmail.com", true,"c")
            crearDatosUs("raulji", "raulji", "Raul Jimenez", "13333334", "rauljim@gmail.com", true,"c")
            crearDatosUs("administrador","admini","Jose Alvarez", "12123123", "jesalv@gmail.com", false, "e")
            BDusr.actualizar(3, mapOf("codAct" to 5))
            BDusr.actualizar(2, mapOf("codAct" to 2))
            BDusr.actualizar(3, mapOf("codAct" to 4))
            BDusr.actualizar(5, mapOf("codAct" to 6))
        }
    }

    fun hayActividadesOCargar(res : MutableList<ActividadDB>){
        if (res.isEmpty()) {
            BDact.insertar("no hay actividad asignada"," "," ",2,0.0,0.0)
            BDact.insertar("Funcional", "lu-mi-vi", "18:00",2, 3000.0, 1000.0)
            BDact.insertar("Yoga", "ma-ju-sa", "19:00", 2, 3500.0, 1200.0)
            BDact.insertar("Pilates", "lu-mi-vi", "10:00", 2, 3500.0, 1200.0)
            BDact.insertar("Spinning", "ma-ju-sa", "9:00", 2, 3000.0, 1000.0)
            BDact.insertar("Dance", "lu-mi-vi", "20:00", 2, 3000.0, 1000.0)
            BDact.insertar("Stretching", "ma-ju-sa", "11:00", 2, 3700.0, 1300.0)
            BDact.insertar("Boulder", "lu-mi-vi", "19:00", 2, 4000.0, 1500.0)
            BDact.insertar("Aparatos", "ma-ju-sa", "0:00", 2, 3700.0, 1300.0)
        }
    }
    fun hayCuotasOCargar(res: MutableList<CuotaDB>) {
        if (res.isEmpty()) {
            BDcuot.insertar(1,"2024-06-01", false, 0.0)
            BDcuot.insertar(2,"2024-06-01", false, 3500.0)
            BDcuot.insertar(3,"2024-06-01", false, 3700.0)
            BDcuot.insertar(4,"2024-06-01", false, 4000.0)
            BDcuot.insertar(5,"2024-06-01", true, 0.0)
            //BDcuot.insertar(6,"1-6-2024", true, 0.0)
        }
    }

    fun iniciarOnoBBDD(){
        Log.i("CRUD", "[[CargaInicial desde MAIN]]")
        hayUsuariosOCargar(buscarUsuarios)
        hayActividadesOCargar(buscarActividades)
        //buscarActividades?.let { hayActividadesOCargar(it) }
        hayCuotasOCargar(buscarCuotas)
    }

    private fun crearDatosUs(username:String, password:String, nombreApellido:String, dni:String, email:String, asociado:Boolean, categoria:String):Boolean{
        val usr = UsuarioDB(username,password,nombreApellido, dni, email, asociado, categoria)
        val res = BDusr.insertar(usr)
        Log.i("CRUD", "[[CargaInicial]] ${res.toString()}")
        return res
    }
}