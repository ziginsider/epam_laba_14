package io.github.ziginsider.epam_laba14

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import io.github.ziginsider.epam_laba14.cache.ImageCache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.*

object ImageLoader {

    //TODO set capacity?
    private val cache = ImageCache(10)

    private var threadCount: Int? = null

    private val threadPool: DownloadCompletionService? = null
        get() = field ?: DownloadCompletionService(Executors.newFixedThreadPool(threadCount
                ?: Runtime.getRuntime().availableProcessors() * 2))

    fun displayImage(view: ImageView, url: String) {
        synchronized(cache) {
            val bitmap = cache.get(url)
            if (bitmap == null) {
                //TODO start downloading... value = ...
                ConsumerThread(threadPool!!).start()
                threadPool!!.submit(ImageDownloadTask(url, view))

                //cache.put(url, value)
            } else {
                addImage(view, bitmap)
            }
        }
    }

    fun sizeCache(newSize: Int) {
        cache.resize(newSize)
    }

    fun capacityCache(newCapacity: Int) {
        cache.setCapacity(newCapacity)
    }

    fun threadCount(newCount: Int) {
        threadCount = newCount
    }

    private fun addImage(view: ImageView, bitmap: Bitmap) {
        Handler(Looper.getMainLooper()).post {
            view.setImageBitmap(bitmap)
        }
    }

    private class ImageDownloadTask(val url: String, val view: ImageView) : Callable<Image> {

        override fun call(): Image {
            return downloadImage(url, view)
        }

        private fun downloadImage(url: String, view: ImageView): Image {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url(url)
                    .build()
            var response: Response? = null
            var bitmap: Bitmap? = null
            try {
                response = client.newCall(request).execute()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (response?.isSuccessful ?: false) {
                //TODO check
                bitmap = BitmapFactory.decodeStream(response?.body()?.byteStream())
            }
            return Image(view, url, bitmap)
        }
    }

    private class DownloadCompletionService(private val executor: ExecutorService)
        : ExecutorCompletionService<Image>(executor) {

        fun shutdown() {
            executor.shutdown()
        }

        fun isTerminated() = executor.isTerminated
    }

    private class ConsumerThread(val executorService: DownloadCompletionService) : Thread() {

        override fun run() {
            super.run()
            try {
                while (!executorService.isTerminated()) {
                    val future = executorService.poll(1, TimeUnit.SECONDS)
                    future?.let {
                        val image = future.get()
                        with(image) {
                            if (bitmap != null) {
                                addImage(view, bitmap)
                                cache.put(url, bitmap)
                            } else {
                                //TODO log that image downloaded unsuccessful
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
