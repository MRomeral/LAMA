package com.example.lavadomanos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.util.*


class Formulario2 : AppCompatActivity() {
    lateinit var fecha : EditText
    lateinit var horaInicio : EditText
    lateinit var horaFin : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario2)

        //Variables
        fecha = findViewById(R.id.rFecha)
        horaInicio = findViewById(R.id.rHoraIni)
        horaFin = findViewById(R.id.rHoraFin)
        //Calendario para comprobar que se elige una fecha de hoy o siguiente
        val calendario = Calendar.getInstance()

        //Fecha de observación
        fecha.setOnClickListener { showDatePickerDialog() }
        //Hora Inicio
        horaInicio.setOnClickListener { showTimePickerDialog() }
        //Hora Fin
        horaFin.setOnClickListener { showTimePickerDialogFin() }

        //Spinners
        //Spinner País
        val paises = resources.getStringArray(R.array.paises)
        val spPaises = findViewById<Spinner>(R.id.spPais)

        var pais = ""
        if(spPaises != null){
            val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,paises)
            spPaises.adapter = adapter
            spPaises.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    pais = paises[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }
        }

        //Spinner Provincias
        val provincias = resources.getStringArray(R.array.provincias)
        val spProvincias = findViewById<Spinner>(R.id.spProvincia)

        var provincia = ""
        if(spProvincias != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provincias)
            spProvincias.adapter = adapter
            spProvincias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    provincia = provincias[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }
        }

        //Botones
        val bVolver = findViewById<Button>(R.id.bVolverForm2)//Volver a la pantalla anterior
        val bSiguiente = findViewById<Button>(R.id.bSigForm2)//Avanzar a la pantalla siguiente

        //Datos que se pasan por el bundle pantalla por pantalla
        var centro = ""
        var servicio = ""
        var pabellon = ""
        var departamento = ""
        var intervencion = ""
        var sesion = ""
        var observados = ""

        //Datos recibidos de la pantalla anterior
        val bundle = intent.extras
        if (bundle != null) {
            centro = "${bundle.getString("centro")}"
            servicio = "${bundle.getString("servicio")}"
            pabellon = "${bundle.getString("pabellon")}"
            departamento = "${bundle.getString("departamento")}"
            intervencion = "${bundle.getString("periodo")}"
            sesion = "${bundle.getString("sesion")}"
            observados = "${bundle.getString("observados")}"
        }

        //Botón de Volver a la pantalla anterior
        bVolver.setOnClickListener {
            val intent = Intent(this, Formulario::class.java)
            //Envío de datos a la pantalla siguiente
            val bundle = Bundle()
            bundle.putString("centro", centro)
            bundle.putString("servicio", servicio)
            bundle.putString("pabellon", pabellon)
            bundle.putString("departamento", departamento)
            bundle.putString("periodo",intervencion)
            bundle.putString("sesion",sesion)
            bundle.putString("observados",observados)
            bundle.putString("fecha", fecha.text.toString())
            bundle.putString("horaInicio",horaInicio.text.toString())
            bundle.putString("horaFin",horaFin.text.toString())
            intent.putExtras(bundle)
            startActivity(intent)
        }

        //Si la fecha elegida es anterior a la actual no deja avanzar de pantalla
        val mensaje = "La fecha es: "+compararFechas()
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT)

        //Pasar a la siguiente pantalla
        bSiguiente.setOnClickListener {
            if(fecha.getText().isNotEmpty() && horaInicio.getText().isNotEmpty() && horaFin.getText().isNotEmpty() && pais.isNotEmpty() && provincia.isNotEmpty()
               /* && rHoraIni.compareTo(rHoraFin)*/){
                val intent = Intent(this, Grabar::class.java)

                //Envío de datos a la pantalla siguiente
                val bundle = Bundle()
                bundle.putString("centro", centro)
                bundle.putString("servicio", servicio)
                bundle.putString("pabellon", pabellon)
                bundle.putString("departamento", departamento)
                bundle.putString("periodo",intervencion)
                bundle.putString("sesion",sesion)
                bundle.putString("observados",observados)
                bundle.putString("fecha", fecha.text.toString())
                bundle.putString("horaInicio",horaInicio.text.toString())
                bundle.putString("horaFin",horaFin.text.toString())
                bundle.putString("pais",pais)
                bundle.putString("provincia",provincia)
                intent.putExtras(bundle)

                val mensaje = "Día: " + fecha.getText().toString() + ", Inicio: " + horaInicio.getText().toString() + ", Fin: " + horaFin.getText().toString()
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
        fecha.setText("$day/$month/$year")
    }

    //Elección de hora de inicio
    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment{onTimeSelected(it)}
        timePicker.show(supportFragmentManager, "time")
    }

    private fun onTimeSelected(time:String){
        horaInicio.setText("$time")
    }

    //Elección de hora de fin
    private fun showTimePickerDialogFin() {
        val timePicker = TimePickerFragment{onTimeSelectedFin(it)}
        timePicker.show(supportFragmentManager, "time")
    }

    private fun onTimeSelectedFin(time:String){
        horaFin.setText("$time")
    }

    //Comparar fechas
    private fun compararFechas() : Boolean{
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        val fecha = "$day/$month/$year"
        val fechaElegida = this.fecha.text.toString()
        if(fecha > fechaElegida){
            return false
        }else if(fecha == fechaElegida){
            return true
        }else{
            return true
        }
    }

}