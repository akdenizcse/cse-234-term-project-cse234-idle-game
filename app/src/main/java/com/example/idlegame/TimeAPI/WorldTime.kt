package com.example.idlegame.TimeAPI

import android.util.Log
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WorldTime {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://worldtimeapi.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val worldTimeApi = retrofit.create(TimeApiService::class.java)

    suspend fun getCurrentTime(): TimeApiResponse? {
        return try {
            val timeApiResponse = worldTimeApi.getCurrentTime()
            if (timeApiResponse != null) {
                Log.d("TAG", "Current time: ${timeApiResponse.datetime}")
            }
            timeApiResponse
        } catch (e: Exception) {
            Log.e("TAG", "Error getting current time", e)
            null
        }
    }
}