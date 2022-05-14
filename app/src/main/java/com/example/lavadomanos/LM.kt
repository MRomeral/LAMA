package com.example.lavadomanos


import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lavadomanos.databinding.ActivityLmBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LM : AppCompatActivity(){
    lateinit var botonGrabar: ImageButton
    lateinit var lm0: ImageButton
    lateinit var lm1: ImageButton
    lateinit var lm2: ImageButton
    lateinit var lm3: ImageButton
    lateinit var lm4: ImageButton
    lateinit var lm5: ImageButton
    lateinit var lm6: ImageButton
    lateinit var lm7: ImageButton
    lateinit var lm8: ImageButton
    lateinit var lm9: ImageButton
    lateinit var lm10: ImageButton
    lateinit var lm11: ImageButton

    lateinit var botonSiguiente: Button
    lateinit var botonVolver: Button

    lateinit var tiempoHigiene: TextView
    lateinit var countDownTimer : CountDownTimer

    //Hora para saber a que hora se inicia la labor
    private val c = Calendar.getInstance()
    private val hora = c.get(Calendar.HOUR)
    private val minuto = c.get(Calendar.MINUTE)
    private val segundo = c.get(Calendar.SECOND)

    private var contBoton = 0
    private var contadorTiempo = 0

    private lateinit var binding: ActivityLmBinding
    private lateinit var outputDirectory : File
    private lateinit var cameraExecutor : ExecutorService
    private var imageCapture: ImageCapture?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Botones básicos
        botonGrabar = findViewById<ImageButton>(R.id.bGrabarLM)
        botonVolver = findViewById<Button>(R.id.bVolverLM)
        botonSiguiente = findViewById<Button>(R.id.bSiguienteLM)
        botonSiguiente.isEnabled = false//Botón deshabilitado
        botonSiguiente.isClickable = false//Botón deshabilitado

        //Botones de acciones
        lm0 = findViewById<ImageButton>(R.id.lm0)
        lm1 = findViewById<ImageButton>(R.id.lm1)
        lm2 = findViewById<ImageButton>(R.id.lm2)
        lm3 = findViewById<ImageButton>(R.id.lm3)
        lm4 = findViewById<ImageButton>(R.id.lm4)
        lm5 = findViewById<ImageButton>(R.id.lm5)
        lm6 = findViewById<ImageButton>(R.id.lm6)
        lm7 = findViewById<ImageButton>(R.id.lm7)
        lm8 = findViewById<ImageButton>(R.id.lm8)
        lm9 = findViewById<ImageButton>(R.id.lm9)
        lm10 = findViewById<ImageButton>(R.id.lm10)
        lm11 = findViewById<ImageButton>(R.id.lm11)

        //Los botones de acción hasta que no se le da a iniciar el proceso están deshabilitados
        lm0.isEnabled = false
        lm0.isClickable = false
        lm1.isEnabled = false
        lm1.isClickable = false
        lm2.isEnabled = false
        lm2.isClickable = false
        lm3.isEnabled = false
        lm3.isClickable = false
        lm4.isEnabled = false
        lm4.isClickable = false
        lm5.isEnabled = false
        lm5.isClickable = false
        lm6.isEnabled = false
        lm6.isClickable = false
        lm7.isEnabled = false
        lm7.isClickable = false
        lm8.isEnabled = false
        lm8.isClickable = false
        lm9.isEnabled = false
        lm9.isClickable = false
        lm10.isEnabled = false
        lm10.isClickable = false
        lm11.isEnabled = false
        lm11.isClickable = false

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
        var categoriaProfesional = ""
        var subcat = ""
        var indicacion = ""
        //Variable que distingue tipo de limpieza
        var tipo = ""
        var paso0 = "";var paso1 = "";var paso2 = "";var paso3 = "";var paso4 = "";var paso5 = "";
        var paso6 = "";var paso7 = "";var paso8 = "";var paso9 = "";var paso10 = "";var paso11 = "";

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
            pais = "${bundle.getString("pais")}"//Datos del país
            provincia = "${bundle.getString("provincia")}"//Datos de la provincia
            categoriaProfesional = "${bundle.getString("categoria")}"//Datos de la categoría profesional
            subcat = "${bundle.getString("subcategoria")}"//Datos de la subcategoría
            indicacion = "${bundle.getString("indicacion")}"//Datos de la indicación
            tipo = "${bundle.getString("tipo")}"//Datos del tipo
        }


        //Textos
        tiempoHigiene = findViewById<TextView>(R.id.tempLM)

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        if(allPermissionGranted()){
            startCamera()
        }else{
            ActivityCompat.requestPermissions(
                this, Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }
        //Funciones asociadas a los botones básicos
        //Botón que inicia el proceso de grabación
        binding.bGrabarLM.setOnClickListener {
            botonSiguiente.alpha = 1.0f
            botonSiguiente.isEnabled = true
            botonSiguiente.isClickable = true
            //Activa los botones de acción al iniciar el proceso
            lm0.isEnabled = true
            lm0.isClickable = true
            lm1.isEnabled = true
            lm1.isClickable = true
            lm2.isEnabled = true
            lm2.isClickable = true
            lm3.isEnabled = true
            lm4.isClickable = true
            lm5.isEnabled = true
            lm5.isClickable = true
            lm6.isEnabled = true
            lm6.isClickable = true
            lm7.isEnabled = true
            lm7.isClickable = true
            lm8.isEnabled = true
            lm8.isClickable = true
            lm9.isEnabled = true
            lm9.isClickable = true
            lm10.isEnabled = true
            lm10.isClickable = true
            lm11.isEnabled = true
            lm11.isClickable = true

            contBoton++
            if(contBoton%2==1){
                countDownTimer = object : CountDownTimer(120000,1000){
                    override fun onTick(millisUntilFinished: Long) {
                        tiempoHigiene.alpha = 1.0f
                        contadorTiempo++
                        tiempoHigiene.setText(""+contadorTiempo)
                        if(contadorTiempo>40)
                            tiempoHigiene.setTextColor(Color.parseColor("#FF00FF0A"))
                    }

                    override fun onFinish() {
                        tiempoHigiene.setText("Tiempo máximo de Higiene "+contadorTiempo)
                    }

                }.start()
            }else{
                botonGrabar.setImageResource(R.mipmap.ic_grabar256)//Vuelve al icono original de grabar
                countDownTimer.cancel()//Para el contador
            }



        }


        //Vuelve a la pantalla anterior
        binding.bVolverLM.setOnClickListener {
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
            bundle.putString("fecha", fecha)
            bundle.putString("horaInicio",horaInicio)
            bundle.putString("horaFin",horaFin)
            bundle.putString("categoria",categoriaProfesional)
            bundle.putString("subcategoria",subcat)
            bundle.putString("indicacion",indicacion)
            bundle.putString("pais",pais)
            bundle.putString("provincia",provincia)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        //Este botón se revela una vez que el usuario ha pulsado grabar
        binding.bSiguienteLM.setOnClickListener {
            val intent = Intent(this, resumenHM::class.java)
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
            bundle.putString("tipo",tipo)
            bundle.putString("pais",pais)
            bundle.putString("provincia",provincia)
            bundle.putString("contador",contadorTiempo.toString())
            bundle.putString("paso0",paso0)
            bundle.putString("paso1",paso1)
            bundle.putString("paso2",paso2)
            bundle.putString("paso3",paso3)
            bundle.putString("paso4",paso4)
            bundle.putString("paso5",paso5)
            bundle.putString("paso6",paso6)
            bundle.putString("paso7",paso7)
            bundle.putString("paso8",paso8)
            bundle.putString("paso9",paso9)
            bundle.putString("paso10",paso10)
            bundle.putString("paso11",paso11)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        //Funcionalidad de los botones de acción
        binding.lm0.setOnClickListener {
            //takePhoto()

            lm0.isClickable = false
            lm0.isEnabled = false
            paso0 = contadorTiempo.toString()
            Toast.makeText(this, "Paso: 0, Instante: $paso0", Toast.LENGTH_SHORT).show()
        }
        binding.lm1.setOnClickListener {
            takePhoto()
            lm1.isClickable = false
            lm1.isEnabled = false
            paso1 = contadorTiempo.toString()
            Toast.makeText(this, "Paso: 1, Instante: $paso1", Toast.LENGTH_SHORT).show()
        }
        binding.lm2.setOnClickListener {
            lm2.isClickable = false
            lm2.isEnabled = false
            paso2 = contadorTiempo.toString()
            Toast.makeText(this, "Paso: 2, Instante: $paso2", Toast.LENGTH_SHORT).show()
        }
        binding.lm3.setOnClickListener {
            lm3.isClickable = false
            lm3.isEnabled = false
            paso3 = contadorTiempo.toString()
            Toast.makeText(this, "Paso: 3, Instante: $paso3", Toast.LENGTH_SHORT).show()
        }
        binding.lm4.setOnClickListener {
            lm4.isClickable = false
            lm4.isEnabled = false
            paso4 = contadorTiempo.toString()
            Toast.makeText(this,"Paso: 4, Instante: $paso4", Toast.LENGTH_SHORT).show()
        }
        binding.lm5.setOnClickListener {
            lm5.isClickable = false
            lm5.isEnabled = false
            paso5 = contadorTiempo.toString()
            Toast.makeText(this, "Paso: 5, Instante: $paso5", Toast.LENGTH_SHORT).show()
        }
        binding.lm6.setOnClickListener {
            lm6.isClickable = false
            lm6.isEnabled = false
            paso6 = contadorTiempo.toString()
            Toast.makeText(this, "Paso: 6, Instante: $paso6", Toast.LENGTH_SHORT).show()
        }
        binding.lm7.setOnClickListener {
            lm7.isClickable = false
            lm7.isEnabled = false
            paso7 = contadorTiempo.toString()
            Toast.makeText(this, "Paso: 7, Instante: $paso7", Toast.LENGTH_SHORT).show()
        }
        binding.lm8.setOnClickListener {
            lm8.isClickable = false
            lm8.isEnabled = false
            paso8 = contadorTiempo.toString()
            Toast.makeText(this, "Paso: 8, Instante: $paso8", Toast.LENGTH_SHORT).show()
        }
        binding.lm9.setOnClickListener {
            lm9.isClickable = false
            lm9.isEnabled = false
            paso9 = contadorTiempo.toString()
            Toast.makeText(this, "Paso: 9, Instante: $paso9", Toast.LENGTH_SHORT).show()
        }
        binding.lm10.setOnClickListener {
            lm10.isClickable = false
            lm10.isEnabled = false
            paso10 = contadorTiempo.toString()
            Toast.makeText(this, "Paso: 10, Instante: $paso10", Toast.LENGTH_SHORT).show()
        }
        binding.lm11.setOnClickListener {
            lm11.isClickable = false
            lm11.isEnabled = false
            paso11 = contadorTiempo.toString()
            Toast.makeText(this, "Paso: 11, Instante: $paso11", Toast.LENGTH_SHORT).show()
            countDownTimer.cancel()
            //mostrarTiempoHigieneLM()
        }
    }



    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let{
            mFile -> File(mFile, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir

    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(Constants.FILE_NAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis())+".jpg")

        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOption, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Foto guardada."

                    Toast.makeText(this@LM,"$msg $savedUri", Toast.LENGTH_SHORT).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(Constants.TAG, "Error al tomar foto.", exception)
                }

            }
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if(requestCode == Constants.REQUEST_CODE_PERMISSIONS){
            if(allPermissionGranted()){
                startCamera()
            }else{
                Toast.makeText(this,"No se ha autorizado a la aplicación.",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
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

    private fun allPermissionGranted() =
        Constants.REQUIRED_PERMISSIONS.all {
            val b = ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
            b
        }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

}


