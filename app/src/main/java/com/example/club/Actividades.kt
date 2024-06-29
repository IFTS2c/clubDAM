package com.example.club

import android.content.Context
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
import com.example.club.datos.BBDDcuota
import com.example.club.datos.DataBaseHelper

class Actividades : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper
    private lateinit var bdUs: BBDDusuario
    private lateinit var bdAct: BBDDactividad
    private lateinit var bdCuo: BBDDcuota
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_actividades)
        dbHelper = DataBaseHelper(this)
        bdUs = BBDDusuario(dbHelper)
        bdAct = BBDDactividad(dbHelper)
        bdCuo = BBDDcuota(dbHelper)
        val username:String = intent.extras?.getString("username").orEmpty()
        val userSelected = bdUs.leerUnDato(username)

        initActividadesRecyclerView(userSelected.id)

        val categGloval = getSharedPreferences("catGloval", Context.MODE_PRIVATE)
        val catGloval = categGloval.getString("categoria", null)

        val userNameText = findViewById<TextView>(R.id.userName)
        val categoriaUser = findViewById<TextView>(R.id.categoria)

        if (userSelected.asociado){
            categoriaUser.text = "Socio"
        } else {
            categoriaUser.text = "No Socio"
        }

        userNameText.text = userSelected.nombreApellido

    }

    private fun initActividadesRecyclerView(userId:Int){
        val actividadesListCompleta = bdAct.leerDatos()
        val actividadesList = actividadesListCompleta.drop(1)
        val recyclerView = findViewById<RecyclerView>(R.id.rvActividades)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ActAdapter(actividadesList, userId)
    }

}