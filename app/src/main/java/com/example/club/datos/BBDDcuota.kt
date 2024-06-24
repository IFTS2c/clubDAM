package com.example.club.datos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val bdCuota = "CuotaDB"

class BBDDcuota(contexto: Context): SQLiteOpenHelper(contexto, bdCuota,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}

//
//id_cuota int auto_increment,
//id_usuario int,
//fecha_vto date,
//estado_de_pago boolean,
//deuda int,
//constraint pk_cuota primary key (id_cuota),
//constraint fk_usuario foreign key (id_usuario) references usuario(id_usuario)
//)engine=innodb;