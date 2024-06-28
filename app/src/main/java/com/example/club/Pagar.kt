package com.example.club

import UsuarioDB
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.club.datos.ActividadDB
import com.example.club.datos.BBDDactividad
import com.example.club.datos.BBDDcuota

class Pagar : AppCompatActivity() {

    val bdUsr = BBDDusuario(this)
    val bdAct = BBDDactividad(this)
    val bdCuo = BBDDcuota(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val categGloval = getSharedPreferences("catGloval", Context.MODE_PRIVATE)
        val catGloval = categGloval.getString("categoria", null)
        val userId = intent.extras!!.getInt("userId")
        val cod_act = intent.extras!!.getInt("codAct")
        val userSelected = bdUsr.leerUnDato(userId)
        val actividadSelected = bdAct.leerUnDato(cod_act)

        var nombreBox = findViewById<TextView>(R.id.userName)
        var asociadBox = findViewById<TextView>(R.id.asociadoOno)
        var actividad = findViewById<TextView>(R.id.textActividad)
        val btnAtras = findViewById<TextView>(R.id.btnAtras)
        val btnAceptar = findViewById<TextView>(R.id.btnAceptar)

        nombreBox.text = userSelected.nombreApellido
        if (catGloval == "e") {
            var radBntEfectivo = findViewById<RadioButton>(R.id.rbEfectivo)
            radBntEfectivo.visibility = View.VISIBLE
        }
        asociadBox.text = if (userSelected.asociado) "Socio" else "No socio"
        actividad.text = actividadSelected!!.nombre


// LOGICA DEUDA Y PAGAR
        if (!userSelected.asociado){
            pagaNoSocio(userSelected, actividadSelected)
        } else {
            pagaSocio(userSelected, actividadSelected)
        }
    }

    fun pagaNoSocio(user:UsuarioDB, actividad:ActividadDB){
        var montoAPagar = actividad.precio_no_socio
        var montoBox = findViewById<TextView>(R.id.montoAPagar)
        montoBox.text = "Monto: $ ${montoAPagar}"
    }
    fun pagaSocio(user:UsuarioDB, actividad:ActividadDB){
        var ultimaCuota = bdCuo.BuscarUltimaCuota(user.id)
        var deuda:Double = 0.0
        if (ultimaCuota == null) {
            deuda = 0.0
        } else {
            deuda = ultimaCuota.deuda
        }
        if (deuda > 0){
            Toast.makeText(this, "Tiene una deuda de ${ultimaCuota!!.deuda}", Toast.LENGTH_SHORT).show()

        }
        var montoAPagar = actividad.precio_socio + deuda
        var montoBox = findViewById<TextView>(R.id.montoAPagar)
        montoBox.text = "Monto: $ ${montoAPagar}"
    }
}