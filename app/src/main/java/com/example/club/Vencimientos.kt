package com.example.club

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.club.adapter.ActAdapter
import com.example.club.adapter.VtoAdapter
import com.example.club.datos.BBDDactividad
import com.example.club.datos.BBDDcuota
import com.example.club.datos.DataBaseHelper

class Vencimientos : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper
    private lateinit var bdUs: BBDDusuario
    private lateinit var bdAct: BBDDactividad
    private lateinit var bdCuo: BBDDcuota
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vencimientos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbHelper = DataBaseHelper(this)
        bdUs = BBDDusuario(dbHelper)
        bdAct = BBDDactividad(dbHelper)
        bdCuo = BBDDcuota(dbHelper)

        initVencimientosRecyclerView()

        var adminName = intent.getStringExtra("adminName")
        val tvNombre = findViewById<TextView>(R.id.userName)
        val tvasoc = findViewById<TextView>(R.id.titleAdmini)

        tvNombre.text = adminName
        tvasoc.text = "Administrador"
    }

    private fun initVencimientosRecyclerView(){

        val vencimientosList = bdCuo.buscarDeudasYVtos()
        val recyclerView = findViewById<RecyclerView>(R.id.rvVencimientos)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = VtoAdapter(vencimientosList)
    }
}