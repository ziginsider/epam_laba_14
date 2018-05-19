package io.github.ziginsider.epam_laba14

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.ziginsider.epam_laba14.utils.logi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val count = Runtime.getRuntime().availableProcessors()

        logi(TAG, "[ COUNT PROCESSORS = $count ]")

        ImageLoader.threadCount = 7
        ImageLoader.cacheCapacity = 15
        //ImageLoader.cacheSize = 1024 * 1024 * 4

        ImageLoader.displayImage(imageView, "http://www.oiseaux.net/photos/robert.balestra/images/pic.epeiche.roba.3p.jpg")
        ImageLoader.displayImage(imageView2, "http://www.realbirder.com/EcuadorD/Blue-grayTanagerEasternSp.JPG")
        ImageLoader.displayImage(imageView3, "http://www.oiseaux.net/photos/alain.chappuis/images/chardonneret.elegant.alch.11g.jpg")
        ImageLoader.displayImage(imageView4, "http://www.uroc5962.fr/wp-content/uploads/2018/04/BEBOPpoitiers123309451399_art.jpg")
        ImageLoader.displayImage(imageView5, "http://www.oiseaux.net/photos/jules.fouarge/images/pic.epeiche.jufo.8g.jpg")

        logi(TAG, "[ COUNT THREADS = ${ImageLoader.threadCount} ]")
        logi(TAG, "[ COUNT CAPACITY CACHE = ${ImageLoader.cacheCapacity} ]")
        logi(TAG, "[ SIZE CACHE = ${ImageLoader.cacheSize} ]")
    }
}
