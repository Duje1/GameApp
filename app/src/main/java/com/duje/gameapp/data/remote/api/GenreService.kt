package com.duje.gameapp.data.remote.api

import com.duje.gameapp.data.remote.responses.GenresResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreService {
    @GET("genres")
    suspend fun getGenres(@Query("key") apiKey: String): GenresResponse
}