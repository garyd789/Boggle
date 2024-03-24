package com.example.boggle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.boggle.databinding.FragmentControlBinding

private const val TAG = "ControlFragment"

class ControlFragment : Fragment() {

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



        return binding.root
    }

    fun updateScoreDisplay(newScore: Int) {
        binding.scoreDisplay.text = "Score: ${newScore.toString()}"
        Log.d(TAG, "Updated score display: $newScore")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}