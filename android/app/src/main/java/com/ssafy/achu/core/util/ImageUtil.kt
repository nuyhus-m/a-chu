package com.ssafy.achu.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

private const val TAG = "ImageUtil"

fun uriToMultipart(context: Context, uri: Uri, name: String = "memoryImages"): MultipartBody.Part? {
    val contentResolver = context.contentResolver

    // 실제 파일의 MIME 타입 가져오기
    val mimeType = contentResolver.getType(uri) ?: return null

    // MIME 타입에 맞는 확장자 추출
    val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"

    // InputStream → Bitmap 변환
    val inputStream = contentResolver.openInputStream(uri) ?: return null
    val bitmap = BitmapFactory.decodeStream(inputStream) ?: return null
    inputStream.close()

    // 압축을 위한 ByteArrayOutputStream 생성
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)
    val compressedByteArray = byteArrayOutputStream.toByteArray()

    if (compressedByteArray.isEmpty()) {
        Log.e(TAG, "⚠️ 압축 후 바이트 배열이 비어있음! 이미지 손실 가능성 있음!")
        return null
    }

    Log.d(TAG, "✅ 압축된 바이트 배열 크기: ${compressedByteArray.size}, MIME: $mimeType, 확장자: $extension")

    // 파일명을 MIME 타입에 맞게 설정
    val fileName = "upload_${System.currentTimeMillis()}.$extension"

    // 올바른 MIME 타입 적용
    val requestBody = compressedByteArray.toRequestBody(mimeType.toMediaTypeOrNull())

    return MultipartBody.Part.createFormData(name, fileName, requestBody)
}