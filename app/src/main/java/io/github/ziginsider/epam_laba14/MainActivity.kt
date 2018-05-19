package io.github.ziginsider.epam_laba14

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.github.ziginsider.epam_laba14.utils.logi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val count = Runtime.getRuntime().availableProcessors()

        logi(TAG, "[ COUNT PROCESSORS = $count ]")

        ImageLoader.displayImage(imageView, "http://www.oiseaux.net/photos/robert.balestra/images/pic.epeiche.roba.3p.jpg")
    }
}
