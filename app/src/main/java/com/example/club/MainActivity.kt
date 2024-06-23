package com.example.club

import UsuarioDB
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.club.tools.toolsVal

class MainActivity : AppCompatActivity() {

    var bbdd=BBDDusuario(this)
    var t = toolsVal()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        crearDatos("Seba", "dddd", "Seba Mam", "12312334", "SebMa@gmail.com", true, "c")
//        crearDatos("Tian", "ffff", "Tian Rod", "13333334", "tianro@gmail.com", false,"c")
//        crearDatos("jos","xxxx","Jose Alvarez", "12123123", "jesalv@gmail.com", false, "e")

        val btnLogin = findViewById<AppCompatButton>(R.id.btnLogin)
        val btnReg = findViewById<AppCompatButton>(R.id.btnReg)
        var etxUser: EditText = findViewById<AppCompatEditText>(R.id.inputUser)
//        etxUser.onFocusChangeListener = View.OnFocusChangeListener {
//                view, hasFocus -> if (hasFocus) { etxUser.hint = "" }
//        }
        var etxPass:EditText = findViewById<AppCompatEditText>(R.id.inputPass)
//        etxPass.onFocusChangeListener = View.OnFocusChangeListener {
//                view, hasFocus -> if (hasFocus) { etxPass.hint = "" }
//        }

        val us= bbdd.leerUnDato(etxUser.text.toString())

        btnLogin.setOnClickListener{
            val inputUser:String = etxUser.text.toString()
            val inputPass:String = etxPass.text.toString()

            if (inputUser.isNotEmpty() && inputPass.isNotEmpty()){
                val solicRead:UsuarioDB = bbdd.leerUnDato(inputUser)
                Log.i("Modulo1","solicRead ${solicRead.toString()}, inputPass ${inputPass}")
                if (solicRead.password == inputPass){
                    if (solicRead.categoria == "c") {
                        val intent = Intent(this, UserForm::class.java)
                        intent.putExtra("username",solicRead.username)
                        intent.putExtra("nombreApellido",solicRead.nombreApellido)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, AdminForm::class.java)
                        intent.putExtra("username",solicRead.username)
                        intent.putExtra("nombreApellido",solicRead.nombreApellido)
                        startActivity(intent)
                    }
                } else {
                    Log.i("Modulo1","Error en login")
                }
            } else {
                return@setOnClickListener
                ////startActivity(intent)
            }
        }

        btnReg.setOnClickListener {
            val intent = Intent(this, RegUsrPassForm::class.java)
            startActivity(intent)
        }

        /*fun leerDatos(username:String):UsuarioDB{
            var bbdd=BBDD(this)
            var res:UsuarioDB = bbdd.leerUno(username)
            Log.i("modulo1",res.toString())
            return res
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/



    }

    /*fun leerUnDato(username:String):UsuarioDB{
        val bbdd=BBDD(this)
        val res:UsuarioDB = bbdd.leerUnDato(username)
        Log.i("modulo1",res.toString())
        return res
    }

    fun leerDatos():MutableList<UsuarioDB>{
        val resList = bbdd.leer()
        if (resList.size>0) {
            for (i in 0..resList.size-1){
                Log.i("modulo1", "LeerTodos => id: ${resList[i].id} username: ${resList[i].username} pass: ${resList[i].password} nombreApellido: ${resList[i].nombreApellido} dni: ${resList[i].dni} email: ${resList[i].email} asociado: ${resList[i].asociado}")
            }
            return resList
        } else {
            Log.i("Modulo1", "no hay datos ${resList}")
            Toast.makeText(this, "No hay datos coincidentes con la busqueda", Toast.LENGTH_SHORT).show()
            return resList
        }
    }
    */
    private fun crearDatos(username:String, password:String, nombreApellido:String, dni:String, email:String, asociado:Boolean, categoria:String):Boolean{
        val usr = UsuarioDB(username,password,nombreApellido, dni, email, asociado, categoria)
        val res = bbdd.insertar(usr)
        Log.i("CRUD", "[[MAIN]] ${res.toString()}")
        return res
    }
}