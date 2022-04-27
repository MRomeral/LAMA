package com.example.lavadomanos

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*


class Formulario2 : AppCompatActivity() {
    lateinit var rFecha : EditText
    lateinit var rHoraIni : EditText
    lateinit var rHoraFin : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario2)

        //Variables
        rFecha = findViewById(R.id.rFecha)
        rHoraIni = findViewById(R.id.rHoraIni)
        rHoraFin = findViewById(R.id.rHoraFin)
        //Calendario para comprobar que se elige una fecha de hoy o siguiente
        val calendario = Calendar.getInstance()

        //Fecha de observación
        rFecha.setOnClickListener { showDatePickerDialog() }
        //Hora Inicio
        rHoraIni.setOnClickListener { showTimePickerDialog() }
        //Hora Fin
        rHoraFin.setOnClickListener { showTimePickerDialogFin() }

        //Spinners
        //Spinner País
        val paises = resources.getStringArray(R.array.paises)
        val spPaises = findViewById<Spinner>(R.id.spPais)

        if(spPaises != null){
            val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,paises)
            spPaises.adapter = adapter
        }

        //Spinner Provincias
        val provincias = resources.getStringArray(R.array.provincias)
        val spProvincias = findViewById<Spinner>(R.id.spProvincia)

        if(spProvincias != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provincias)
            spProvincias.adapter = adapter
        }

        //Botones
        val bVolver = findViewById<Button>(R.id.bVolverForm2)//Volver a la pantalla anterior
        val bSiguiente = findViewById<Button>(R.id.bSigForm2)//Avanzar a la pantalla siguiente

        //Datos que se pasan por el bundle pantalla por pantalla
        var rCentro = ""
        var rServicio = ""
        var rPabellon = ""
        var rDepartamento = ""
        var per = ""
        var ses = ""
        var observ = ""

        //Datos recibidos de la pantalla anterior
        val bundle = intent.extras
        if (bundle != null) {
            rCentro = "${bundle.getString("centro")}"
            rServicio = "${bundle.getString("servicio")}"
            rPabellon = "${bundle.getString("pabellon")}"
            rDepartamento = "${bundle.getString("departamento")}"
            per = "${bundle.getString("periodo")}"
            ses = "${bundle.getString("sesion")}"
            observ = "${bundle.getString("observados")}"
        }

        //Botón de Volver a la pantalla anterior
        bVolver.setOnClickListener {
            val intent = Intent(this, Formulario::class.java)
            startActivity(intent)
        }

        //Si la fecha elegida es anterior a la actual no deja avanzar de pantalla
        val mensaje = "La fecha es: "+compararFechas()
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT)

        //Pasar a la siguiente pantalla
        bSiguiente.setOnClickListener {
            if(rFecha.getText().isNotEmpty() && rHoraIni.getText().isNotEmpty() && rHoraFin.getText().isNotEmpty()
               /* && rHoraIni.compareTo(rHoraFin)*/){
                val intent = Intent(this, Grabar::class.java)

                //Envío de datos a la pantalla siguiente
                val bundle = Bundle()
                bundle.putString("centro", rCentro)
                bundle.putString("servicio", rServicio)
                bundle.putString("pabellon", rPabellon)
                bundle.putString("departamento", rDepartamento)
                bundle.putString("periodo",per)
                bundle.putString("sesion",ses)
                bundle.putString("observados",observ)
                bundle.putString("fecha", rFecha.text.toString())
                bundle.putString("horaInicio",rHoraIni.text.toString())
                bundle.putString("horaFin",rHoraFin.text.toString())
                intent.putExtras(bundle)

                val mensaje = "Día: " + rFecha.getText().toString() + ", Inicio: " + rHoraIni.getText().toString() + ", Fin: " + rHoraFin.getText().toString()
                Toast.makeText(this,mensaje, Toast.LENGTH_LONG).show()
                startActivity(intent)
            }else{
                Toast.makeText(this,"No ha insertado los campos requeridos",Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment{day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int){
        rFecha.setText("$day/$month/$year")
    }

    //Elección de hora de inicio
    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment{onTimeSelected(it)}
        timePicker.show(supportFragmentManager, "time")
    }

    private fun onTimeSelected(time:String){
        rHoraIni.setText("$time")
    }

    //Elección de hora de fin
    private fun showTimePickerDialogFin() {
        val timePicker = TimePickerFragment{onTimeSelectedFin(it)}
        timePicker.show(supportFragmentManager, "time")
    }

    private fun onTimeSelectedFin(time:String){
        rHoraFin.setText("$time")
    }

    //Comparar fechas
    private fun compararFechas() : Boolean{
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        val fecha = "$day/$month/$year"
        val fechaElegida = rFecha.text.toString()
        if(fecha > fechaElegida){
            return false
        }else if(fecha == fechaElegida){
            return true
        }else{
            return true
        }
    }

}