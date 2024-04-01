package com.example.boggle

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.widget.Toast

import java.util.Objects
import kotlin.math.sqrt

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity(), GameFragment.ScoreInterface, ControlFragment.ControlInterface {
    // Variables for Shake Detection
    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f


    override fun updateScore(score: Int?) {
        if (score != null) {
            updateMainScore(score)
        }
        else {
            Log.e(TAG, "Data is null")
        }
    }

    override fun newGame(signal: Boolean) {
        if (signal) {
            newGame()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Shake Detection
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        Objects.requireNonNull(sensorManager)!!
            .registerListener(sensorListener, sensorManager!!
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)

        acceleration = 0f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH


    }

    override fun onResume() {
        sensorManager?.registerListener(sensorListener, sensorManager!!.getDefaultSensor(
            Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }

    override fun onPause() {
        sensorManager!!.unregisterListener(sensorListener)
        super.onPause()
    }
    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta

            if (acceleration > 8 ) {
                Toast.makeText(
                    this@MainActivity,
                    "New Game!",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "Shake event detected, launching new game")
                newGame()

            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    }
    fun updateMainScore(newScore: Int) {
        Log.d(TAG, "New score: $newScore")
        val controlFrag = supportFragmentManager.findFragmentById(R.id.control_fragment_container) as? ControlFragment
        controlFrag?.updateScoreDisplay(newScore)

    }

    fun newGame(){
        Log.d(TAG, "New Game Starting")
        val gameFrag = supportFragmentManager.findFragmentById(R.id.game_fragment_container) as? GameFragment
        gameFrag?.newGame()

    }





}

