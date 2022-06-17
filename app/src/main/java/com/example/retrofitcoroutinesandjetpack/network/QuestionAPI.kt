package com.example.retrofitcoroutinesandjetpack.network

import com.example.retrofitcoroutinesandjetpack.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionAPI {
    @GET("world.json")
    suspend fun getAllQuestions(): Question
}