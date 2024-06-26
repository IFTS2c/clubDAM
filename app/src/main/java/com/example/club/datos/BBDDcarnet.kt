package com.example.club.datos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import android.graphics.Bitmap
import android.util.Log
import com.journeyapps.barcodescanner.BarcodeEncoder

val bdCarnet = "CarnetDB"

class BBDDcarnet(contexto: Context) : SQLiteOpenHelper(contexto, bdCarnet, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaCarnet = "CREATE TABLE CarnetDB (id_usuario INTEGER PRIMARY KEY, qr_code BLOB)"
        db?.execSQL(crearTablaCarnet)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }



}