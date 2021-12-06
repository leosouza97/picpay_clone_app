package br.com.picpayclone.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val URL = "http://192.168.31.206:8080/"

object RetrofitService {

    val instance = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    inline fun <reified  T> criarService() = RetrofitService.instance.create(T::class.java)
}