package com.example.calculoresistenciascolores

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var SpnC1: Spinner? = null
    var SpnC2: Spinner? = null
    var SpnC3: Spinner? = null
    var SpnC4: Spinner? = null
    var BtnCallIt: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BtnCallIt = findViewById<View>(R.id.BtnCalculateIt) as Button

        SpnC1 = findViewById<View>(R.id.SpnColor1) as Spinner
        SpnC2 = findViewById<View>(R.id.SpnColor2) as Spinner
        SpnC3 = findViewById<View>(R.id.SpnColor3) as Spinner

        val adapter = ArrayAdapter.createFromResource(this,
                R.array.Colores1, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        SpnC1!!.adapter = adapter
        SpnC2!!.adapter = adapter
        SpnC3!!.adapter = adapter

        SpnC4 = findViewById<View>(R.id.SpnColor4) as Spinner

        val adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Colores2, android.R.layout.simple_spinner_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        SpnC4!!.adapter = adapter2

        BtnCallIt!!.setOnClickListener {
            ShowDialog("El Resultado es:", BuildNumber(SpnC1!!.selectedItemPosition, SpnC2!!.selectedItemPosition,
                    SpnC3!!.selectedItemPosition) + " " + GetTolerance(SpnC4!!.selectedItemPosition))
        }
    }


    private fun ShowDialog(Title: String, Caption: String) {
        AlertDialog.Builder(this)
                .setTitle(Title)
                .setMessage(Caption)
                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which -> })
                .setIcon(android.R.drawable.ic_menu_info_details)
                .show()
    }

    private fun GetTolerance(Value: Int): String {
        return if (Value == 0) "+5% de tolerancia" else "+10% de tolerancia"
    }


    private fun BuildNumber(Value1: Int, Value2: Int, Value3: Int): String {
        val Significant: String

        Significant = Integer.toString(Value1) + Integer.toString(Value2)

        val Resultado = Significant.toInt() * Math.pow(10.0, Value3.toDouble())

        if (Resultado / 1000 >= 1 && Resultado / 1e3 < 1000) {
            return (Resultado / 1e3).toString() + "KΩ"
        }
        return if (Resultado / 1e6 >= 1) {
            (Resultado / 1e6).toString() + "MΩ"
        } else {
            Resultado.toString() + "Ω"
        }
    }
}