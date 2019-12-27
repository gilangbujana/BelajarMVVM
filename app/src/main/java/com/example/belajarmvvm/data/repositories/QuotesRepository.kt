package com.example.belajarmvvm.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.belajarmvvm.data.db.AppDatabase
import com.example.belajarmvvm.data.db.entities.Quote
import com.example.belajarmvvm.data.network.MyApi
import com.example.belajarmvvm.data.network.SafeApiRequest
import com.example.belajarmvvm.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    suspend fun getQuotes() : LiveData<List<Quote>> {
        return withContext(Dispatchers.IO){
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes(){
        if(isFetchNeeded()){
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFetchNeeded() : Boolean{
        return true;
    }


    private fun saveQuotes(quotes: List<Quote>){
        Coroutines.io {
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }

}