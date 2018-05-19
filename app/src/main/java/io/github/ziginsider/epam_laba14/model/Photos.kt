package io.github.ziginsider.epam_laba14.model

import com.google.gson.annotations.SerializedName

class Photos(@SerializedName("page") val page: Int,
             @SerializedName("photo") val data: List<Photo>)