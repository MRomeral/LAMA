package com.example.lavadomanos

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.RequiresApi

class OmisionHM : AppCompatActivity() {
    lateinit var centroIns : TextView
    lateinit var servicioIns : TextView
    lateinit var pabellonIns : TextView
    lateinit var departamentoIns : TextView
    lateinit var fechaIns : TextView
    lateinit var horaIniIns : TextView
    lateinit var horaFinIns : TextView
    lateinit var catProfIns : TextView
    lateinit var subcatIns : TextView
    lateinit var indicIns : TextView
    lateinit var intervencionIns : TextView
    lateinit var sesionIns : TextView
    lateinit var observadosIns : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omision_hm)


        //Botones
        var bVolverOmision = findViewById<Button>(R.id.bVolverOmision)



        //Textos
        centroIns = findViewById(R.id.centroIns)
        servicioIns = findViewById(R.id.servicioIns)
        pabellonIns = findViewById(R.id.pabellonIns)
        departamentoIns = findViewById(R.id.departamentoIns)
        fechaIns = findViewById(R.id.fechaIns)
        horaIniIns = findViewById(R.id.horaIniIns)
        horaFinIns = findViewById(R.id.horaFinIns)
        catProfIns = findViewById(R.id.catProfIns)
        subcatIns = findViewById(R.id.subcatIns)
        indicIns = findViewById(R.id.indicIns)
        intervencionIns = findViewById(R.id.intervencionIns)
        sesionIns = findViewById(R.id.sesionIns)
        observadosIns = findViewById(R.id.observadosIns)

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
            intervencionIns.text = "${bundle.getString("periodo")}"//Datos del periodo
            sesionIns.text = "${bundle.getString("sesion")}"//Datos de la sesión
            observadosIns.text = "${bundle.getString("observados")}"//Datos de los observados
            if(pabellonIns.text == "Pabellón"){
                pabellonIns.alpha = 0f
            }
            if(departamentoIns.text == "Departamento"){
                departamentoIns.alpha = 0f
            }
        }

        //Acción asociada al botón "Volver a Inicio", devuelve a la pantalla principal
        bVolverOmision.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)//Manda a la siguiente pantalla (Inicial en este caso)
        }


    }
    //Bloquea volver atrás

}