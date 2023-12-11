package app.junsu.onui_android

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import app.junsu.onui.R
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

/**
 * this is a enum class about type of mood
 */
enum class Mood { WORST, BAD, NOT_BAD, FINE, GOOD, }

val sproutImages = listOf(
    R.drawable.sprout_good,
    R.drawable.sprout_fine,
    R.drawable.sprout_normal,
    R.drawable.sprout_green,
    R.drawable.sprout_bad,
)
val flushingImages = listOf(
    R.drawable.flushing_good,
    R.drawable.flushing_fine,
    R.drawable.flushing_normal,
    R.drawable.flushing_bad,
    R.drawable.flushing_very_bad,
)

val catImages = listOf(
    R.drawable.cat_good,
    R.drawable.cat_fine,
    R.drawable.cat_normal,
    R.drawable.cat_bad,
    R.drawable.cat_very_bad,
)

val defaultImage = listOf(
    R.drawable.very_good_small,
    R.drawable.good_small,
    R.drawable.normal_small,
    R.drawable.bad_samll,
    R.drawable.very_bad_small,
)


val bigImageList = listOf(
    listOf(
        R.drawable.very_bad_big,
        R.drawable.bad_big,
        R.drawable.normal_big,
        R.drawable.good_big,
        R.drawable.very_good_big,
    ),
    listOf(
        R.drawable.cat_very_bad,
        R.drawable.cat_bad,
        R.drawable.cat_normal,
        R.drawable.cat_fine,
        R.drawable.cat_good,
    ),
    listOf(
        R.drawable.flushing_very_bad,
        R.drawable.flushing_bad,
        R.drawable.flushing_normal,
        R.drawable.flushing_fine,
        R.drawable.flushing_good,
    ),
    listOf(
        R.drawable.sprout_bad,
        R.drawable.sprout_green,
        R.drawable.sprout_normal,
        R.drawable.sprout_fine,
        R.drawable.sprout_good,
    )
)

val smallImageList = listOf(
    listOf(
        R.drawable.very_bad_small,
        R.drawable.bad_samll,
        R.drawable.normal_small,
        R.drawable.good_small,
        R.drawable.very_good_small,
    ),
    listOf(
        R.drawable.cat_very_bad,
        R.drawable.cat_bad,
        R.drawable.cat_normal,
        R.drawable.cat_fine,
        R.drawable.cat_good,
    ),
    listOf(
        R.drawable.flushing_very_bad,
        R.drawable.flushing_bad,
        R.drawable.flushing_normal,
        R.drawable.flushing_fine,
        R.drawable.flushing_good,
    ),
    listOf(
        R.drawable.sprout_bad,
        R.drawable.sprout_green,
        R.drawable.sprout_normal,
        R.drawable.sprout_fine,
        R.drawable.sprout_good,
    )
)

val grayImageList = listOf(
    listOf(
        R.drawable.very_bad_gray,
        R.drawable.bad_gray,
        R.drawable.normal_gray,
        R.drawable.good_gray,
        R.drawable.very_good_gray,
    ),
    listOf(
        R.drawable.nya_verybad_gray,
        R.drawable.nya_bad_gray,
        R.drawable.nya_normal_gray,
        R.drawable.nya_bad_gray,
        R.drawable.nya_verygood_gray,
    ),
    listOf(
        R.drawable.hong_verybad_gray,
        R.drawable.hong_bad_gray,
        R.drawable.hong_normal_gray,
        R.drawable.hong_good_gray,
        R.drawable.hong_verygood_gray,
    ),
    listOf(
        R.drawable.ssac_verybad_gray,
        R.drawable.ssac_bad_gray,
        R.drawable.ssac_normal_gray,
        R.drawable.ssac_good_gray,
        R.drawable.ssac_verygood_gray,
    )
)

/**
 * this is a function that change theme to int
 *
 * @return the theme of int
 */
fun String.toInt(): Int = when (this) {
    "default" -> 0
    "홍조쓰" -> 2
    "애옹쓰" -> 1
    "새싹쓰" -> 3
    else -> 0
}

var imageState = 0
fun imageTheme(state: Int) {
    imageState = state
}

fun Mood.toBigImage() =
    when (this) {
        Mood.WORST -> bigImageList[imageState][0]
        Mood.BAD -> bigImageList[imageState][1]
        Mood.NOT_BAD -> bigImageList[imageState][2]
        Mood.FINE -> bigImageList[imageState][3]
        else -> bigImageList[imageState][4]
    }

fun Mood.toGrayImage() =
    when (this) {
        Mood.WORST -> grayImageList[imageState][0]
        Mood.BAD -> grayImageList[imageState][1]
        Mood.NOT_BAD -> grayImageList[imageState][2]
        Mood.FINE -> grayImageList[imageState][3]
        else -> grayImageList[imageState][4]
    }

fun Mood.toSmallImage() =
    when (this) {
        Mood.WORST -> smallImageList[imageState][0]
        Mood.BAD -> smallImageList[imageState][1]
        Mood.NOT_BAD -> smallImageList[imageState][2]
        Mood.FINE -> smallImageList[imageState][3]
        else -> smallImageList[imageState][4]
    }

/**
 * this is a function that image change file
 * @param context
 * @param uri this is image uri that taken from gallery
 * @return get file name & extension
 */
fun toFile(context: Context, uri: Uri): File {
    val fileName = getFileName(context, uri)

    val file = createTempFile(context, fileName)
    copyToFile(context, uri, file)

    return File(file.absolutePath)
}

private fun getFileName(context: Context, uri: Uri): String {
    val name = uri.toString().split("/").last()
    val ext = context.contentResolver.getType(uri)!!.split("/").last()

    return "$name.$ext"
}

private fun createTempFile(context: Context, fileName: String): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File(storageDir, fileName)
}

@SuppressLint("Recycle")
private fun copyToFile(context: Context, uri: Uri, file: File) {
    val inputStream = context.contentResolver.openInputStream(uri)
    val outputStream = FileOutputStream(file)

    val buffer = ByteArray(4 * 1024)
    while (true) {
        val byteCount = inputStream!!.read(buffer)
        if (byteCount < 0) break
        outputStream.write(buffer, 0, byteCount)
    }

    outputStream.flush()
}

/**
 * this function change application/json to multipart/form-data
 * @param key file name
 * @param file application/json file
 * @return file that request to send to server
 */
fun getImageMultipart(key: String, file: File): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        name = key,
        filename = file.name,
        body = file.asRequestBody("multipart/form-data".toMediaType())
    )
}