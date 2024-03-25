package com.example.boggle

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.boggle.databinding.FragmentControlBinding

private const val TAG = "ControlFragment"

class ControlFragment : Fragment() {

    private var listener: ControlInterface? = null

    interface ControlInterface {
        fun newGame(data: Boolean)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is ControlInterface) {
            context
        } else {
            throw RuntimeException("$context must implement ControlInterface")
        }
    }

    private fun signalNewGame(){
        listener?.newGame(true)
    }

    private var score = 0

    private var _binding: FragmentControlBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentControlBinding.inflate(inflater, container, false)

        binding.newGameButton.setOnClickListener(){
            signalNewGame()
            score = 0
            updateScoreDisplay(0)
        }

        return binding.root
    }

    fun updateScoreDisplay(points: Int) {
        score += points
        binding.scoreDisplay.text = "Score: ${score.toString()}"
        Log.d(TAG, "Updated score display: $score")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}