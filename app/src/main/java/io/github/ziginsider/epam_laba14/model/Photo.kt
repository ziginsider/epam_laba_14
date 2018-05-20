package io.github.ziginsider.epam_laba14.model

import com.google.gson.annotations.SerializedName

class Photo(@SerializedName("id") val id: String,
            @SerializedName("title") val title: String,
            @SerializedName("url_n") val urlSmall: String?,
            @SerializedName("url_o") val urlOriginal: String?,
            @SerializedName("height_o") val height: Int?,
            @SerializedName("width_o") val width: Int?)
