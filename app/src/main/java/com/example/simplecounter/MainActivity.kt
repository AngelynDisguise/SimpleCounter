package com.example.simplecounter

import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.simplecounter.viewModels.MyViewModel

class MainActivity : AppCompatActivity() {

    private val button: Button by lazy { findViewById(R.id.buttonCount)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up Fragments
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container_a, FragmentA())
                replace(R.id.fragment_container_b, FragmentB())
            }
        }

        /** ViewModelProvider creates ViewModels for a scope.
        * Takes the scope (Activity or Fragment) in the constructor.
        * The Activity or Fragment will become the ViewModelStoreOwner, and the ViewModel gets put in its ViewModelStore.
        * ViewModel will live in memory until ViewModelStoreOwner dies.
        * (Fun: any asynchronous work from the ViewModel still persists!)
        */
        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        // ViewModels in Java:
        // MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        /** Or, use Kotlin delegates for less code. This does all of the above for you.
         * Delegates uses ViewModelProvider under the hood, using the scope of the function caller.
        */
        val viewModel2: MyViewModel by viewModels() // uses the scope of the function caller (in this case, this Activity).

        // LiveData listening to ViewModel data
        viewModel2.count.observe(this) { count ->
            button.text = count.toString()
            Log.d("MainActivity", "MainActivity received count $count from ViewModel")
        }

        /* LiveData in Java
        viewModel2.getCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                button.setText(String.valueOf(count));
                Log.d("MainActivity", "MainActivity received count " + count + " from ViewModel");
            }
        });
        */

        // On click, send UI event to ViewModel
        button.setOnClickListener {
            viewModel.increment()
        }

    }
}