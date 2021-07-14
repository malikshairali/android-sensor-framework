package com.example.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class AvailableSensors : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var textSensorsList: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_sensors)

        setAdView()
        setSensorsList()
    }

    private fun setSensorsList() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        var availableSensors = ""
        deviceSensors.forEach { sensor ->
            availableSensors += sensor.name + "\n"
        }
        textSensorsList = findViewById(R.id.available_sensors)
        textSensorsList.text = availableSensors
    }

    private fun setAdView() {
        MobileAds.initialize(this) {}
        val mAdView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
}