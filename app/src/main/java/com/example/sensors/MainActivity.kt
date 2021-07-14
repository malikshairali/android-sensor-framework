package com.example.sensors

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor


class MainActivity : AppCompatActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        initUI()
        authenticate();
    }

    private fun authenticate() {
        val biometricManager = BiometricManager.from(this)
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            == BiometricManager.BIOMETRIC_SUCCESS
        ) {
            displayBiometricPrompt()
        } else {
            Toast.makeText(this, "Biometric not supported by the device!", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayBiometricPrompt() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    val message = when(errorCode){
                        BiometricPrompt.ERROR_USER_CANCELED -> {
                            errString.toString()
                        }
                        BiometricPrompt.ERROR_LOCKOUT -> {
                            errString.toString()
                        }
                        BiometricPrompt.ERROR_NEGATIVE_BUTTON -> {
                            "Authenticate via password has yet to implement"
                        }
                        else -> {
                            errString.toString()
                        }
                    }
                    showDialog(message)
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .setNegativeButtonText("Use account password")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun initUI() {
        val btnProximity = findViewById<Button>(R.id.btn_proximity)
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        if (sensor != null)
            btnProximity.setOnClickListener {
                val intent = Intent(this, ProximitySensor::class.java)
                startActivity(intent)
            }
        else
            btnProximity.isEnabled = false

        val btnLightSensor = findViewById<Button>(R.id.btn_light)
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (sensor != null)
            btnLightSensor.setOnClickListener {
                val intent = Intent(this, LightSensor::class.java)
                startActivity(intent)
            }
        else
            btnLightSensor.isEnabled = false

        val btnAccelerometer = findViewById<Button>(R.id.btn_accelerometer)
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (sensor != null)
            btnAccelerometer.setOnClickListener {
                val intent = Intent(this, Accelerometer::class.java)
                startActivity(intent)
            }
        else
            btnAccelerometer.isEnabled = false

        val btnGyroscope = findViewById<Button>(R.id.btn_gyroscope)
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (sensor != null)
            btnGyroscope.setOnClickListener {
                val intent = Intent(this, Gyroscope::class.java)
                startActivity(intent)
            }
        else
            btnGyroscope.isEnabled = false

        val btnMagnetometer = findViewById<Button>(R.id.btn_magnetometer)
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        if (sensor != null)
            btnMagnetometer.setOnClickListener {
                val intent = Intent(this, Magnetometer::class.java)
                startActivity(intent)
            }
        else
            btnMagnetometer.isEnabled = false

        val btnBarometer = findViewById<Button>(R.id.btn_barometer)
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if (sensor != null)
            btnBarometer.setOnClickListener {
                val intent = Intent(this, Barometer::class.java)
                startActivity(intent)
            }
        else
            btnBarometer.isEnabled = false

        val btnAllSensors = findViewById<Button>(R.id.btn_list_all)
        btnAllSensors.setOnClickListener {
            val intent = Intent(this, AvailableSensors::class.java)
            startActivity(intent)
        }
    }

    private fun showDialog(message: String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setTitle("Authentication Error")
            .setCancelable(false)
            .setPositiveButton("Retry") { dialog, id ->
                biometricPrompt.authenticate(promptInfo)
            }
            .show()
    }
}