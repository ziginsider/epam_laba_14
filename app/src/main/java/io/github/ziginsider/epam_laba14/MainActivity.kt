package io.github.ziginsider.epam_laba14

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.github.ziginsider.epam_laba14.adapter.RecyclerViewAdapter
import io.github.ziginsider.epam_laba14.model.Photo
import io.github.ziginsider.epam_laba14.retrofit.Contract.API_KEY
import io.github.ziginsider.epam_laba14.retrofit.Contract.FORMAT
import io.github.ziginsider.epam_laba14.retrofit.Contract.JSON_RAW
import io.github.ziginsider.epam_laba14.retrofit.Contract.METHOD
import io.github.ziginsider.epam_laba14.retrofit.Contract.URL_TYPE
import io.github.ziginsider.epam_laba14.retrofit.RetrofitService
import io.github.ziginsider.epam_laba14.utils.logi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val count = Runtime.getRuntime().availableProcessors()

        logi(TAG, "[ COUNT PROCESSORS = $count ]")

//        ImageLoader.threadCount = 7
//        ImageLoader.cacheCapacity = 15
        //ImageLoader.cacheSize = 1024 * 1024 * 4

//        ImageLoader.displayImage(imageView, "http://www.oiseaux.net/photos/robert.balestra/images/pic.epeiche.roba.3p.jpg")
//        ImageLoader.displayImage(imageView2, "http://www.realbirder.com/EcuadorD/Blue-grayTanagerEasternSp.JPG")
//        ImageLoader.displayImage(imageView3, "http://www.oiseaux.net/photos/alain.chappuis/images/chardonneret.elegant.alch.11g.jpg")
//        ImageLoader.displayImage(imageView4, "http://www.uroc5962.fr/wp-content/uploads/2018/04/BEBOPpoitiers123309451399_art.jpg")
//        ImageLoader.displayImage(imageView5, "http://www.oiseaux.net/photos/jules.fouarge/images/pic.epeiche.jufo.8g.jpg")


        progressBar.visibility = View.VISIBLE
        RetrofitService.create()
                .recentPhotos(METHOD, API_KEY, FORMAT, 10, 1, JSON_RAW, URL_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    response -> addPhotos(response.photos.data)
                    progressBar.visibility = View.GONE
                }, { error ->
                    error.printStackTrace()
                })

        logi(TAG, "[ COUNT THREADS = ${ImageLoader.threadCount} ]")
        logi(TAG, "[ COUNT CAPACITY CACHE = ${ImageLoader.cacheCapacity} ]")
        logi(TAG, "[ SIZE CACHE = ${ImageLoader.cacheSize} ]")
    }

    private fun addPhotos(photos: List<Photo>) {
        val recyclerAdapter = RecyclerViewAdapter(R.layout.item_view, {})
        recyclerAdapter.submitList(photos)

        with (recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = recyclerAdapter
        }


    }
}
