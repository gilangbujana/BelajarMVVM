package com.example.belajarmvvm.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.belajarmvvm.data.db.AppDatabase
import com.example.belajarmvvm.data.db.entities.Quote
import com.example.belajarmvvm.data.network.MyApi
import com.example.belajarmvvm.data.network.SafeApiRequest
import com.example.belajarmvvm.data.preferences.PreferenceProvider
import com.example.belajarmvvm.util.Coroutines
import com.example.belajarmvvm.util.getDiffHours
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.Hours
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*


private val MINIMUM_INTERVAL = 6

class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
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
        val lastSavedAt = prefs.getLastSavedAt()
        if(lastSavedAt == null || isFetchNeeded(Date(lastSavedAt))){
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFetchNeeded(savedAt: Date): Boolean{
       // return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
        return getDiffHours(savedAt, Date()) > MINIMUM_INTERVAL
    }


    private fun saveQuotes(quotes: List<Quote>){
        Coroutines.io {
            prefs.saveLastSavedAt(Date().toString())
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }

}