package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateTv()

        binding.btnUpdate.setOnClickListener {
            LogUtil.i("Button clicked!")
            updateTv()
        }

    }

    private var isMain = System.currentTimeMillis() % 2 == 0L       // initial random value

    private fun updateTv(){
        lifecycleScope.launchWhenStarted {
            if (isMain)
                onMain()
            else
                onIO()
            isMain = !isMain
        }
    }

    private suspend fun onMain() = withContext(Dispatchers.Main){
        binding.tvHello.text = "Hello Main World!"
        LogUtil.d("Hello From Main Thread")
    }

    /**
     *   [X] Don't try this in a production project ;)
     */
    private suspend fun onIO() = withContext(Dispatchers.IO){
        binding.tvHello.text = "Hello IO World!"
        //findViewById<TextView>(R.id.tvHello).text = "Hello IO World!"
        LogUtil.wtf("Hello From IO Thread")
    }

}

/**
 *    Case = IO/Main thread  + TextView.setText
 *
 *    Result :
 *
 *    1. Text will be updated on screen in both cases
 *    2. App will crash in both cases
 *    3. App will crash in IO thread case
 *    4. App will crash in Main thread case
 *
 */
