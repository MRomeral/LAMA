package com.example.lavadomanos


import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.lavadomanos.databinding.ActivityFmBinding
import java.io.File
import java.util.*
import java.util.concurrent.ExecutorService

class FM : AppCompatActivity(), LifecycleOwner {
    //Variables de los botones básicos
    private lateinit var bGrabarFM : ImageButton
    private lateinit var bVolverFM : Button
    private lateinit var bSiguienteFM : Button//Botón oculto hasta que no se realice la limpieza


    //Botones de acción
    lateinit var fm1a: ImageButton
    lateinit var fm1b: ImageButton
    lateinit var fm2: ImageButton
    lateinit var fm3: ImageButton
    lateinit var fm4: ImageButton
    lateinit var fm5: ImageButton
    lateinit var fm6: ImageButton
    lateinit var fm7: ImageButton
    lateinit var fm8: ImageButton

    //Variables de los textos
    private lateinit var tContadorFM : TextView
    private lateinit var tTiempoFM : TextView//Tiempo de lavado de manos (PRUEBA) (OCULTO AL PRINCIPIO)
    //Variables para el vídeo
    lateinit var binding: ActivityFmBinding
    private lateinit var outputDirectory : File
    private lateinit var cameraExecutor : ExecutorService
    private var imageCapture: ImageCapture?=null
    private var contador = 0

    //Hora para saber a que hora se inicia la labor
    private val c = Calendar.getInstance()
    private val hora = c.get(Calendar.HOUR)
    private val minuto = c.get(Calendar.MINUTE)
    private val segundo = c.get(Calendar.SECOND)
    private var auxFM = 0
    private var horaComienzoFM = ""

    private var terminadoFM = false
    private var catProf = ""
    private var subcat = ""
    private var indicacion = ""

    lateinit var tempFM: TextView
    private var tiempoTotalFM = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Textos sobreimpresionados
        //tContadorFM = findViewById(R.id.tContadorFM)//Inicialmente el texto está oculto
        //tTiempoFM = findViewById(R.id.tTiempoFM)//TEXTO DEL TIEMPO DE LAVADO (PRUEBA)

        //Textos
        tempFM = findViewById<TextView>(R.id.tempFM)

        //Permisos para la cámara
        if(allPermissionGranted()){
            startCamera()
        }else{
            ActivityCompat.requestPermissions(
                this, Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }

        //Datos que pasamos por el bundle
        var rCentro = ""
        var rServicio = ""
        var rPabellon = ""
        var rDepartamento = ""
        var per = ""
        var ses = ""
        var observ = ""
        var rFecha = ""
        var rHoraIni = ""
        var rHoraFin = ""

        val bundle = intent.extras//Obtiene todos los datos del bundle
        if(bundle != null){
            rCentro = "${bundle.getString("centro")}"//Datos del centro
            rServicio = "${bundle.getString("servicio")}"//Datos del servicio
            rPabellon = "${bundle.getString("pabellon")}"//Datos del servicio
            rDepartamento = "${bundle.getString("departamento")}"//Datos del servicio
            rFecha = "${bundle.getString("fecha")}"//Datos de la fecha
            rHoraIni = "${bundle.getString("horaInicio")}"//Datos de la hora de inicio
            rHoraFin = "${bundle.getString("horaFin")}"//Datos de la hora de fin
            per = "${bundle.getString("periodo")}"//Datos del periodo
            ses = "${bundle.getString("sesion")}"//Datos de la sesión
            observ = "${bundle.getString("observados")}"//Datos de los observados
            catProf = "${bundle.getString("categoria")}"//Datos de los observados
            subcat = "${bundle.getString("subcategoria")}"//Datos de los observados
        }


        //Botones básicos
        bGrabarFM = findViewById(R.id.bGrabarFM)
        bVolverFM = findViewById(R.id.bVolverFM)
        bSiguienteFM = findViewById(R.id.bSiguienteFM)//Botón oculto
        bSiguienteFM.isEnabled = false//Botón deshabilitado
        bSiguienteFM.isClickable = false//Botón deshabilitado

        //Botones de acción
        fm1a = findViewById<ImageButton>(R.id.fm1a)
        fm1b = findViewById<ImageButton>(R.id.fm1b)
        fm2 = findViewById<ImageButton>(R.id.fm2)
        fm3 = findViewById<ImageButton>(R.id.fm3)
        fm4 = findViewById<ImageButton>(R.id.fm4)
        fm5 = findViewById<ImageButton>(R.id.fm5)
        fm6 = findViewById<ImageButton>(R.id.fm6)
        fm7 = findViewById<ImageButton>(R.id.fm7)
        fm8 = findViewById<ImageButton>(R.id.fm8)
        //Los botones de acción hasta que no se le da a iniciar el proceso están deshabilitados
        fm1a.isEnabled = false
        fm1a.isClickable = false
        fm1b.isEnabled = false
        fm1b.isClickable = false
        fm2.isEnabled = false
        fm2.isClickable = false
        fm3.isEnabled = false
        fm3.isClickable = false
        fm4.isEnabled = false
        fm4.isClickable = false
        fm5.isEnabled = false
        fm5.isClickable = false
        fm6.isEnabled = false
        fm6.isClickable = false
        fm7.isEnabled = false
        fm7.isClickable = false
        fm8.isEnabled = false
        fm8.isClickable = false

        //Funcionalidad de los botones de acción
        binding.fm1a.setOnClickListener {
            fm1a.isClickable = false
            fm1a.isEnabled = false
            mostrarHoraPulsadaFM("Paso 1a")
        }
        binding.fm1b.setOnClickListener {
            fm1b.isClickable = false
            fm1b.isEnabled = false
            mostrarHoraPulsadaFM("Paso 1b")
        }
        binding.fm2.setOnClickListener {
            fm2.isClickable = false
            fm2.isEnabled = false
            mostrarHoraPulsadaFM("Paso 2")
        }
        binding.fm3.setOnClickListener {
            fm3.isClickable = false
            fm3.isEnabled = false
            mostrarHoraPulsadaFM("Paso 3")
        }
        binding.fm4.setOnClickListener {
            fm4.isClickable = false
            fm4.isEnabled = false
            mostrarHoraPulsadaFM("Paso 4")
        }
        binding.fm5.setOnClickListener {
            fm5.isClickable = false
            fm5.isEnabled = false
            mostrarHoraPulsadaFM("Paso 5")
        }
        binding.fm6.setOnClickListener {
            fm6.isClickable = false
            fm6.isEnabled = false
            mostrarHoraPulsadaFM("Paso 6")
        }
        binding.fm7.setOnClickListener {
            fm7.isClickable = false
            fm7.isEnabled = false
            mostrarHoraPulsadaFM("Paso 7")
        }
        binding.fm8.setOnClickListener {
            fm8.isClickable = false
            fm8.isEnabled = false
            mostrarHoraPulsadaFM("Paso 8")
            mostrarTiempoHigieneFM()
        }

        //Funcionalidad del botón de Grabar vídeo
        bGrabarFM.setOnClickListener {
            contarTiempoFM()
            bSiguienteFM.alpha = 1.0f//Activa el botón de siguiente que está oculto
            bSiguienteFM.isEnabled = true//Botón habilitado
            bSiguienteFM.isClickable = true//Botón habilitado
            horaComienzoFM = "$hora:$minuto:$segundo"
            auxFM = hora*3600 + minuto*60 + segundo
            Toast.makeText(this, "Se ha iniciado la grabación, $horaComienzoFM", Toast.LENGTH_SHORT)
        }

        //Funcionalidad del botón de Volver atrás
        bVolverFM.setOnClickListener {
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
            bundle.putString("fecha", rFecha)
            bundle.putString("horaInicio",rHoraIni)
            bundle.putString("horaFin",rHoraFin)
            bundle.putString("categoria",catProf)
            bundle.putString("subcategoria",subcat)
            bundle.putString("indicacion",indicacion)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        //Funcionalidad del botón siguiente
        bSiguienteFM.setOnClickListener {
            val intent = Intent(this, resumenFM::class.java)
            //Envío de datos a la pantalla siguiente
            val bundle = Bundle()
            bundle.putString("centro", rCentro)
            bundle.putString("servicio", rServicio)
            bundle.putString("pabellon", rPabellon)
            bundle.putString("departamento", rDepartamento)
            bundle.putString("periodo",per)
            bundle.putString("sesion",ses)
            bundle.putString("observados",observ)
            bundle.putString("fecha", rFecha)
            bundle.putString("horaInicio",rHoraIni)
            bundle.putString("horaFin",rHoraFin)
            bundle.putString("categoria",catProf)
            bundle.putString("subcategoria",subcat)
            bundle.putString("indicacion",indicacion)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        //viewFinderFM = findViewById(R.id.viewFinderFM)
    }

    private fun contarTiempoFM() {
        //Los botones de acción se activan al iniciar el proceso están deshabilitados
        fm1a.isEnabled = true
        fm1a.isClickable = true
        fm1b.isEnabled = true
        fm1b.isClickable = true
        fm2.isEnabled = true
        fm2.isClickable = true
        fm3.isEnabled = true
        fm3.isClickable = true
        fm4.isEnabled = true
        fm4.isClickable = true
        fm5.isEnabled = true
        fm5.isClickable = true
        fm6.isEnabled = true
        fm6.isClickable = true
        fm7.isEnabled = true
        fm7.isClickable = true
        fm8.isEnabled = true
        fm8.isClickable = true

        //Cuando empieza el proceso de higiene FM
        horaComienzoFM = "$hora:$minuto:$segundo"
        auxFM = hora*3600 + minuto*60 + segundo
        Toast.makeText(this, "Ha comenzado a las $horaComienzoFM", Toast.LENGTH_SHORT).show()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                    mPreview -> mPreview.setSurfaceProvider(
                binding.viewFinder.surfaceProvider
            )
            }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            }catch (e: Exception){
                Log.d(Constants.TAG, "startCamera ha fallado.", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionGranted() = Constants.REQUIRED_PERMISSIONS.all {
        val b = ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
        b
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun mostrarTiempoHigieneFM() {
        if(tiempoTotalFM < 40){
            tempFM.setTextColor(Color.parseColor("#FF0000"))
        }else if(tiempoTotalFM > 40){
            tempFM.setTextColor(Color.parseColor("#FF00FF0A"))
        }
        tempFM.text = tiempoTotalFM.toString()
        tempFM.alpha = 1.0f
        bSiguienteFM.alpha = 1.0f
    }

    private fun mostrarHoraPulsadaFM(paso: String) {

        val c = Calendar.getInstance()
        val dia = c.get(Calendar.DAY_OF_MONTH)
        val mes = c.get(Calendar.MONTH)
        val anno = c.get(Calendar.YEAR)
        val hora = c.get(Calendar.HOUR)
        val minuto = c.get(Calendar.MINUTE)
        val segundo = c.get(Calendar.SECOND)
        val auxPaso = hora*3600 + minuto*60 + segundo

        Toast.makeText(this,"Se ha realizado el $paso, en el segundo: ${difHoraFM(auxFM, auxPaso)}", Toast.LENGTH_LONG).show()
        if(paso == "Paso 8"){
            tiempoTotalFM = difHoraFM(auxFM, auxPaso).toInt()
            terminadoFM = true
        }
    }

    private fun difHoraFM(horaComienzo: Int, horaPaso: Int): String {
        return horaPaso.minus(horaComienzo).toString()
    }
}


