package io.github.ziginsider.epam_laba14

import android.graphics.Bitmap
import android.widget.ImageView
import io.github.ziginsider.epam_laba14.cache.ImageCache
import java.util.concurrent.*

object ImageLoader {

    //TODO set capacity?
    private val cache = ImageCache(10)

    private var threadCount: Int? = null

    fun displayImage(view: ImageView, url: String) {
        synchronized(cache) {
            val bitmap = cache.get(url)
            if (bitmap == null) {
                //TODO start downloading... value = ...
                cache.put(url, value)
            } else {
                //TODO thread??
                view.setImageBitmap(bitmap)
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

    private class ImageDownloadTask(val url: String) : Callable<Bitmap> {

        override fun call(): Bitmap {
            return downloadImage(url)
        }

        private fun downloadImage(url: String): Bitmap {
            //TODO downloading image


        }
    }

    private class DownloadCompletionService(private val executor: ExecutorService)
        : ExecutorCompletionService<Bitmap>(executor) {

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
                        addImage(it.get())
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
