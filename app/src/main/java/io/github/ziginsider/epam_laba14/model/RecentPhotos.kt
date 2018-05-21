package io.github.ziginsider.epam_laba14.model

import com.google.gson.annotations.SerializedName

/**
 * Model of RecentPhotos (JSON response from api.flickr.com)
 *
 * Describes the image data: [photos] an [Photos] json object,
 * [status] if response OK
 *
 * @author Alex Kisel
 * @since 2018-05-05
 */
class RecentPhotos(@SerializedName("photos") val photos: Photos,
                   @SerializedName("stat") val status: String)
