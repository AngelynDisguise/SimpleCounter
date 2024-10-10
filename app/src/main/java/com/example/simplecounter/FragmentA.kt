package com.example.simplecounter

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.simplecounter.viewModels.FragmentAViewModel
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.simplecounter.viewModels.MyViewModel
import kotlin.math.sqrt

class FragmentA : Fragment() {
    // Unique to the caller (FragmentA)
    private val viewModel: FragmentAViewModel by viewModels()

    // Shared with all fragments within MainActivity (Fragment B)
    private val sharedViewModel: MyViewModel by activityViewModels()

    // Sharing ViewModels in Java:
    // private MyViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

    private lateinit var isPrimeText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("FragmentA", "FragmentA inflated")

        isPrimeText = view.findViewById(R.id.textPrimeResult)

        // Observe the count data from the shared view model
        sharedViewModel.count.observe(viewLifecycleOwner) { count ->
            toggleUI(isPrime(count))  // set UI according to count
            Log.d("FragmentA", "FragmentA received count $count from ViewModel")
        }
    }

    private fun isPrime(number: Int): Boolean {
        if (number <= 1) return false
        for (i in 2..sqrt(number.toDouble()).toInt()) {
            if (number % i == 0) return false
        }
        return true
    }

    private fun toggleUI(isPrime: Boolean) {
        isPrimeText.text = if (isPrime) "YES" else "NO"
        isPrimeText.setTextColor(if (isPrime) Color.RED else Color.BLUE)
    }

}