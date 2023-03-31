package vn.gotech.audiobook.base.domain.module

import android.content.Context
import vn.gotech.audiobook.base.common.Const
import vn.gotech.audiobook.base.common.UserDataManager
import vn.gotech.audiobook.base.domain.api.ApiService
import vn.gotech.audiobook.base.domain.auth.AppAuthenticator
import vn.gotech.audiobook.base.domain.utils.NetworkHelper
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.schedulers.Schedulers
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {
    single { provideAuthService(androidContext()) }
    single { provideNoAuthService(androidContext()) }
    single { provideNetworkHelper(androidContext()) }
}

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideOkHttpClientNoAuth(context: Context): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
        .cache(provideOkHttpCache(context))
        .addInterceptor(provideHeaderInterceptor())
        .connectTimeout(Const.API.TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(Const.API.TIME_OUT, TimeUnit.SECONDS)

//    if (BuildConfig.DEBUG) {
    okHttpClient.addInterceptor(provideLoggingInterceptor())
//        okHttpClient.eventListenerFactory(LoggingEventListener.Factory())
//    }

    return okHttpClient.build()
}

private fun provideOkHttpClientAuth(context: Context): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
        .cache(provideOkHttpCache(context))
        .authenticator(provideAuthenticator())
        .addInterceptor(provideHeaderInterceptor())
        .addInterceptor(provideAuthHeaderInterceptor())
        .connectTimeout(Const.API.TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(Const.API.TIME_OUT, TimeUnit.SECONDS)

//    if (BuildConfig.DEBUG) {
    okHttpClient.addInterceptor(provideLoggingInterceptor())
//        okHttpClient.eventListenerFactory(LoggingEventListener.Factory())
//    }

    return okHttpClient.build()
}

private fun provideRetrofitNoAuth(
    context: Context,
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .baseUrl(Const.BASE_URL)
        .client(provideOkHttpClientNoAuth(context))
        .build()

private fun provideRetrofitAuth(
    context: Context,
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .baseUrl(Const.BASE_URL)
        .client(provideOkHttpClientAuth(context))
        .build()


fun provideNoAuthService(context: Context): ApiService.NoAuth {
    return provideRetrofitNoAuth(context).create(ApiService.NoAuth::class.java)
}

fun provideAuthService(context: Context): ApiService.Auth {
    val authService = provideRetrofitAuth(context).create(ApiService.Auth::class.java)
    val auth = provideAuthenticator()
    if (auth is AppAuthenticator)
        auth.authService = authService
    return authService
}

fun provideOkHttpCache(context: Context): okhttp3.Cache {
    val size: Long = 10L * 1024 * 1024 // 10Mb
    return okhttp3.Cache(context.cacheDir, size)
}

fun provideHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
        val original = chain.request()

        val request = original.newBuilder()
            .header("Accept", "application/json")
            .method(original.method, original.body)
            .build()

        try {
            return@Interceptor chain.proceed(request)
        } catch (e: Exception) {
            return@Interceptor chain.proceed(original.newBuilder().build())
        }
    }
}

fun provideLoggingInterceptor(): Interceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

fun provideAuthHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
        val original = chain.request()

        val request = original.newBuilder()
            .header("Authorization", "Bearer " + UserDataManager.accessToken)
            .header("Accept", "application/json")
            .method(original.method, original.body)
            .build()

        return@Interceptor chain.proceed(request)
    }
}

fun provideAuthenticator(): Authenticator {
    return AppAuthenticator()
}
