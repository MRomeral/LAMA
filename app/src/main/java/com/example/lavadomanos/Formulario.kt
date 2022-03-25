package com.example.lavadomanos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class Formulario : AppCompatActivity() {
    lateinit var rCentro : EditText
    lateinit var rServicio : EditText
    lateinit var rPabellon : EditText
    lateinit var rDepartamento : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        //Variables
        rCentro = findViewById(R.id.rCentro)
        rServicio = findViewById(R.id.rServicio)
        rPabellon = findViewById(R.id.rPabellon)
        rDepartamento = findViewById(R.id.rDepartamento)

        //Botones
        val bVolver = findViewById<Button>(R.id.botonVolverForm)
        val bSiguiente = findViewById<Button>(R.id.botonSigForm)

        //Spinners
        //Spinner de Número de Periodo
        val periodo = resources.getStringArray(R.array.periodos)
        val spPeriodo = findViewById<Spinner>(R.id.spPeriodo)
        //Hay que añadir una variable para pasar el periodo para volcarlo a un .csv
        if(spPeriodo != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, periodo)
            spPeriodo.adapter = adapter
        }

        //Spinner de Número de Sesión
        val sesion = resources.getStringArray(R.array.Sesiones)
        val spSesion = findViewById<Spinner>(R.id.spSesion)
        //Hay que añadir una variable para pasar la sesión a la última pantalla y poder volcarlo a un .csv
        if(spSesion != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sesion)
            spSesion.adapter = adapter
        }


        //Número de Observados
        val observados = resources.getStringArray(R.array.observados)
        val spObservados = findViewById<Spinner>(R.id.spObservados)
        if(spObservados != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, observados)
            spObservados.adapter = adapter
        }

        //Verifica si los campos se han insertado

        bSiguiente.setOnClickListener{//Botón para avanzar en el formulario
            if(rCentro.getText().isNotEmpty() && rServicio.getText().isNotEmpty()){
                val intent = Intent(this, Formulario2::class.java)
                val mensaje ="Centro: " + rCentro.getText().toString() + ", Servicio: " + rServicio.getText().toString()

                val bundle = Bundle()
                bundle.putString("centro",rCentro.text.toString())
                bundle.putString("servicio",rServicio.text.toString())
                intent.putExtras(bundle)

                startActivity(intent)//Manda a la siguiente pantalla (Formulario 2)
                Toast.makeText(this,mensaje, Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"No has insertado centro", Toast.LENGTH_SHORT).show()
            }

        }

        bVolver.setOnClickListener{//Botón para volver al inicio
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)//Manda a la siguiente pantalla (Pantalla Inicial)
        }


    }
}