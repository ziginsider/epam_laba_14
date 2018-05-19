package io.github.ziginsider.epam_laba14.utils

import android.util.Log
import io.github.ziginsider.epam_laba14.BuildConfig

/**
 * Extension fun for logging info
 */
fun logi(className: String, message: String) {
    if (BuildConfig.DEBUG) Log.i(className, message)
}

/**
 * Extension fun for logging errors
 */
fun loge(className: String, message: String) {
    Log.e(className, message)
}
