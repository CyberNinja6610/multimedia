package ru.netology.nmedia.ui.api


import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.ui.model.Album

private const val BASE_URL = BuildConfig.BASE_URL


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("album.json")
    suspend fun loadAlbum(): Response<Album>
}

object Api {
    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}