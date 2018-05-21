package io.github.ziginsider.epam_laba14.model

import com.google.gson.annotations.SerializedName

/**
 * Model of Photos (JSON response from api.flickr.com)
 *
 * Describes the image data: [page] current page number of the response,
 * [data] list of [Photo]
 *
 * @author Alex Kisel
 * @since 2018-05-05
 */
class Photos(@SerializedName("page") val page: Int,
             @SerializedName("photo") val data: List<Photo>)
