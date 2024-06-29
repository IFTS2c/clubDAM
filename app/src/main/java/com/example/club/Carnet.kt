package com.example.club

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.club.datos.BBDDactividad
import com.example.club.datos.BBDDcuota
import com.example.club.datos.DataBaseHelper
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class Carnet : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper
    private lateinit var bdUs: BBDDusuario
    private lateinit var bdAct: BBDDactividad
    private lateinit var bdCuo: BBDDcuota

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carnet)
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

        var usr = bdUs.leerUnDato(username)
        var viewQR : ImageView = findViewById(R.id.ViewQR)
        var datos = usr.id.toString()

        var nombreApellidoBox = findViewById<TextView>(R.id.userName)
        var dniBox = findViewById<TextView>(R.id.dni)
        var asociadoBox = findViewById<TextView>(R.id.asociado)
        var idBox = findViewById<TextView>(R.id.idUsr)
        var codActvarBox = findViewById<TextView>(R.id.codAct)

        nombreApellidoBox.text = usr.nombreApellido
        dniBox.text = "DNI: ${usr.dni}"
        asociadoBox.text = if (usr.asociado == true) "Socio -" else "No Socio -"
        idBox.text = "  ID: ${usr.id.toString()}"
        codActvarBox.text = "Actividad: ${bdAct.leerUnDato(usr.codAct)?.nombre}"

        fun generarQr(){
            var barcodeEncoder : BarcodeEncoder = BarcodeEncoder()
            var bitmap : Bitmap = barcodeEncoder.encodeBitmap(
                datos,
                BarcodeFormat.QR_CODE,
                800,800
            )

            viewQR.setImageBitmap(bitmap)
        }

        generarQr()
    }
}