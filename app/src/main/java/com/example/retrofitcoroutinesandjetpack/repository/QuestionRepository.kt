package com.example.retrofitcoroutinesandjetpack.repository

import android.util.Log
import com.example.retrofitcoroutinesandjetpack.data.DataOrException
import com.example.retrofitcoroutinesandjetpack.model.QuestionItem
import com.example.retrofitcoroutinesandjetpack.network.QuestionAPI
import java.lang.Exception
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api:QuestionAPI){
    private val dataOrException = DataOrException <ArrayList<QuestionItem>, Boolean, Exception>()


    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception>{
        try{
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if(dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false

        }catch (exception:Exception){
             dataOrException.e = exception
            Log.d("getAllQuestions:", "${dataOrException.e!!.localizedMessage}")
        }
return dataOrException
}
}