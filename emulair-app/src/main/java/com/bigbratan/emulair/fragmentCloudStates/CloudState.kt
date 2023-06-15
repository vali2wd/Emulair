package com.bigbratan.emulair.fragmentCloudStates

import android.graphics.Bitmap

class CloudState(
    title: String, image: Bitmap?
) {
    var title: String = title
    var image: Bitmap? = image

    //implement equal on title
    override fun equals(other: Any?): Boolean {
        if (other is CloudState) {
            return title == other.title
        }
        return false
    }

}
