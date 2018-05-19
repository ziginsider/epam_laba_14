package io.github.ziginsider.epam_laba14.model

import com.google.gson.annotations.SerializedName

class Photo(@SerializedName("id") val id: String,
            @SerializedName("title") val title: String,
            @SerializedName("url_c") val url: String,
            @SerializedName("height_c") val height: Int,
            @SerializedName("width_c") val width: Int)