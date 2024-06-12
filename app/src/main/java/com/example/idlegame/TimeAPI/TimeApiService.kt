package com.example.idlegame.TimeAPI

import retrofit2.http.GET

interface TimeApiService {
    @GET("/api/ip")
    suspend fun getCurrentTime(): TimeApiResponse
}