package com.duje.gameapp.data.remote.api

import com.duje.gameapp.data.remote.responses.GameInfoResponseModel
import com.duje.gameapp.data.remote.responses.GamesResponseModel
import com.duje.gameapp.data.remote.responses.GenresResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DatabaseService {
    @GET("genres")
    suspend fun getGenres(@Query("key") apiKey: String): GenresResponse

    @GET("games")
    suspend fun getGame(
        @Query("genres") genres: String,
        @Query("key") apiKey: String
    ): GamesResponseModel

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") gameId: String,
        @Query("key") apiKey: String
    ): GameInfoResponseModel

}