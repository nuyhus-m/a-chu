package com.ssafy.achu.core

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssafy.achu.data.database.SharedPreferencesUtil
import com.ssafy.achu.data.network.StompService
import com.ssafy.achu.data.repository.AuthRepository
import com.ssafy.achu.data.repository.BabyRepository
import com.ssafy.achu.data.repository.ChatRepository
import com.ssafy.achu.data.repository.FcmRepository
import com.ssafy.achu.data.repository.MemoryRepository
import com.ssafy.achu.data.repository.ProductRepository
import com.ssafy.achu.data.repository.UserRepository
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

private const val TAG = "ApplicationClass"

class ApplicationClass : Application() {

    companion object {
        const val SERVER_URL = "https://api.a-chu.dukcode.org"

        // Retrofit, Gson, SharedPreferences
        lateinit var retrofit: Retrofit
        lateinit var sharedPreferencesUtil: SharedPreferencesUtil
        val gson: Gson = GsonBuilder().setLenient().create()
        lateinit var stompService: StompService

        // Repository
        lateinit var authRepository: AuthRepository
        lateinit var userRepository: UserRepository
        lateinit var productRepository: ProductRepository
        lateinit var memoryRepository: MemoryRepository
        lateinit var babyRepository: BabyRepository
        lateinit var chatRepository: ChatRepository
        lateinit var fcmRepository: FcmRepository
        lateinit var json: Json

        // Null-on-empty converter
        private val nullOnEmptyConverterFactory = object : Converter.Factory() {
            private fun factory() = this
            override fun responseBodyConverter(
                type: Type,
                annotations: Array<out Annotation>,
                retrofit: Retrofit
            ) = Converter<ResponseBody, Any?> { value ->
                if (value.contentLength() != 0L) {
                    try {
                        val delegate =
                            retrofit.nextResponseBodyConverter<Any?>(factory(), type, annotations)
                        delegate.convert(value)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }
                } else null
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ApplicationClass 초기화됨")

        // SharedPreferences 초기화
        sharedPreferencesUtil = SharedPreferencesUtil(applicationContext)

        // 알림 채널 생성 (Oreo 이상)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        // Retrofit 클라이언트 구성
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(TokenInterceptor())
//            .authenticator(TokenAuthenticator())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        // Repository 초기화
        authRepository = AuthRepository()
        userRepository = UserRepository()
        productRepository = ProductRepository()
        memoryRepository = MemoryRepository()
        babyRepository = BabyRepository()
        chatRepository = ChatRepository()
        fcmRepository = FcmRepository()

        stompService = StompService

        json = Json { ignoreUnknownKeys = true }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "알림 채널"
        val descriptionText = "FCM 알림을 위한 채널입니다"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("channel_id", name, importance).apply {
            description = descriptionText
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}
