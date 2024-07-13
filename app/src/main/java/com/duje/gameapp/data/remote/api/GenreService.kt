package com.duje.gameapp.data.remote.api

import com.duje.gameapp.data.remote.responses.GenresResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GenreService {
    @GET("/genres?key={api}")
    suspend fun getGenres(@Path("api") apiKey: String): GenresResponse
}