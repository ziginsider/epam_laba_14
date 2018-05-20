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
import io.github.ziginsider.epam_laba14.utils.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private var recyclerAdapter: RecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.visibility = View.VISIBLE
        RetrofitService.create()
                .recentPhotos(METHOD, API_KEY, FORMAT, 10, 1, JSON_RAW, URL_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    setUpRecyclerView(response.photos.data)
                    progressBar.visibility = View.GONE
                }, { error ->
                    error.printStackTrace()
                })

        logi(TAG, "[ COUNT THREADS = ${ImageLoader.threadCount} ]")
        logi(TAG, "[ COUNT CAPACITY CACHE = ${ImageLoader.cacheCapacity} ]")
        logi(TAG, "[ SIZE CACHE = ${ImageLoader.cacheSize} ]")
    }

    private fun setUpRecyclerView(photos: List<Photo>) {
        recyclerAdapter = RecyclerViewAdapter(R.layout.item_view,
                { toast("I'm photo with title = ${it.title}") })
        recyclerAdapter?.submitList(photos)
        with(recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = recyclerAdapter
        }
    }
}
