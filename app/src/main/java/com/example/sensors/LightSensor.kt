package com.example.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class LightSensor : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private lateinit var textSensorValues: TextView
    private lateinit var bulb: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_sensor)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        textSensorValues = findViewById(R.id.sensorValues)
        bulb = findViewById(R.id.bulb_on)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val lux = event!!.values[0]
        textSensorValues.text = "Brightness: $lux"
        bulb.alpha = lux / 40
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, 5000000);
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this);
    }
}