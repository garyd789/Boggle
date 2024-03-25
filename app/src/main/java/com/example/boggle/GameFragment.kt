package com.example.boggle

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.boggle.databinding.FragmentGameBinding
import java.io.IOException
import kotlin.random.Random


private const val TAG = "GameFragment"

class GameFragment: Fragment() {

    private var listener: ScoreInterface? = null
    interface ScoreInterface {
        fun updateScore(data: Int?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is ScoreInterface) {
            context
        } else {
            throw RuntimeException("$context must implement ScoreInterface")
        }
    }
    private fun updateGameScore() {
        listener?.updateScore(10)
    }


    private val alphabet = arrayListOf<Char>('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z')
    private val dictionaryList: ArrayList<String> = arrayListOf("")
    private var userInput = ""
    private var _binding: FragmentGameBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)

        // Button and dictionary setup
        loadDictionaryData()
        newGame()


        // Button Functionality
        binding.button.setOnClickListener() {
            pickLetter(binding.button.text.toString())
            Log.d(TAG, "${binding.button.text}")
        }
        binding.button2.setOnClickListener() {
            pickLetter(binding.button2.text.toString())
            Log.d(TAG, "${binding.button2.text}")
        }
        binding.button3.setOnClickListener() {
            pickLetter(binding.button3.text.toString())
            Log.d(TAG, "${binding.button3.text}")
        }
        binding.button4.setOnClickListener() {
            pickLetter(binding.button4.text.toString())
            Log.d(TAG, "${binding.button4.text}")
        }
        binding.button5.setOnClickListener() {
            pickLetter(binding.button5.text.toString())
            Log.d(TAG, "${binding.button5.text}")
        }
        binding.button6.setOnClickListener() {
            pickLetter(binding.button6.text.toString())
            Log.d(TAG, "${binding.button6.text}")
        }
        binding.button7.setOnClickListener() {
            pickLetter(binding.button7.text.toString())
            Log.d(TAG, "${binding.button7.text}")
        }
        binding.button8.setOnClickListener() {
            pickLetter(binding.button8.text.toString())
            Log.d(TAG, "${binding.button8.text}")
        }
        binding.button9.setOnClickListener() {
            pickLetter(binding.button9.text.toString())
            Log.d(TAG, "${binding.button9.text}")
        }
        binding.button10.setOnClickListener() {
            pickLetter(binding.button10.text.toString())
            Log.d(TAG, "${binding.button10.text}")
        }
        binding.button11.setOnClickListener() {
            pickLetter(binding.button11.text.toString())
            Log.d(TAG, "${binding.button11.text}")
        }
        binding.button12.setOnClickListener() {
            pickLetter(binding.button12.text.toString())
            Log.d(TAG, "${binding.button12.text}")
        }
        binding.button13.setOnClickListener() {
            pickLetter(binding.button13.text.toString())
            Log.d(TAG, "${binding.button13.text}")
        }
        binding.button14.setOnClickListener() {
            pickLetter(binding.button14.text.toString())
            Log.d(TAG, "${binding.button14.text}")
        }
        binding.button15.setOnClickListener() {
            pickLetter(binding.button15.text.toString())
            Log.d(TAG, "${binding.button15.text}")
        }
        binding.button16.setOnClickListener() {
            pickLetter(binding.button16.text.toString())
            Log.d(TAG, "${binding.button16.text}")
        }

        binding.clearButton.setOnClickListener() {
            clearWord()
            Toast.makeText(
                requireContext(),
                "Word Cleared",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.submitButton.setOnClickListener() {
            submitWord()
        }


        return binding.root


    }



    private fun setLetter(): Char {
        val randomNumber = Random.nextInt(0, 26)
        return alphabet[randomNumber]
    }

    private fun pickLetter(letter:String) {
        userInput += letter
        Log.d(TAG, "Current word: $userInput")
        binding.userInput.setText(userInput)
    }

    private fun clearWord() {
        userInput = ""
        binding.userInput.setText("")
    }

    private fun submitWord() {
        if (userInput in dictionaryList) {
            Toast.makeText(
                requireContext(),
                "Congrats you won points",
                Toast.LENGTH_SHORT
            ).show()
            updateGameScore()
        }
        else {
            Toast.makeText(
                requireContext(),
                "Sorry word not in dictionary!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun loadDictionaryData(){
        try {
            context?.assets?.open("dictionary.txt")?.bufferedReader().use  { reader ->
                if (reader != null) {
                    reader.forEachLine { line ->
                        dictionaryList.add(line)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

     fun newGame(){
        binding.button.setText(setLetter().toString())
        binding.button2.setText(setLetter().toString())
        binding.button3.setText(setLetter().toString())
        binding.button4.setText(setLetter().toString())
        binding.button5.setText(setLetter().toString())
        binding.button6.setText(setLetter().toString())
        binding.button7.setText(setLetter().toString())
        binding.button8.setText(setLetter().toString())
        binding.button9.setText(setLetter().toString())
        binding.button10.setText(setLetter().toString())
        binding.button11.setText(setLetter().toString())
        binding.button12.setText(setLetter().toString())
        binding.button13.setText(setLetter().toString())
        binding.button14.setText(setLetter().toString())
        binding.button15.setText(setLetter().toString())
        binding.button16.setText(setLetter().toString())
    }
}

