package com.project.erphealthcare.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private val httpClient = OkHttpClient.Builder()
    var token: String = ""

    private fun initRetrofit(): Retrofit {

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer $token").build()
            chain.proceed(request)
        }
        val client = httpClient.build()

        return Retrofit.Builder()
            .baseUrl("https://api-erp-tcc.azurewebsites.net")
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(client)
            .build()
    }

    val service: ErpServices = initRetrofit().create(ErpServices::class.java)
}