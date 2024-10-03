package otus.homework.customview.utils

import java.io.IOException
import java.io.InputStream


fun InputStream.inputStreamToString(): String? {
    return try {
        val bytes = ByteArray(available())
        read(bytes, 0, bytes.size)
        String(bytes)
    } catch (e: IOException) {
        null
    }
}