package io.github.ziginsider.epam_laba14.model

import com.google.gson.annotations.SerializedName

/**
 * Model of Photo (JSON response from api.flickr.com)
 *
 * Describes the image data: [id] id of the photo, [title] title of the photo,
 * [urlSmall] url for small size of the photo, [urlOriginal] url for origin size of the photo,
 * [height] size of height, [width] size of width
 *
 * @author Alex Kisel
 * @since 2018-05-05
 */
class Photo(@SerializedName("id") val id: String,
            @SerializedName("title") val title: String,
            @SerializedName("url_n") val urlSmall: String?,
            @SerializedName("url_o") val urlOriginal: String?,
            @SerializedName("height_o") val height: Int?,
            @SerializedName("width_o") val width: Int?)
