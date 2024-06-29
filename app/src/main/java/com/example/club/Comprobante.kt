package com.example.club

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.club.datos.ActividadDB
import com.example.club.datos.BBDDactividad
import com.example.club.datos.BBDDcuota
import com.example.club.datos.DataBaseHelper

class Comprobante : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper
    private lateinit var bdUs: BBDDusuario
    private lateinit var bdAct: BBDDactividad
    private lateinit var bdCuo: BBDDcuota

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comprobante)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbHelper = DataBaseHelper(this)
        bdUs = BBDDusuario(dbHelper)
        bdAct = BBDDactividad(dbHelper)
        bdCuo = BBDDcuota(dbHelper)

        val userId = intent.extras!!.getInt("userId", 0)
        val cod_act = intent.extras!!.getInt("codAct", 0)
        val monto = intent.extras!!.getDouble("monto", 0.0)
        Log.i("montopagar", "Comprobante antes ${monto}")
        val formaDePago = intent.extras!!.getString("formaPago", "")

        var userSelected = bdUs.leerUnDato(userId)
        var actividadSelected = bdAct.leerUnDato(cod_act)

        var nombreBox = findViewById<TextView>(R.id.userName)
        var asociadBox = findViewById<TextView>(R.id.asociadoOno)
        var actividad = findViewById<TextView>(R.id.textActividad)
        var montoTv = findViewById<TextView>(R.id.montoAPagar)
        var formaDePagoTv = findViewById<TextView>(R.id.formaDePago)
        val btnAtras = findViewById<TextView>(R.id.btnAtras)
        val btnGuardar = findViewById<TextView>(R.id.btnGuardar)

        nombreBox.text = userSelected.nombreApellido
        asociadBox.text = if(userSelected.asociado) "Socio" else "No Socio"
        actividad.text = actividadSelected?.nombre
        montoTv.text = "Monto: $ ${monto}"
        formaDePagoTv.text = formaDePago

    }
}