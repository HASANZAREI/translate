package com.example.translate.network

import com.example.translate.models.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationApi {
    @GET("get")
    suspend fun translate(
        @Query("q") query: String,
        @Query("langpair") langPair: String
    ): Response<ResponseData>
}