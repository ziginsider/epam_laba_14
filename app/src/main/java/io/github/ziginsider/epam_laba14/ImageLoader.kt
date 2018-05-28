package io.github.ziginsider.epam_laba14

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import io.github.ziginsider.epam_laba14.cache.ImageCache
import io.github.ziginsider.epam_laba14.utils.loge
import io.github.ziginsider.epam_laba14.utils.logi
import io.github.ziginsider.epam_laba14.utils.resize
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.*

/**
 * Implements a simple image loader
 *
 * The image loader implements the cache [ImageCache] for storing downloaded images
 *
 * Image download tasks [ImageDownloadTask] are placed in [threadPool], which is an implementation
 * of [ExecutorCompletionService]. The [threadPool] downloads images on the URL and puts answers
 * [Image] in the [BlockingQueue] that is monitoring by [ConsumerThread].
 * [ConsumerThread] places downloaded image asynchronously into container view and puts it into
 * cache.
 *
 * @property threadCount number of threads for [DownloadCompletionService]
 * @property cacheSize cache size [ImageCache] in bytes
 * @property cacheCapacity cache capacity (number of elements)
 *
 * @author Alex Kisel
 * @since 2018-05-19
 */
object ImageLoader {

    private val TAG = ImageLoader::class.java.simpleName

    private const val DEFAULT_CACHE_CAPACITY = 100
    private const val DEFAULT_REPEAT_MAP_CAPACITY = 16

    private var cache = ImageCache(DEFAULT_CACHE_CAPACITY)
    private val threadCountInit = Runtime.getRuntime().availableProcessors() * 2
    private var threadPool = DownloadCompletionService(Executors.newFixedThreadPool(threadCountInit))

    var threadCount = threadCountInit
        set(value) {
            threadPool.setThreadCount(value)
            field = value
        }

    var cacheCapacity = DEFAULT_CACHE_CAPACITY
        set(value) {
            logi(TAG, "[ capacityCache($value) ]")
            cache.setCapacity(value)
            field = value
        }

    var cacheSize: Int = cacheCapacity * 1024 * 1024
        set(value) {
            logi(TAG, "[ sizeCache($value) ]")
            cache.resize(value)
            field = value
        }

    val optimalImageSize: Int? = null
        get() = field ?: cacheSize / cacheCapacity

    private val repeatHashMap: HashMap<String, String> = HashMap(DEFAULT_REPEAT_MAP_CAPACITY)

    init {
        logi(TAG, "[ init: start ConsumerThread ]")
        ConsumerThread(threadPool).start()
    }

    /**
     * Downloads an image on the specified url and puts it in the view container
     *
     * @param view view container for image
     * @param url image url
     */
    fun displayImage(view: ImageView, url: String) {
        logi(TAG, "[ displayImage($view, $url) with unique id = ${view.layoutParams} ]")
        synchronized(cache) {
            val bitmap = cache.get(url)
            if (bitmap == null) {
                logi(TAG, "[ start image downloading task ]")
                view.setImageBitmap(null)
                repeatHashMap[view.layoutParams.toString()] = url
                threadPool.submit(ImageDownloadTask(url, view))
            } else {
                logi(TAG, "[ add image from cache ]")
                view.setImageBitmap(bitmap)
            }
        }
    }

    private fun addImage(view: ImageView, bitmap: Bitmap, url: String) {
        logi(TAG, "[ addImage($view, $bitmap) ]")
        if (repeatHashMap[view.layoutParams.toString()] == url) {
            Handler(Looper.getMainLooper()).post {
                view.setImageBitmap(bitmap)
            }
        }
    }

    private class ImageDownloadTask(val url: String, val view: ImageView) : Callable<Image> {

        override fun call(): Image {
            return downloadImage(url, view)
        }

        private fun downloadImage(url: String, view: ImageView): Image {
            logi(TAG, "[ downloadImage($url, $view) ]")
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url(url)
                    .build()
            var response: Response? = null
            var bitmap: Bitmap? = null
            try {
                response = client.newCall(request).execute()
            } catch (e: IOException) {
                loge(TAG, "[ OkHttp request execute error ]")
                e.printStackTrace()
            }
            if (response?.isSuccessful == true) {
                logi(TAG, "[ OkHttp response is successful ]")
                response.use {
                    try {
                        bitmap = BitmapFactory.decodeStream(it.body()?.byteStream())
                        bitmap?.let {
                            if (optimalImageSize!! < it.byteCount) {
                                logi(TAG, "[ old size = ${it.byteCount} ]")
                                bitmap = it.resize(it.width / 2, it.height / 2)
                                logi(TAG, "[ new size ${bitmap?.byteCount} ]")
                            }
                        }
                    } catch (e: Exception) {
                        loge(TAG, "[ BitmapFactory decoding image error. " +
                                "The image data could not be decoded ]")
                        e.printStackTrace()
                    }
                }
            }
            return Image(view, url, bitmap)
        }
    }

    private class DownloadCompletionService(private val executor: ExecutorService)
        : ExecutorCompletionService<Image>(executor) {

        fun setThreadCount(newCount: Int) {
            (executor as ThreadPoolExecutor).corePoolSize = newCount
        }

        fun shutdown() {
            logi(TAG, "[ task shutdown() ]")
            executor.shutdown()
        }

        fun isTerminated() = executor.isTerminated
    }

    private class ConsumerThread(val executorService: DownloadCompletionService) : Thread() {

        override fun run() {
            logi(TAG, "[ ConsumerThread: run() ]")
            super.run()
            try {
                while (!executorService.isTerminated()) {
                    val future = executorService.poll()
                    //logi(TAG, "[ executor.poll() = $future ] ")
                    future?.let {
                        val image = future.get()
                        with(image) {
                            logi(TAG, "[ ConsumerThread: future.get(): url = $url\n" +
                                    "view = $view\n" +
                                    "bitmap = $bitmap ]")
                            if (bitmap != null) {
                                addImage(view, bitmap, url)
                                cache.put(url, bitmap)
                            } else {
                                loge(TAG, "[ The image data could not be decoded ]")
                            }
                        }
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }
        }
    }
}
