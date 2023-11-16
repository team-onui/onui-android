package app.junsu.onui_android

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import app.junsu.onui.R
import app.junsu.onui_android.exception.BadRequestException
import app.junsu.onui_android.exception.ConflictException
import app.junsu.onui_android.exception.ForbiddenException
import app.junsu.onui_android.exception.NeedLoginException
import app.junsu.onui_android.exception.NoInternetException
import app.junsu.onui_android.exception.NotFoundException
import app.junsu.onui_android.exception.ServerException
import app.junsu.onui_android.exception.TimeoutException
import app.junsu.onui_android.exception.UnauthorizedException
import app.junsu.onui_android.exception.UnknownException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

enum class Mood { WORST, BAD, NOT_BAD, FINE, GOOD, }

fun Mood.toSmallImage() =
    when (this) {
        Mood.WORST -> R.drawable.very_bad_small
        Mood.BAD -> R.drawable.bad_samll
        Mood.NOT_BAD -> R.drawable.normal_small
        Mood.FINE -> R.drawable.good_small
        else -> R.drawable.very_good_small
    }

fun Mood.toBigImage() =
    when (this) {
        Mood.WORST -> R.drawable.very_bad_big
        Mood.BAD -> R.drawable.bad_big
        Mood.NOT_BAD -> R.drawable.normal_big
        Mood.FINE -> R.drawable.good_big
        else -> R.drawable.very_good_big
    }

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

fun getImageMultipart(key: String, file: File): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        name = key,
        filename = file.name,
        body = file.asRequestBody("multipart/form-data".toMediaType())
    )
}