package com.example.boggle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity(), GameFragment.ScoreInterface, ControlFragment.ControlInterface {
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

