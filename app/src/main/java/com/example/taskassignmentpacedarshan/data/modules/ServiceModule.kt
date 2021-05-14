package com.example.taskassignmentpacedarshan.data.modules

import android.util.Log
import com.example.taskassignmentpacedarshan.di.api.ApiService
import com.example.taskassignmentpacedarshan.di.managers.ApiManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

const val baseURL = "http://ios.greenapex.tech/"

val serviceModule= module {
    single { provideRetrofit(get()) }
    single { provideApiManager(get()) }
    single { provideUnsafeOkHttpClient() }
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseURL)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideApiManager(retrofit: Retrofit): ApiManager {
    return ApiManager(retrofit.create(ApiService::class.java))
}

private fun provideUnsafeOkHttpClient(): OkHttpClient {
    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.getSocketFactory()

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier(object : HostnameVerifier {
            override fun verify(hostname: String, session: SSLSession): Boolean {
                return true
            }
        })
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.e("",""+it)})
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        builder.addInterceptor(interceptor)

        return builder.build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}
