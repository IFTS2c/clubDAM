package com.example.club

import UsuarioDB
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.club.datos.ActividadDB
import com.example.club.datos.BBDDactividad
import com.example.club.datos.BBDDcuota
import com.example.club.datos.DataBaseHelper
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Pagar : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper
    private lateinit var bdUs: BBDDusuario
    private lateinit var bdAct: BBDDactividad
    private lateinit var bdCuo: BBDDcuota
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbHelper = DataBaseHelper(this)
        bdUs = BBDDusuario(dbHelper)
        bdAct = BBDDactividad(dbHelper)
        bdCuo = BBDDcuota(dbHelper)

        val categGloval = getSharedPreferences("catGloval", Context.MODE_PRIVATE)
        val catGloval = categGloval.getString("categoria", null)
        val userId = intent.extras!!.getInt("userId", 0)
        val cod_act = intent.extras!!.getInt("codAct", 0)
        val username = intent.extras!!.getString("username", "")
        var userSelected: UsuarioDB
        var actividadSelected: ActividadDB
//REVIASMOS SI ENTRAMOS A ESTA SECCION DESDE ACTIVIDAD CON UN USUARIO O A PAGAR UNA DEUDA O CON UN ADMINISTRADOR
        if (userId != 0 ) {
            userSelected = bdUs.leerUnDato(userId)
        } else {
            userSelected = bdUs.leerUnDato(username)
        }
        if (cod_act != 0) {
            actividadSelected = bdAct.leerUnDato(cod_act)!!
        } else {
            actividadSelected = bdAct.leerUnDato(userSelected.codAct)!!
        }

        var nombreBox = findViewById<TextView>(R.id.userName)
        var asociadBox = findViewById<TextView>(R.id.asociadoOno)
        var actividad = findViewById<TextView>(R.id.textActividad)
        val btnAtras = findViewById<TextView>(R.id.btnAtras)
        val btnPagar = findViewById<TextView>(R.id.btnPagar)

        nombreBox.text = userSelected.nombreApellido

        if (catGloval == "e") {
            var radBntEfectivo = findViewById<RadioButton>(R.id.rbEfectivo)
            radBntEfectivo.visibility = View.VISIBLE
        }
        asociadBox.text = if (userSelected.asociado) "Socio" else "No socio"
        actividad.text = actividadSelected.nombre
        var radioButtonId = findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId
        var formaDePago : String = ""
        if (radioButtonId != -1){
            var radiobutonSelected = findViewById<RadioButton>(radioButtonId)
            formaDePago = radiobutonSelected.text.toString()
        }


        var monto: Double = 0.0
        var montoBox = findViewById<TextView>(R.id.montoAPagar)
// LOGICA DEUDA Y PAGAR
        var cuota = bdCuo.buscarUltimaCuota(userSelected.id)
        var fechaHoy = LocalDate.now()
        if (cuota != null){
            var fechaVtoParsed = LocalDate.parse(cuota.fecha_vto)
            Log.i("pagar", "fechaVtoParsed.isAfter(fechaHoy) ${fechaVtoParsed.isAfter(fechaHoy) && cuota.deuda == 0.0}")
            if (fechaVtoParsed.isAfter(fechaHoy) && cuota.deuda == 0.0){
//                monto = 0.0
//                montoBox.text = "Monto: $ ${monto}0"
                Toast.makeText(this, "Tienes una cuota vigente, se vence ${cuota.fecha_vto}", Toast.LENGTH_SHORT).show()
                if (!userSelected.asociado){
                    Log.i("montopagar", "2 antes ${monto}")
                    monto = pagaNoSocio(userSelected, actividadSelected)
                    Log.i("montopagar", "2 despues ${monto}")
                } else {
                    Log.i("montopagar", "2 antes ${monto}")
                    monto = pagaSocio(userSelected, actividadSelected)
                    Log.i("montopagar", "2 despues ${monto}")
                }

            } else {
                //3
                if (!userSelected.asociado){
                    Log.i("montopagar", "3 antes ${monto}")
                    monto = pagaNoSocio(userSelected, actividadSelected)
                    Log.i("montopagar", "3 antes ${monto}")
                } else {
                    Log.i("montopagar", "3 antes ${monto}")
                    monto = pagaSocio(userSelected, actividadSelected)
                    Log.i("montopagar", "3 antes ${monto}")
                }
            }
        } else {
            //4
            if (actividadSelected != null){
                if (!userSelected.asociado){
                    Log.i("montopagar", "4 antes ${monto}")
                    monto = pagaNoSocio(userSelected, actividadSelected)
                    Log.i("montopagar", "4 despues ${monto}")
                } else {
                    Log.i("montopagar", "4 antes ${monto}")
                    monto = pagaSocio(userSelected, actividadSelected)
                    Log.i("montopagar", "4 despues ${monto}")
                }
                //montoBox.text = "Monto: $ ${monto}0"
            }
        }

        btnPagar.setOnClickListener {
            Log.i("montopagar", "Pagar antes ${monto}")
            pagar(userSelected.id, actividadSelected.cod_act, monto, formaDePago)
            Log.i("montopagar", "Pagar despues ${monto}")
        }
        btnAtras.setOnClickListener {
            finishAfterTransition()
        }


    }

    fun pagaNoSocio(user:UsuarioDB, actividad:ActividadDB):Double{
        var montoAPagar = actividad.precio_no_socio
        var montoBox = findViewById<TextView>(R.id.montoAPagar)
        montoBox.text = "Monto: $ ${montoAPagar}0"
        return montoAPagar
    }
    fun pagaSocio(user:UsuarioDB, actividad:ActividadDB):Double{
        var ultimaCuota = bdCuo.buscarUltimaCuota(user.id)
        var deuda:Double = 0.0
        if (ultimaCuota == null) {
            deuda = 0.0
        } else {
            deuda = ultimaCuota.deuda
        }
        if (deuda > 0){
            Toast.makeText(this, "Tiene una deuda de ${ultimaCuota!!.deuda}0", Toast.LENGTH_LONG).show()

        }
        var montoAPagar = actividad.precio_socio + deuda
        var montoBox = findViewById<TextView>(R.id.montoAPagar)
        montoBox.text = "Monto: $ ${montoAPagar}0"
        return montoAPagar
    }
    fun pagar(userId:Int, codAct:Int, monto:Double, formaDePago:String){
        //Log.i("actUsr", "anes ${bdUsr.leerUnDato(userId).codAct}")
        bdUs.actualizar(userId, mapOf("codAct" to codAct))
        //Log.i("actUsr", "despues ${bdUsr.leerUnDato(userId).codAct}")
        var asociado = bdUs.leerUnDato(userId).asociado

        var fecha_vto = if (asociado) LocalDate.now().plusMonths(1).withDayOfMonth(1) else
            LocalDate.now().plusDays(1)

        //Log.i("actUsr", "fechaFormato ${fecha_vto}")
        //Log.i("actUsr", "Cuota antes  ${bdCuo.buscarUltimaCuota(userId)}")
        bdCuo.insertar(userId, fecha_vto.toString(), true, 0.0)
        var actSelec = bdAct.leerUnDato(codAct)
        bdAct.actualizar(codAct, mapOf("cupo" to actSelec!!.cupo - 1))
        var intent = Intent(this@Pagar, Comprobante::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("codAct", codAct)
        intent.putExtra("monto", monto)
        intent.putExtra("formaPago", formaDePago)
        startActivity(intent)
    }
}