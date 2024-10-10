package com.example.simplecounter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.simplecounter.viewModels.MyViewModel

class FragmentB : Fragment() {
    // Shared with MainActivity and FragmentA
    private val sharedViewModel: MyViewModel by activityViewModels()

    private lateinit var editText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("FragmentB", "FragmentB inflated")

        editText = view.findViewById(R.id.editTextResetNumber)

        sharedViewModel.count.observe(viewLifecycleOwner) { count ->
            editText.hint = count.toString()
            Log.d("FragmentB", "FragmentB received count $count from ViewModel")
        }

        editText.setOnEditorActionListener { _, actionId, _ ->
            val input = editText.text.toString()
            if (actionId == EditorInfo.IME_ACTION_DONE && input.isNotEmpty()) {
                val number: Int = input.toInt()
                sharedViewModel.reset(number)
                editText.text.clear()
                Log.d("FragmentB", "Resetting Count to: $number")
                true
            } else {
                Log.d("FragmentB", "Nothing to Reset")
                false
            }
        }

    }

}