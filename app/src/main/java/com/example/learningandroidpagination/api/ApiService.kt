package com.example.learningandroidpagination.api

import com.example.learningandroidpagination.model.DoggoImageModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/images/search")
    suspend fun getDoggoImages(@Query("page") page: Int, @Query("limit") size: Int): List<DoggoImageModel>
}