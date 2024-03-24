package com.example.boggle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity(), BoggleInterface {
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    override fun updateScore(newScore: Int) {
        Log.d(TAG, "New score: $newScore")
        val fragment = supportFragmentManager.findFragmentById(R.id.control_fragment_container) as? ControlFragment
        fragment?.updateScoreDisplay(newScore)
    }


}

