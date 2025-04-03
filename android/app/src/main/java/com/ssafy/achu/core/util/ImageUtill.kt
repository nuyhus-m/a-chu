package com.ssafy.achu.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface

import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
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
    val bitmap = getRotatedBitmap(context, uri) ?: return null
    // 이미지 압축
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)

    return byteArrayOutputStream.toByteArray()
}

fun uriToMultipart(context: Context, uri: Uri, name: String = "memoryImages"): MultipartBody.Part? {
    val contentResolver = context.contentResolver

    // 실제 파일의 MIME 타입 가져오기
    val mimeType = contentResolver.getType(uri) ?: return null

    // MIME 타입에 맞는 확장자 추출
    val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"

    // 기존 `compressImage` 함수 사용
    val byteArray = compressImage(uri, context) ?: return null

    if (byteArray.isEmpty()) {
        Log.e(TAG, "⚠️ 변환된 바이트 배열이 비어있음! 이미지 손실 가능성 있음!")
        return null
    }

    Log.d(TAG, "✅ 변환된 바이트 배열 크기: ${byteArray.size}, MIME: $mimeType, 확장자: $extension")

    // 파일명 설정
    val fileName = "upload_${System.currentTimeMillis()}.$extension"

    // 올바른 MIME 타입 적용
    val requestBody = byteArray.toRequestBody(mimeType.toMediaTypeOrNull())

    return MultipartBody.Part.createFormData(name, fileName, requestBody)
}



