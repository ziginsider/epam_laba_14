package io.github.ziginsider.epam_laba14.model

import com.google.gson.annotations.SerializedName

class RecentPhotos(@SerializedName("photos") val photos: Photos,
                   @SerializedName("stat") val status: String)
