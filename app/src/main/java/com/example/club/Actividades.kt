package com.example.club

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.club.datos.BBDDactividad

class Actividades : AppCompatActivity() {

    val bdUsr = BBDDusuario(this)
    val bdAct = BBDDactividad(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_actividades)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username:String = intent.extras?.getString("username").orEmpty()
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

        val recyclerView = findViewById<RecyclerView>(R.id.rvActividades)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        //recyclerView.adapter = actAdapter(actCards)
    }
}