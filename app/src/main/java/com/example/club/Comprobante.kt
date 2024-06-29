package com.example.club

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.club.datos.ActividadDB
import com.example.club.datos.BBDDactividad
import com.example.club.datos.BBDDcuota

class Comprobante : AppCompatActivity() {

    val bdUsr = BBDDusuario(this)
    val bdAct = BBDDactividad(this)
    val bdCuo = BBDDcuota(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comprobante)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userId = intent.extras!!.getInt("userId", 0)
        val cod_act = intent.extras!!.getInt("codAct", 0)
        val monto = intent.extras!!.getString("monto", "")
        val formaDePago = intent.extras!!.getString("formaPago", "")

        var userSelected = bdUsr.leerUnDato(userId)
        var actividadSelected = bdAct.leerUnDato(cod_act)

        var nombreBox = findViewById<TextView>(R.id.userName)
        var asociadBox = findViewById<TextView>(R.id.asociadoOno)
        var actividad = findViewById<TextView>(R.id.textActividad)
        var montoTv = findViewById<TextView>(R.id.montoAPagar)
        var formaDePagoTv = findViewById<TextView>(R.id.formaDePago)
        val btnAtras = findViewById<TextView>(R.id.btnAtras)
        val btnPagar = findViewById<TextView>(R.id.btnGuardar)

        nombreBox.text = userSelected.nombreApellido
        asociadBox.text = if(userSelected.asociado) "Socio" else "No Socio"
        actividad.text = actividadSelected?.nombre
        montoTv.text = monto
        formaDePagoTv.text = formaDePago

    }
}