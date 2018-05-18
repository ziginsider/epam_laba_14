package io.github.ziginsider.epam_laba14

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val count = Runtime.getRuntime().availableProcessors()

        Log.d("TAG", "[ COUNT PROCESSORS = $count ]")
    }
}
