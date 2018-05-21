package io.github.ziginsider.epam_laba14

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.github.ziginsider.epam_laba14.adapter.EndlessScrollListener
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

/**
 * Сontains a RecyclerView list (see [RecyclerViewAdapter]) with endless scroll listener
 * (see [EndlessScrollListener])
 *
 * The list loads recent photos from api.flickr.com
 *
 * Uploading images is done using [ImageLoader]
 *
 * @author Alex Kisel
 * @since 2018-05-19
 */
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val MAX_LOAD_PAGES_COUNT = 10
    private val COUNT_PAGES_PER_REQUEST = 100

    private var recyclerAdapter: RecyclerViewAdapter? = null
    private var offset = MAX_LOAD_PAGES_COUNT
    private var listPhotos: List<Photo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestToFlickr(offset)
        logi(TAG, "[ COUNT THREADS = ${ImageLoader.threadCount} ]")
        logi(TAG, "[ COUNT CAPACITY CACHE = ${ImageLoader.cacheCapacity} ]")
        logi(TAG, "[ SIZE CACHE = ${ImageLoader.cacheSize} ]")
    }

    private fun setUpRecyclerView(photos: List<Photo>) {
        recyclerAdapter = RecyclerViewAdapter(R.layout.item_view,
                { toast("I'm photo with title = ${it.title}, id = ${it.id}") })
        recyclerAdapter?.submitList(photos)
        with(recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = recyclerAdapter
            addOnScrollListener(EndlessScrollListener({ pagingPhotos() },
                    layoutManager as LinearLayoutManager))
        }
    }

    private fun updateAdapter(photos: List<Photo>) {
        recyclerAdapter?.submitList(photos) ?: setUpRecyclerView(photos)
    }

    private fun requestToFlickr(page: Int) {
        progressBar.visibility = View.VISIBLE
        RetrofitService.create()
                .recentPhotos(METHOD, API_KEY, FORMAT, COUNT_PAGES_PER_REQUEST, page, JSON_RAW,
                        URL_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    listPhotos += response.photos.data
                    updateAdapter(listPhotos)
                    progressBar.visibility = View.GONE
                }, { error ->
                    error.printStackTrace()
                })
    }

    private fun pagingPhotos() {
        if (offset > 1) {
            requestToFlickr(--offset)
        } else {
            toast("The limit of photos is reached...")
        }
    }
}
