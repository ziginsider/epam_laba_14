package io.github.ziginsider.epam_laba14.retrofit

/**
 * Contract for requesting to api.flickr.com
 *
 * @author Alex Kisel
 * @since 2018-05-05
 */
object Contract {

    const val API_KEY = "23d9808055fcf4f6e5671f40eb926bcc"
    const val METHOD = "flickr.photos.getRecent"
    const val FORMAT = "json"
    const val JSON_RAW = 1
    const val URL_TYPE = "url_n,url_o"
}
