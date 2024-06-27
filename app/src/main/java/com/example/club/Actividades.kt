package com.example.club

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.club.adapter.ActAdapter
import com.example.club.datos.BBDDactividad
class Actividades : AppCompatActivity() {

    val bdUsr = BBDDusuario(this)
    val bdAct = BBDDactividad(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_actividades)
        initActividadesRecyclerView()

        val username:String = intent.extras?.getString("username").orEmpty()
        val userSelected = bdUsr.leerUnDato(username)
        val userNameText = findViewById<TextView>(R.id.userName)
        val categoriaUser = findViewById<TextView>(R.id.categoria)

        if (userSelected.asociado){
            categoriaUser.text = "Socio"
        } else {
            categoriaUser.text = "No Socio"
        }

        userNameText.text = userSelected.nombreApellido



    }

    private fun initActividadesRecyclerView(){
        val actividadesListCompleta = bdAct.leerDatos()
        val actividadesList = actividadesListCompleta.drop(1)
        val recyclerView = findViewById<RecyclerView>(R.id.rvActividades)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ActAdapter(actividadesList)
    }

}