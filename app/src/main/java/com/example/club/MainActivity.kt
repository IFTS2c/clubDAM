package com.example.club

import UsuarioDB
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.club.datos.BBDDactividad
import com.example.club.datos.BBDDcuota
import com.example.club.datos.CargaInicialBD
import com.example.club.datos.DataBaseHelper
import com.example.club.tools.toolsVal

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper
    private lateinit var bdUs: BBDDusuario
    private lateinit var bdAct: BBDDactividad
    private lateinit var bdCuo: BBDDcuota

    var t = toolsVal()
    private lateinit var d: CargaInicialBD


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbHelper = DataBaseHelper(this)
        bdUs = BBDDusuario(dbHelper)
        bdAct = BBDDactividad(dbHelper)
        bdCuo = BBDDcuota(dbHelper)

        d = CargaInicialBD(this)


// INICIALIZACION DE REGISTROS EN bd SI ES QUE NO HAY NADA
        d.iniciarOnoBBDD()

// IMPRESION EN LOGCAT DE REGISTROS EN BD
        var act = bdAct.leerDatos()
        if (act != null) {
            for (i in act){
                Log.i("CRUD","registro act. ${i}")
            }
        } else {
            Log.i("CRUD","no hay registros de actividades")
        }
        var us = bdUs.leer()
        if (us != null) {
            for (i in us){
                Log.i("CRUD","registro act. ${i}")
            }
        } else {
            Log.i("CRUD","no hay registros de usuarios")
        }
        var cu = bdCuo.leerDatos()
        if (cu != null) {
            for (i in cu){
                Log.i("CRUD","registro act. ${i}")
            }
        } else {
            Log.i("CRUD","no hay registros de usuarios")
        }

// FORZANDO ENTRADA DIRECRTA PARA TESTEAR
//        var intent = Intent(this,AdminForm::class.java)
//        intent.putExtra("adminName","Jose Alvarez")
//        //intent.putExtra("codAct",8)
//        startActivity(intent)

// LOGICA DE ACTIVITY MAIN
        val btnLogin = findViewById<AppCompatButton>(R.id.btnLogin)
        val btnReg = findViewById<AppCompatButton>(R.id.btnReg)
        var textUser: EditText = findViewById<AppCompatEditText>(R.id.inputUser)
        var textPass:EditText = findViewById<AppCompatEditText>(R.id.inputPass)
        textUser.text.clear()
        textPass.text.clear()


        btnLogin.setOnClickListener{
            var inputUser:String = textUser.text.toString()
            var inputPass:String = textPass.text.toString()

            if (inputUser.isNotEmpty() && inputPass.isNotEmpty()){
                val solicRead:UsuarioDB = bdUs.leerUnDato(inputUser)
                Log.i("LOGIN","solicRead ${solicRead.toString()}, inputPass ${inputPass}")
                if (solicRead.password == inputPass){
                    if (solicRead.categoria == "c") {
                        textUser.text.clear()
                        textPass.text.clear()
                        val intent = Intent(this, UserForm::class.java)
                        intent.putExtra("username",solicRead.username)
                        intent.putExtra("nombreApellido",solicRead.nombreApellido)
                        categoriaGloval(solicRead.categoria)
                        startActivity(intent)
                    } else {
                        textUser.text.clear()
                        textPass.text.clear()
                        val intent = Intent(this, AdminForm::class.java)
                        intent.putExtra("username",solicRead.username)
                        intent.putExtra("nombreApellido",solicRead.nombreApellido)
                        categoriaGloval(solicRead.categoria)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this,"El usuario o clave son incorrectas",Toast.LENGTH_SHORT).show()
                    Log.i("LOGIN","Error en login")
                    textUser.text.clear()
                    textPass.text.clear()
                    textUser.requestFocus()
                    return@setOnClickListener
                }
            } else {
                Toast.makeText(this,"Tenés que ingresar usuario y clave",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        btnReg.setOnClickListener {
            val intent = Intent(this, RegUsrPassForm::class.java)
            startActivity(intent)
        }

    }


    private fun crearDatos(username:String, password:String, nombreApellido:String, dni:String, email:String, asociado:Boolean, categoria:String):Boolean{
        val usr = UsuarioDB(username,password,nombreApellido, dni, email, asociado, categoria)
        val res = bdUs.insertar(usr)
        Log.i("CRUD", "[[MAIN]] ${res.toString()}")
        return res
    }
    private fun categoriaGloval(categoria:String){
        val categGloval = getSharedPreferences("catGloval", Context.MODE_PRIVATE)
        val editor = categGloval.edit()
        editor.putString("categoria", categoria)
        editor.apply()
    }
}