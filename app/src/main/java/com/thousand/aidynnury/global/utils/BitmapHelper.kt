package com.example.helperapp.global.utils

import android.graphics.Bitmap
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream

object BitmapHelper {

    fun getFileDataFromBitmap(fieldName: String, bitmap: Bitmap?): MultipartBody.Part? {
        if(bitmap != null){
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)

            val filePart = RequestBodyHelper.getRequestBodyImage(byteArrayOutputStream.toByteArray())

            return RequestBodyHelper.getMultipartData(
                fieldName,
                "${System.currentTimeMillis()}.png",
                filePart
            )
        }
        return null
    }
}