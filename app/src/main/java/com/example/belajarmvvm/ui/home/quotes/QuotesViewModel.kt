package com.example.belajarmvvm.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.example.belajarmvvm.data.repositories.QuotesRepository
import com.example.belajarmvvm.util.lazyDeferred

class QuotesViewModel(
    private val repository : QuotesRepository
) : ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
    }

}
