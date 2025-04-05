package com.ssafy.achu.core

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssafy.achu.data.database.SharedPreferencesUtil
import com.ssafy.achu.data.network.StompService
import com.ssafy.achu.data.repository.AuthRepository
import com.ssafy.achu.data.repository.BabyRepository
import com.ssafy.achu.data.repository.ChatRepository
import com.ssafy.achu.data.repository.MemoryRepository
import com.ssafy.achu.data.repository.ProductRepository
import com.ssafy.achu.data.repository.UserRepository
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
        // ipconfig를 통해 ip확인하기
        // 핸드폰으로 접속은 같은 인터넷으로 연결 되어있어야함 (유,무선)
        const val SERVER_URL = "https://api.a-chu.dukcode.org"
        lateinit var sharedPreferencesUtil: SharedPreferencesUtil
        lateinit var retrofit: Retrofit

        private val nullOnEmptyConverterFactory = object : Converter.Factory() {
            fun converterFactory() = this
            override fun responseBodyConverter(
                type: Type,
                annotations: Array<out Annotation>,
                retrofit: Retrofit
            ) = object :
                Converter<ResponseBody, Any?> {
                val nextResponseBodyConverter =
                    retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)

                override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) {
                    try {
                        nextResponseBodyConverter.convert(value)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }
                } else {
                    null
                }
            }
        }

        // repository 객체
        lateinit var authRepository: AuthRepository
        lateinit var userRepository: UserRepository
        lateinit var productRepository: ProductRepository
        lateinit var memoryRepository: MemoryRepository
        lateinit var babyRepository: BabyRepository
        lateinit var chatRepository: ChatRepository

        lateinit var stompService: StompService
    }


    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: 초기화")
        //shared preference 초기화
        sharedPreferencesUtil = SharedPreferencesUtil(applicationContext)

        // 레트로핏 인스턴스를 생성하고, 레트로핏에 각종 설정값들을 지정해줍니다.
        // 연결 타임아웃시간은 5초로 지정이 되어있고, HttpLoggingInterceptor를 붙여서 어떤 요청이 나가고 들어오는지를 보여줍니다.
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(TokenInterceptor())
            .authenticator(TokenAuthenticator())
            .build()

        // 앱이 처음 생성되는 순간, retrofit 인스턴스를 생성
        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        // repository 초기화
        authRepository = AuthRepository()
        userRepository = UserRepository()
        productRepository = ProductRepository()
        memoryRepository = MemoryRepository()
        babyRepository = BabyRepository()
        chatRepository = ChatRepository()

        stompService = StompService()
        stompService.connect()
    }

    //GSon은 엄격한 json type을 요구하는데, 느슨하게 하기 위한 설정. success, fail이 json이 아니라 단순 문자열로 리턴될 경우 처리..
    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    override fun onTerminate() {
        super.onTerminate()
        stompService.disconnect()
    }

}