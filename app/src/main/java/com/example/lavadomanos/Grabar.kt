package com.example.lavadomanos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class Grabar : AppCompatActivity() {
    lateinit var bVolverGrabar : Button
    lateinit var bAlcohol : ImageButton
    lateinit var bJabon : ImageButton
    lateinit var bGuantes : ImageButton
    lateinit var bOmision : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grabar)

        //Botones
        bVolverGrabar = findViewById(R.id.bVolverGrabar)
        bAlcohol = findViewById(R.id.bAlcohol)
        bJabon = findViewById(R.id.bJabon)
        bGuantes = findViewById(R.id.bGuantes)
        bOmision = findViewById(R.id.bOmision)

        //Spinners
        //Spinner de Categoría profesional
        val categorias = resources.getStringArray(R.array.categoriasProfesionales)
        val spCategorias = findViewById<Spinner>(R.id.spCatProf)
        var categoriaProfesional = ""
        var subcat = ""

        if(spCategorias != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,categorias)
            spCategorias.adapter = adapter

            spCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val text : String = parent?.getItemAtPosition(position).toString()
                    categoriaProfesional = categorias[position]
                    if(position == 1){
                        val subEnf = resources.getStringArray(R.array.subEnfermera)
                        val spSubcat = findViewById<Spinner>(R.id.spSubcat)
                        if(spSubcat != null){
                            val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, subEnf)
                            spSubcat.adapter = adapter
                            spSubcat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                override fun onItemSelected(parent: AdapterView<*>?,view: View?,
                                    position: Int,id: Long) {
                                    if(position == 1){
                                        subcat = subEnf[position]
                                    }else if(position == 2){
                                        subcat = subEnf[position]
                                    }else if(position == 3){
                                        subcat = subEnf[position]
                                    }
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {}

                            }
                        }
                    }else if(position == 2){
                        val subAux = resources.getStringArray(R.array.subAuxiliar)
                        val spSubcat = findViewById<Spinner>(R.id.spSubcat)
                        if(spSubcat != null){
                            val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item,subAux)
                            spSubcat.adapter = adapter
                            subcat = " "
                        }
                    }else if(position == 3){
                        val subMed = resources.getStringArray(R.array.subMedico)
                        val spSubcat = findViewById<Spinner>(R.id.spSubcat)
                        if(spSubcat != null){
                            val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, subMed)
                            spSubcat.adapter = adapter
                            spSubcat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                override fun onItemSelected(parent: AdapterView<*>?,view: View?,
                                                            position: Int,id: Long) {
                                    if(position == 1){
                                        subcat = subMed[position]
                                    }else if(position == 2){
                                        subcat = subMed[position]
                                    }else if(position == 3){
                                        subcat = subMed[position]
                                    }else if(position == 4){
                                        subcat = subMed[position]
                                    }else if(position == 5){
                                        subcat = subMed[position]
                                    }else if(position == 6){
                                        subcat = subMed[position]
                                    }else if(position == 7){
                                        subcat = subMed[position]
                                    }
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {}

                            }
                        }
                    }else if(position == 4){
                        val subOtros = resources.getStringArray(R.array.subOtros)
                        val spSubcat = findViewById<Spinner>(R.id.spSubcat)
                        if(spSubcat != null){
                            val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, subOtros)
                            spSubcat.adapter = adapter
                            spSubcat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                override fun onItemSelected(parent: AdapterView<*>?,view: View?,
                                                            position: Int,id: Long) {
                                    if(position == 1){
                                        subcat = subOtros[position]
                                    }else if(position == 2){
                                        subcat = subOtros[position]
                                    }else if(position == 3){
                                        subcat = subOtros[position]
                                    }else if(position == 4){
                                        subcat = subOtros[position]
                                    }
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {}

                            }
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }


        //Spinner de indicación
        val indicaciones = resources.getStringArray(R.array.indicaciones)
        val spIndicaciones = findViewById<Spinner>(R.id.spInd)
        var indicacion = ""
        if(spCategorias != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, indicaciones)
            spIndicaciones.adapter = adapter
            spIndicaciones.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if(position == 1){
                        indicacion = indicaciones[position]
                    }else if(position == 2){
                        indicacion = indicaciones[position]
                    }else if(position == 3){
                        indicacion = indicaciones[position]
                    }else if(position == 4){
                        indicacion = indicaciones[position]
                    }else if(position == 5){
                        indicacion = indicaciones[position]
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }
        }

        //Funcionalidad de los botones
        bVolverGrabar.setOnClickListener{
            var intent = Intent(this,Formulario2::class.java)
            startActivity(intent)//Vuelve a la pantalla anterior (Formulario 2)
        }

        //Datos que pasamos por el bundle
        var centro = ""
        var servicio = ""
        var pabellon = ""
        var departamento = ""
        var intervencion = ""
        var sesion = ""
        var observados = ""
        var fecha = ""
        var horaInicio = ""
        var horaFin = ""
        var pais = ""
        var provincia = ""
        var tipo = ""

        val bundle = intent.extras//Obtiene todos los datos del bundle
        if(bundle != null){
            centro = "${bundle.getString("centro")}"//Datos del centro
            servicio = "${bundle.getString("servicio")}"//Datos del servicio
            pabellon = "${bundle.getString("pabellon")}"//Datos del servicio
            departamento = "${bundle.getString("departamento")}"//Datos del servicio
            fecha = "${bundle.getString("fecha")}"//Datos de la fecha
            horaInicio = "${bundle.getString("horaInicio")}"//Datos de la hora de inicio
            horaFin = "${bundle.getString("horaFin")}"//Datos de la hora de fin
            intervencion = "${bundle.getString("periodo")}"//Datos del periodo
            sesion = "${bundle.getString("sesion")}"//Datos de la sesión
            observados = "${bundle.getString("observados")}"//Datos de los observados
            pais = "${bundle.getString("pais")}"
            provincia = "${bundle.getString("provincia")}"
        }

        //Acciones asociadas a los botones
        //Envía a la función de FM
        bAlcohol.setOnClickListener{
            if(categoriaProfesional.isNotEmpty() && subcat.isNotEmpty() && indicacion.isNotEmpty()){
                val intent = Intent(this,FM::class.java)
                var mensaje = "Categoría profesional: " + categoriaProfesional.toString() + ", "
                tipo = "FM"
                //Envío de datos a la pantalla siguiente
                val bundle = Bundle()
                bundle.putString("centro", centro)
                bundle.putString("servicio", servicio)
                bundle.putString("pabellon", pabellon)
                bundle.putString("departamento", departamento)
                bundle.putString("periodo",intervencion)
                bundle.putString("sesion",sesion)
                bundle.putString("observados",observados)
                bundle.putString("fecha", fecha)
                bundle.putString("horaInicio",horaInicio)
                bundle.putString("horaFin",horaFin)
                bundle.putString("categoria",categoriaProfesional)
                bundle.putString("subcategoria",subcat)
                bundle.putString("indicacion",indicacion)
                bundle.putString("pais",pais)
                bundle.putString("provincia",provincia)
                bundle.putString("tipo",tipo)
                intent.putExtras(bundle)

                startActivity(intent)//Manda a la siguiente pantalla
                Toast.makeText(this,"Frotado de Manos con Gel Hidroalcohólico",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"No se han rellenado los campos requeridos.",Toast.LENGTH_LONG).show()
            }

        }

        //Envía a la función de LM
        bJabon.setOnClickListener{
            if(categoriaProfesional.isNotEmpty() && subcat.isNotEmpty() && indicacion.isNotEmpty()){
                val intent = Intent(this,LM::class.java)
                var mensaje = "Categoría profesional: " + categoriaProfesional.toString() + ", "
                tipo = "LM"
                //Envío de datos a la pantalla siguiente
                val bundle = Bundle()
                bundle.putString("centro", centro)
                bundle.putString("servicio", servicio)
                bundle.putString("pabellon", pabellon)
                bundle.putString("departamento", departamento)
                bundle.putString("periodo",intervencion)
                bundle.putString("sesion",sesion)
                bundle.putString("observados",observados)
                bundle.putString("fecha", fecha)
                bundle.putString("horaInicio",horaInicio)
                bundle.putString("horaFin",horaFin)
                bundle.putString("categoria",categoriaProfesional)
                bundle.putString("subcategoria",subcat)
                bundle.putString("indicacion",indicacion)
                bundle.putString("pais",pais)
                bundle.putString("provincia",provincia)
                bundle.putString("tipo",tipo)
                intent.putExtras(bundle)

                startActivity(intent)//Manda a la siguiente pantalla
                Toast.makeText(this,"Lavado de Manos con Agua y Jabón",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"No se han rellenado los campos requeridos.",Toast.LENGTH_LONG).show()
            }
        }

        //Envía a la función de Guantes
        bGuantes.setOnClickListener{
            if(categoriaProfesional.isNotEmpty() && subcat.isNotEmpty() && indicacion.isNotEmpty()){
                val intent = Intent(this,resumenHM::class.java)
                var mensaje = "Categoría profesional: " + categoriaProfesional.toString() + ", "
                tipo = "Guantes"
                //Envío de datos a la pantalla siguiente
                val bundle = Bundle()
                bundle.putString("centro", centro)
                bundle.putString("servicio", servicio)
                bundle.putString("pabellon", pabellon)
                bundle.putString("departamento", departamento)
                bundle.putString("periodo",intervencion)
                bundle.putString("sesion",sesion)
                bundle.putString("observados",observados)
                bundle.putString("fecha", fecha)
                bundle.putString("horaInicio",horaInicio)
                bundle.putString("horaFin",horaFin)
                bundle.putString("categoria",categoriaProfesional)
                bundle.putString("subcategoria",subcat)
                bundle.putString("indicacion",indicacion)
                bundle.putString("pais",pais)
                bundle.putString("provincia",provincia)
                bundle.putString("tipo",tipo)
                intent.putExtras(bundle)

                startActivity(intent)//Manda a la siguiente pantalla
                Toast.makeText(this,"Higiene con guantes",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"No se han rellenado los campos requeridos.",Toast.LENGTH_LONG).show()
            }
        }

        //Envía a la función de Omisión
        bOmision.setOnClickListener{
            if(categoriaProfesional.isNotEmpty() && subcat.isNotEmpty() && indicacion.isNotEmpty()){
                var intent = Intent(this,resumenHM::class.java)
                var mensaje = "Categoría profesional: " + categoriaProfesional.toString() + ", "
                tipo = "Omisión"
                //Envío de datos a la pantalla siguiente
                val bundle = Bundle()
                bundle.putString("centro", centro)
                bundle.putString("servicio", servicio)
                bundle.putString("pabellon", pabellon)
                bundle.putString("departamento", departamento)
                bundle.putString("periodo",intervencion)
                bundle.putString("sesion",sesion)
                bundle.putString("observados",observados)
                bundle.putString("fecha", fecha)
                bundle.putString("horaInicio",horaInicio)
                bundle.putString("horaFin",horaFin)
                bundle.putString("categoria",categoriaProfesional)
                bundle.putString("subcategoria",subcat)
                bundle.putString("indicacion",indicacion)
                bundle.putString("pais",pais)
                bundle.putString("provincia",provincia)
                bundle.putString("tipo",tipo)
                intent.putExtras(bundle)

                startActivity(intent)//Manda a la siguiente pantalla
                Toast.makeText(this,"Omisión. NO HAY HIGIENE DE MANOS.",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"No se han rellenado los campos requeridos.",Toast.LENGTH_LONG).show()
            }

        }
    }
}