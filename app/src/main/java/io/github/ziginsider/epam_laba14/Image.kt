package io.github.ziginsider.epam_laba14

import android.graphics.Bitmap
import android.widget.ImageView

/**
 * Consists data for [ImageLoader] image
 *
 * Describes the image data: [view] container view for the image, [url] url image,
 * [bitmap] bitmap image
 *
 * @author Alex Kisel
 * @since 2018-05-05
 */
class Image(val view: ImageView, val url: String, val bitmap: Bitmap?)
