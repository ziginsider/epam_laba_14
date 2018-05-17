package io.github.ziginsider.epam_laba14

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import io.github.ziginsider.epam_laba14.cache.ImageCache
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
                threadPool!!.submit(ImageDownloadTask(url))

                cache.put(url, value)
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
            //TODO downloading image


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
                        val image = it.get()
                        addImage(image.view, image.bitmap)
                        cache.put(image.url, image.bitmap)
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
