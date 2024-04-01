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
            Log.d(TAG, "${binding.button.text}")
            if (buildPath("button1")){
                pickLetter(binding.button.text.toString())
                binding.button.isEnabled = false
            }
        }
        binding.button2.setOnClickListener() {
            Log.d(TAG, "${binding.button2.text}")
            if (buildPath("button2")){
                pickLetter(binding.button2.text.toString())
                binding.button2.isEnabled = false
            }
        }
        binding.button3.setOnClickListener() {
            Log.d(TAG, "${binding.button3.text}")
            if (buildPath("button3")){
                pickLetter(binding.button3.text.toString())
                binding.button3.isEnabled = false
            }
        }
        binding.button4.setOnClickListener() {
            Log.d(TAG, "${binding.button4.text}")
            if (buildPath("button4")){
                pickLetter(binding.button4.text.toString())
                binding.button4.isEnabled = false
            }
        }
        binding.button5.setOnClickListener() {
            Log.d(TAG, "${binding.button5.text}")
            if (buildPath("button5")){
                pickLetter(binding.button5.text.toString())
                binding.button5.isEnabled = false
            }
        }
        binding.button6.setOnClickListener() {
            Log.d(TAG, "${binding.button6.text}")
            if (buildPath("button6")){
                pickLetter(binding.button6.text.toString())
                binding.button6.isEnabled = false
            }
        }
        binding.button7.setOnClickListener() {
            Log.d(TAG, "${binding.button7.text}")
            if (buildPath("button7")){
                pickLetter(binding.button7.text.toString())
                binding.button7.isEnabled = false
            }
        }
        binding.button8.setOnClickListener() {
            Log.d(TAG, "${binding.button8.text}")
            if (buildPath("button8")){
                pickLetter(binding.button8.text.toString())
                binding.button8.isEnabled = false
            }
        }
        binding.button9.setOnClickListener() {
            Log.d(TAG, "${binding.button9.text}")
            if (buildPath("button9")){
                pickLetter(binding.button9.text.toString())
                binding.button9.isEnabled = false
            }
        }
        binding.button10.setOnClickListener() {
            Log.d(TAG, "${binding.button10.text}")
            if (buildPath("button10")){
                pickLetter(binding.button10.text.toString())
                binding.button10.isEnabled = false
            }
        }
        binding.button11.setOnClickListener() {
            Log.d(TAG, "${binding.button11.text}")
            if (buildPath("button11")){
                pickLetter(binding.button11.text.toString())
                binding.button11.isEnabled = false
            }

        }
        binding.button12.setOnClickListener() {
            Log.d(TAG, "${binding.button12.text}")
            if (buildPath("button12")){
                pickLetter(binding.button12.text.toString())
                binding.button12.isEnabled = false
            }

        }
        binding.button13.setOnClickListener() {
            Log.d(TAG, "${binding.button13.text}")
            if (buildPath("button13")){
                pickLetter(binding.button13.text.toString())
                binding.button13.isEnabled = false
            }
        }
        binding.button14.setOnClickListener() {
            Log.d(TAG, "${binding.button14.text}")
            if (buildPath("button14")){
                pickLetter(binding.button14.text.toString())
                binding.button14.isEnabled = false
            }
        }
        binding.button15.setOnClickListener() {
            Log.d(TAG, "${binding.button15.text}")
            if (buildPath("button15")){
                pickLetter(binding.button15.text.toString())
                binding.button15.isEnabled = false
            }
        }
        binding.button16.setOnClickListener() {
            Log.d(TAG, "${binding.button16.text}")
            if (buildPath("button16")){
                pickLetter(binding.button16.text.toString())
                binding.button16.isEnabled = false
            }

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

    private fun buildPath(buttonId: String): Boolean {
        if (currentPath == listOf("") ) {
            possiblePaths = buttonMapping[buttonId] ?: return false
            currentPath = arrayListOf(buttonId)
        }
        else {
            if (buttonId !in possiblePaths){
                Toast.makeText(
                    requireContext(),
                    "Please pick an adjacent letter!",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            possiblePaths = buttonMapping[buttonId] ?: return false
            currentPath += buttonId
        }
        Log.d(TAG, "Current Path: $currentPath")
        return true

    }

    private fun clearWord() {
        userInput = ""
        possiblePaths = listOf("")
        currentPath = arrayListOf("")
        binding.userInput.setText("")
        enableButtons()
    }

    private fun submitWord() {
        if (userInput.lowercase() in pastWords){
            Toast.makeText(
                requireContext(),
                "Word already selected",
                Toast.LENGTH_SHORT
            ).show()
        }
        else if (userInput.lowercase() in dictionaryList) {
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
            Toast.makeText(
                requireContext(),
                "Sorry word not in dictionary! -10",
                Toast.LENGTH_SHORT
            ).show()
            updateGameScore(-10)
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
                points += 1
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

