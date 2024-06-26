package com.example.club

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.club.tools.toolsVal
import androidx.appcompat.app.AlertDialog
import com.example.club.datos.BBDDactividad

class UserForm : AppCompatActivity() {
    val bdUsr = BBDDusuario(this)
    val bdAct = BBDDactividad(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username:String = intent.extras?.getString("username").orEmpty()
        //val nombreApellido:String = intent.extras?.getString("nombreApellido").orEmpty()
        val userSelected = bdUsr.leerUnDato(username)
        val userNameText = findViewById<TextView>(R.id.userName)
        val categoriaUser = findViewById<TextView>(R.id.categoria)
        val actividad = findViewById<TextView>(R.id.actividad)

        if (userSelected.asociado){
            categoriaUser.text = "Socio"
        } else {
            categoriaUser.text = "No Socio"
        }

        userNameText.text = "Hola " + userSelected.nombreApellido

        var act = bdAct.leerUnDato(userSelected.codAct)
        actividad.text = act?.nombre

        val btnActividad = findViewById<TextView>(R.id.btnActividad)
        val btnPagar = findViewById<TextView>(R.id.btnPagar)
        val btnCarnet = findViewById<TextView>(R.id.btnCarnet)

        btnActividad.setOnClickListener(){
            var intent = Intent(this,Actividades::class.java)
            intent.putExtra("username",username)
            startActivity(intent)
        }

        btnPagar.setOnClickListener(){
            var intent = Intent(this,Pagar::class.java)
            intent.putExtra("username",username)
            startActivity(intent)
        }

        btnCarnet.setOnClickListener(){
            var intent = Intent(this,Carnet::class.java)
            intent.putExtra("username",username)
            startActivity(intent)
        }



    }
    /*fun leerUnDato(username:String):UsuarioDB{
        var res:UsuarioDB = bdUsr.leerUnDato(username)
        Log.i("modulo1",res.toString())
        return res
    }*/
}