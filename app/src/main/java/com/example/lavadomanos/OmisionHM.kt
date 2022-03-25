package com.example.lavadomanos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class OmisionHM : AppCompatActivity() {
    lateinit var centroIns : TextView
    lateinit var servicioIns : TextView
    lateinit var fechaIns : TextView
    lateinit var horaIniIns : TextView
    lateinit var horaFinIns : TextView
    lateinit var catProfIns : TextView
    lateinit var subcatIns : TextView
    lateinit var indicIns : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omision_hm)


        //Botones
        var bVolverOmision = findViewById<Button>(R.id.bVolverOmision)

        //Textos
        centroIns = findViewById(R.id.centroIns)
        servicioIns = findViewById(R.id.servicioIns)
        fechaIns = findViewById(R.id.fechaIns)
        horaIniIns = findViewById(R.id.horaIniIns)
        horaFinIns = findViewById(R.id.horaFinIns)
        catProfIns = findViewById(R.id.catProfIns)
        subcatIns = findViewById(R.id.subcatIns)
        indicIns = findViewById(R.id.indicIns)

        val bundle = intent.extras
        if(bundle != null){
            centroIns.text = "${bundle.getString("centro")}"
            servicioIns.text = "${bundle.getString("servicio")}"
            fechaIns.text = "${bundle.getString("fecha")}"
            horaIniIns.text = "${bundle.getString("horaInicio")}"
            horaFinIns.text = "${bundle.getString("horaFin")}"
            catProfIns.text = "${bundle.getString("categoria")}"
            subcatIns.text = "${bundle.getString("subcategoria")}"
            indicIns.text = "${bundle.getString("indicacion")}"
        }


        bVolverOmision.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)//Manda a la siguiente pantalla (Inicial en este caso)
        }
    }
}