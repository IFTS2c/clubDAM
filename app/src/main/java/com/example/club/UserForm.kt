package com.example.club

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.club.tools.toolsVal
import androidx.appcompat.app.AlertDialog
import com.example.club.datos.BBDDactividad
import com.example.club.datos.BBDDcuota
import com.example.club.datos.DataBaseHelper
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UserForm : AppCompatActivity() {
    private lateinit var dbHelper: DataBaseHelper
    private lateinit var bdUs: BBDDusuario
    private lateinit var bdAct: BBDDactividad
    private lateinit var bdCuo: BBDDcuota

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
        dbHelper = DataBaseHelper(this)
        bdUs = BBDDusuario(dbHelper)
        bdAct = BBDDactividad(dbHelper)
        bdCuo = BBDDcuota(dbHelper)

        val username:String = intent.extras?.getString("username").orEmpty()
        //val nombreApellido:String = intent.extras?.getString("nombreApellido").orEmpty()
        val userSelected = bdUs.leerUnDato(username)
        val userNameText = findViewById<TextView>(R.id.userName)
        val asociadoUser = findViewById<TextView>(R.id.asociado)
        val actividad = findViewById<TextView>(R.id.actividad)
        val vtoTv = findViewById<TextView>(R.id.tvVto)

        val formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        if (userSelected.asociado){
            asociadoUser.text = "Socio"
            var cuota = bdCuo.buscarUltimaCuota(userSelected.id)
            var deuda = cuota?.deuda

            if (cuota != null) {
                var fechaVtoParsed = LocalDate.parse(cuota.fecha_vto)
                var fechaVto = fechaVtoParsed.format(formatoFecha)
                vtoTv.text = "Ultimo vto.: ${fechaVto}"
                var fechaVtoCuota = LocalDate.parse(cuota!!.fecha_vto)
                var fechaHoy = LocalDate.now()
                Log.i("fecha", "LocalDate.now ${fechaHoy}")
                //val formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                //var fechaCuotaFormated = cuota!!.fecha_vto.format(formatoFecha)

                Log.i("fecha", "fechaCuotaParsed ${fechaVtoCuota}, hoy ${fechaHoy}")
                Log.i("fecha", ".fecha_vto < fechaHoy]] ${fechaHoy.isBefore(fechaVtoCuota)}")
                if (!fechaHoy.isBefore(fechaVtoCuota)){

                    if (deuda != null && deuda > 0.0 && fechaHoy.dayOfMonth in 10 .. 27) { // > 10) {
                        dialogDeuda(this@UserForm, userSelected.id, userSelected.codAct, deuda)
                    }
                    if (deuda != null && deuda > 0.0 && userSelected.codAct == 1) { // > 10) {
                        dialogDeuda(this@UserForm, userSelected.id, userSelected.codAct, deuda)
                    }
                    if (userSelected.codAct > 1 && fechaHoy.dayOfMonth > 27){//in 1 .. 11) {
                        var deudaParaDialog = 0.0
                        if (deuda != null && deuda > 0.0){
                            deudaParaDialog = deuda
                        }
                        dialogVencimiento(this@UserForm,userSelected.id, userSelected.codAct, deudaParaDialog)
                    }
                }
            }
        } else {
            var cuota = bdCuo.buscarUltimaCuota(userSelected.id)
            if (cuota != null) {
                var fechaVtoParsed = LocalDate.parse(cuota.fecha_vto)
                var fechaVto = fechaVtoParsed.format(formatoFecha)
                vtoTv.text = "Ultimo vto.: ${fechaVto}"
                asociadoUser.text = "No Socio"
            }
        }

        userNameText.text = "Hola " + userSelected.nombreApellido

        var act = bdAct.leerUnDato(userSelected.codAct)
        actividad.text = act?.nombre

        val btnActividad = findViewById<TextView>(R.id.btnActividad)
        val btnPagar = findViewById<TextView>(R.id.btnPagar)
        val btnCarnet = findViewById<TextView>(R.id.btnCarnet)

        btnActividad.setOnClickListener(){
            var intent = Intent(this,Actividades::class.java)
            intent.putExtra("username",userSelected.username)
            intent.putExtra("userId", userSelected.id)
            startActivity(intent)
        }

        btnPagar.setOnClickListener(){
            var intent = Intent(this,Pagar::class.java)
            intent.putExtra("username",userSelected.username)
            intent.putExtra("userId", userSelected.id)
            startActivity(intent)
        }

        btnCarnet.setOnClickListener(){
            var intent = Intent(this,Carnet::class.java)
            intent.putExtra("username",userSelected.username)
            intent.putExtra("userId", userSelected.id)
            startActivity(intent)
        }



    }


    fun dialogDeuda(context: Context, userId:Int, usrCodAct:Int, deuda:Double){
        val builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_deuda, null)
        builder.setView(view)
        val tvMontoDeuda = view.findViewById<TextView>(R.id.tvMontoDeuda)
        tvMontoDeuda.text = "Monto: $ ${deuda}0"
        val dialog = builder.create()
        dialog.show()

        var btnPagarDueda = view.findViewById<TextView>(R.id.btnPagarDeuda)
        btnPagarDueda.setOnClickListener {
            val intent = Intent(context, Pagar::class.java)
            intent.putExtra("userId",userId)
            intent.putExtra("codAct", usrCodAct)
            context.startActivity(intent)
            dialog.dismiss()
        }
    }

    fun dialogVencimiento(context: Context, userId:Int, usrCodAct:Int, deuda:Double){
        val builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_renovar_cambiar_cancelar,null)
        builder.setView(view)
        val tvDeudaPrevia = view.findViewById<TextView>(R.id.tvDeudaPrevia)
        val tvNameAct = view.findViewById<TextView>(R.id.tvNameActividad)
        val btnRenovar = view.findViewById<TextView>(R.id.btnRenovAct)
        val btnCambiar = view.findViewById<TextView>(R.id.btnCambiarAct)
        val btnCancelar = view.findViewById<TextView>(R.id.btnCancelarAct)

        val usuarioSelected = bdUs.leerUnDato(userId)
        val actividadSelected = bdAct.leerUnDato(usrCodAct)


        tvNameAct.text = actividadSelected?.nombre
        tvDeudaPrevia.text = "Deuda previa: $ ${deuda}0"

        val dialog = builder.create()
        dialog.show()

        btnRenovar.setOnClickListener {
            val intent = Intent(context, Pagar::class.java)
            intent.putExtra("userId",userId)
            intent.putExtra("codAct", usrCodAct)
            context.startActivity(intent)
            dialog.dismiss()
        }
        btnCambiar.setOnClickListener {
            val intent = Intent(context, Actividades::class.java)
            intent.putExtra("username",usuarioSelected.username)
            intent.putExtra("codAct", usrCodAct)
            context.startActivity(intent)
            dialog.dismiss()
        }
        btnCancelar.setOnClickListener {
            bdUs.actualizar(usuarioSelected.id, mapOf("codAct" to 1))
            val intent = Intent(context, UserForm::class.java)
            intent.putExtra("username",usuarioSelected.username)
            intent.putExtra("codAct", usrCodAct)
            context.startActivity(intent)
            dialog.dismiss()
        }
    }
}