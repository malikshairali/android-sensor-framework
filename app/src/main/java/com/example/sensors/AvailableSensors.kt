package com.example.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class AvailableSensors : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var textSensorsList: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_sensors)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        var availableSensors = ""
        deviceSensors.forEach { sensor ->
            availableSensors += sensor.name + "\n"
        }
        textSensorsList = findViewById(R.id.available_sensors)
        textSensorsList.text = availableSensors
    }
}