package com.ssafy.achu.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream


fun getRotatedBitmap(context: Context, uri: Uri): Bitmap? {
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



