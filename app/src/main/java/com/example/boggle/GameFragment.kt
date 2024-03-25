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
    private fun updateGameScore(points: Int) {
        listener?.updateScore(points)
    }


    private val alphabet = arrayListOf<Char>('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z')
    private val dictionaryList: ArrayList<String> = arrayListOf("")
    private var vowelEnsurer = 0;
    private var userInput = ""
    private var _binding: FragmentGameBinding? = null

    private val buttonMapping = mapOf(
        "button1" to listOf("button2", "button5", "button6"),
        "button2" to listOf("button1", "button3", "button5", "button6", "button7"),
        "button3" to listOf("button2", "button4", "button6", "button7","button8"),
        "button4" to listOf("button3", "button7", "button8"),
        "button5" to listOf("button1", "button2", "button6", "button9", "button10"),
        "button6" to listOf("button1", "button2", "button3", "button5", "button7", "button9", "button10", "button11"),
        "button7" to listOf("button2", "button3", "button4", "button6", "button7", "button8", "button10", "button11", "button12"),
        "button8" to listOf("button3", "button4", "button7", "button11", "button12"),
        "button9" to listOf("button5", "button6", "button10", "button13", "button14"),
        "button10" to listOf("button5", "button6", "button7", "button9", "button11", "button13", "button14", "button15"),
        "button11" to listOf("button6", "button7", "button8", "button10", "button12", "button14", "button15", "button16"),
        "button12" to listOf("button7", "button8", "button11", "button15", "button16"),
        "button13" to listOf("button9", "button10", "button14"),
        "button14" to listOf("button9", "button10", "button11", "button13", "button15"),
        "button15" to listOf("button10", "button11", "button12", "button14", "button16"),
        "button16" to listOf("button11", "button12", "button15")
    )

    private var currentPath = arrayListOf("")
    private var possiblePaths = listOf("")
    private var incorrectPathing = false;
    private var pastWords = arrayListOf("")

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
            buildPath("button1")
            Log.d(TAG, "${binding.button.text}")
            binding.button.isEnabled = false
        }
        binding.button2.setOnClickListener() {
            pickLetter(binding.button2.text.toString())
            buildPath("button2")
            Log.d(TAG, "${binding.button2.text}")
            binding.button2.isEnabled = false
        }
        binding.button3.setOnClickListener() {
            pickLetter(binding.button3.text.toString())
            buildPath("button3")
            Log.d(TAG, "${binding.button3.text}")
            binding.button3.isEnabled = false
        }
        binding.button4.setOnClickListener() {
            pickLetter(binding.button4.text.toString())
            buildPath("button4")
            Log.d(TAG, "${binding.button4.text}")
            binding.button4.isEnabled = false
        }
        binding.button5.setOnClickListener() {
            pickLetter(binding.button5.text.toString())
            buildPath("button5")
            Log.d(TAG, "${binding.button5.text}")
            binding.button5.isEnabled = false
        }
        binding.button6.setOnClickListener() {
            pickLetter(binding.button6.text.toString())
            buildPath("button6")
            Log.d(TAG, "${binding.button6.text}")
            binding.button6.isEnabled = false
        }
        binding.button7.setOnClickListener() {
            pickLetter(binding.button7.text.toString())
            buildPath("button7")
            Log.d(TAG, "${binding.button7.text}")
            binding.button7.isEnabled = false
        }
        binding.button8.setOnClickListener() {
            pickLetter(binding.button8.text.toString())
            buildPath("button8")
            Log.d(TAG, "${binding.button8.text}")
            binding.button8.isEnabled = false
        }
        binding.button9.setOnClickListener() {
            pickLetter(binding.button9.text.toString())
            buildPath("button9")
            Log.d(TAG, "${binding.button9.text}")
            binding.button9.isEnabled = false
        }
        binding.button10.setOnClickListener() {
            pickLetter(binding.button10.text.toString())
            buildPath("button10")
            Log.d(TAG, "${binding.button10.text}")
            binding.button10.isEnabled = false
        }
        binding.button11.setOnClickListener() {
            pickLetter(binding.button11.text.toString())
            buildPath("button11")
            Log.d(TAG, "${binding.button11.text}")
            binding.button11.isEnabled = false
        }
        binding.button12.setOnClickListener() {
            pickLetter(binding.button12.text.toString())
            buildPath("button12")
            Log.d(TAG, "${binding.button12.text}")
            binding.button12.isEnabled = false
        }
        binding.button13.setOnClickListener() {
            pickLetter(binding.button13.text.toString())
            buildPath("button13")
            Log.d(TAG, "${binding.button13.text}")
            binding.button13.isEnabled = false
        }
        binding.button14.setOnClickListener() {
            pickLetter(binding.button14.text.toString())
            buildPath("button14")
            Log.d(TAG, "${binding.button14.text}")
            binding.button14.isEnabled = false
        }
        binding.button15.setOnClickListener() {
            pickLetter(binding.button15.text.toString())
            buildPath("button15")
            Log.d(TAG, "${binding.button15.text}")
            binding.button15.isEnabled = false
        }
        binding.button16.setOnClickListener() {
            pickLetter(binding.button16.text.toString())
            buildPath("button16")
            Log.d(TAG, "${binding.button16.text}")
            binding.button16.isEnabled = false
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



    private fun setLetter(id: String): Char {
        Log.d(TAG, "VowelEnsurer: $vowelEnsurer")
        val vowels = listOf('A', 'E', 'I', 'O', 'U')
        if (vowelEnsurer < 2) {
            if (id == "button7" || id == "button10"){
                vowelEnsurer++
                return vowels.random()
            }
            val randomNumber = Random.nextInt(0, 100)
            if (randomNumber > 50) {
                vowelEnsurer++
                return vowels.random()
            } else {
                val alphaNumber = Random.nextInt(0,26)
                if (alphaNumber in listOf(0, 4, 8, 14, 20)) {
                    vowelEnsurer++
                }
                return alphabet[alphaNumber]
            }
        }
        else {
            val alphaNumber = Random.nextInt(0,26)
            return alphabet[alphaNumber]
        }


    }

    private fun pickLetter(letter:String) {
        userInput += letter
        Log.d(TAG, "Current word: $userInput")
        binding.userInput.setText(userInput)
    }

    private fun buildPath(buttonId: String) {
        if (currentPath == listOf("") ) {
            possiblePaths = buttonMapping[buttonId] ?: return
            currentPath = arrayListOf(buttonId)
        }
        else {
            if (buttonId !in possiblePaths){
                incorrectPathing = true
                Log.d(TAG, "Incorrect Pathing")
            }
            possiblePaths = buttonMapping[buttonId] ?: return
            currentPath += buttonId
        }
        Log.d(TAG, "Current Path: $currentPath")

    }

    private fun clearWord() {
        userInput = ""
        possiblePaths = listOf("")
        currentPath = arrayListOf("")
        binding.userInput.setText("")
    }

    private fun submitWord() {
        if (userInput.lowercase() in dictionaryList && !incorrectPathing
            && userInput.lowercase() !in pastWords) {
            val points = calculatePoints(userInput)
            if (points > 0) {
                Toast.makeText(
                    requireContext(),
                    "Congrats you won points",
                    Toast.LENGTH_SHORT
                ).show()
                pastWords += userInput.lowercase()
            }
            else {
                Toast.makeText(
                    requireContext(),
                    "Sorry word is invalid! -10",
                    Toast.LENGTH_SHORT
                ).show()
            }
            updateGameScore(points)
        }
        else {
            if (incorrectPathing) {
                Toast.makeText(
                    requireContext(),
                    "Sorry incorrect pathing! -10",
                    Toast.LENGTH_SHORT
                ).show()
                updateGameScore(-10)
            }
            else {
                Toast.makeText(
                    requireContext(),
                    "Sorry word not in dictionary! -10",
                    Toast.LENGTH_SHORT
                ).show()
                updateGameScore(-10)
            }

        }
        enableButtons()
        clearWord()
    }

    private fun calculatePoints(word: String): Int {
        if (userInput.length < 4) {
            return -10
        }
        var points = 0
        var multiplier = false
        var vowelCount = 0
        val vowels = setOf('A', 'E', 'I', 'O', 'U')
        val doubles = setOf('S','Z','P','X')
        for (char in word) {
            if (char.isLetter() && char in vowels) {
                points += 5
                vowelCount++
            }
            else if (char.isLetter() && char in doubles ) {
                multiplier = true
            }
            else {
                points += 1
            }
        }
        if (multiplier) {
            points *= 2
        }
        if (vowelCount < 2) {
            return -10
        }
        Log.d(TAG, "Vowel Count: $vowelCount")
        return points
    }

    private fun loadDictionaryData(){
        try {
            context?.assets?.open("dictionary.txt")?.bufferedReader().use  { reader ->
                if (reader != null) {
                    reader.forEachLine { line ->
                        dictionaryList.add(line.lowercase())
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

     fun newGame(){
         binding.button.setText(setLetter("button1").toString())
         binding.button2.setText(setLetter("button2").toString())
         binding.button3.setText(setLetter("button3").toString())
         binding.button4.setText(setLetter("button4").toString())
         binding.button5.setText(setLetter("button5").toString())
         binding.button6.setText(setLetter("button6").toString())
         binding.button7.setText(setLetter("button7").toString())
         binding.button8.setText(setLetter("button8").toString())
         binding.button9.setText(setLetter("button9").toString())
         binding.button10.setText(setLetter("button10").toString())
         binding.button11.setText(setLetter("button11").toString())
         binding.button12.setText(setLetter("button12").toString())
         binding.button13.setText(setLetter("button13").toString())
         binding.button14.setText(setLetter("button14").toString())
         binding.button15.setText(setLetter("button15").toString())
         binding.button16.setText(setLetter("button16").toString())

         enableButtons()
         currentPath = arrayListOf("")
         pastWords = arrayListOf("")
         incorrectPathing = false
         clearWord()
    }

    private fun enableButtons(){
        binding.button.isEnabled = true
        binding.button2.isEnabled = true
        binding.button3.isEnabled = true
        binding.button4.isEnabled = true
        binding.button5.isEnabled = true
        binding.button6.isEnabled = true
        binding.button7.isEnabled = true
        binding.button8.isEnabled = true
        binding.button9.isEnabled = true
        binding.button10.isEnabled = true
        binding.button11.isEnabled = true
        binding.button12.isEnabled = true
        binding.button13.isEnabled = true
        binding.button14.isEnabled = true
        binding.button15.isEnabled = true
        binding.button16.isEnabled = true

    }
}

