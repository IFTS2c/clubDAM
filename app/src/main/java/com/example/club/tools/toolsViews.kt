package com.example.club.tools

import android.util.Log
import android.widget.Toast
import com.example.club.BBDDusuario

public class toolsVal {
    fun valid_not_start_num(into:String):Boolean{
        return (!into[0].isDigit() && !into[0].isWhitespace())
    }
    fun valid_is_Int(into:String):Boolean{
        return (into.toIntOrNull()!=null)
    }
    fun valid_have_no_whitespace(into:String):Boolean{
        var haveNoSpace = true
        for (i in into){
            if (i.isWhitespace()){
                haveNoSpace=false
            }
        }
        return haveNoSpace
    }
    fun valid_len_six(into:String):Boolean{
        return (into.length >= 6)
    }
    fun valid_have_Arrob_Dot(into:String):Boolean{
        var i = 1
        var haveArroba = false
        var haveDot = false
        do {
            if (into[i].toString() == "@") {
                haveArroba = true
            }
            if (haveArroba == true && into[i].toString() == ".") {
                haveDot = true
                return true
            }
            i += 1
        }  while (i < into.length-2)
        return false
    }


}