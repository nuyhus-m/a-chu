package com.ssafy.achu.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.util.Log
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.InputStream

private const val TAG = "ImgeUtill"

fun getRotatedBitmap(context: Context, uri: Uri, name: String = "memoryImages"): Bitmap? {
    val contentResolver = context.contentResolver
    val inputStream: InputStream = contentResolver.openInputStream(uri) ?: return null

    val bitmap = BitmapFactory.decodeStream(inputStream)
    inputStream.close()

    // 다시 EXIF 정보를 읽기 위해 InputStream을 새로 연다
    val inputStreamForExif = contentResolver.openInputStream(uri) ?: return bitmap
    val exif = ExifInterface(inputStreamForExif)
    inputStreamForExif.close()

    // EXIF에서 회전 정보 읽기
    val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

    val matrix = Matrix()
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
    }

    return if (matrix.isIdentity) bitmap else Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

fun compressImage(uri: Uri, context: Context): ByteArray? {
    Log.d(TAG, "✅ 이미지 압축 시작")
    val contentResolver = context.contentResolver
    val mimeType = contentResolver.getType(uri) ?: return null

    // 파일 크기 측정 (주의: available()은 신뢰성이 낮음 → statSize가 더 정확함)
    val fileSizeInBytes = contentResolver.openFileDescriptor(uri, "r")?.statSize ?: -1
    val fileSizeInKB = fileSizeInBytes / 1024

    // 압축률 결정 (파일이 클수록 더 압축)
    val quality = when {
        fileSizeInKB < 300 -> 80
        fileSizeInKB < 1000 -> 50
        fileSizeInKB < 3000 -> 30
        fileSizeInKB < 6000 -> 20
        fileSizeInKB < 7000 -> 20
        else -> 10
    }

    // PNG도 모두 JPG로 변환하며, 회전은 JPG일 때만 처리
    val bitmap = if (mimeType == "image/jpeg" || mimeType == "image/jpg") {
        getRotatedBitmap(context, uri)
    } else {
        val stream = contentResolver.openInputStream(uri) ?: return null
        val bmp = BitmapFactory.decodeStream(stream)
        stream.close()
        bmp
    } ?: return null

    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
    val compressedSizeInKB = byteArrayOutputStream.size() / 1024

    Log.d(
        TAG,
        "✅ 압축률 $quality% 적용, 원본 크기 ${fileSizeInKB}KB → 압축 후 ${compressedSizeInKB}KB"
    )

    Log.d(TAG, "✅ 이미지 압축 끝")

    return byteArrayOutputStream.toByteArray()
}


fun uriToMultipart(context: Context, uri: Uri, name: String = "memoryImages"): MultipartBody.Part? {
    val contentResolver = context.contentResolver

    // 👉 이미지 크기 먼저 체크 (100x100 이하 거르기)
    val inputStreamForCheck = contentResolver.openInputStream(uri) ?: return null
    val bitmapForCheck = BitmapFactory.decodeStream(inputStreamForCheck)
    inputStreamForCheck.close()


    // 압축된 이미지 바이트 배열
    val byteArray = compressImage(uri, context) ?: return null

    if (byteArray.isEmpty()) {
        Log.e(TAG, "⚠️ 변환된 바이트 배열이 비어있음! 이미지 손실 가능성 있음!")
        return null
    }

    // 확장자는 항상 JPG로 고정
    val fileName = "upload_${System.currentTimeMillis()}.jpg"
    val requestBody = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())

    Log.d(TAG, "✅ 변환된 바이트 배열 크기: ${byteArray.size}, 확장자: jpg")

    return MultipartBody.Part.createFormData(name, fileName, requestBody)
}


fun isImageValid(context: Context, uri: Uri): Boolean {
    val contentResolver = context.contentResolver

    try {
        val inputStream = contentResolver.openInputStream(uri) ?: return false
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeStream(inputStream, null, options)
        inputStream.close()

        val width = options.outWidth
        val height = options.outHeight

        // 파일 크기 가져오기
        val fileSize = contentResolver.openFileDescriptor(uri, "r")?.statSize ?: return false
        val fileSizeMB = fileSize / (1024 * 1024)  // MB로 변환

        // 로그로 파일 크기 확인
        Log.d("ImageValidation", "파일 크기: $fileSizeMB MB")

        // 크기 조건
        val isValidSize = width > 100 && height > 100
        // 8MB 이하로 제한
        val isValidFileSize = fileSizeMB < 8

        // 조건에 맞지 않으면 Toast 메시지 출력
        if (!isValidSize) {
            Toast.makeText(context, "너무 작은 이미지는 업로드할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }

        if (!isValidFileSize) {
            Toast.makeText(context, "8MB를 초과하는 이미지는 업로드할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }

        // 이미지가 유효한 경우
        return isValidSize && isValidFileSize
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}
