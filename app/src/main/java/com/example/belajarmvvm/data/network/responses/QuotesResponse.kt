package com.example.belajarmvvm.data.network.responses

import com.example.belajarmvvm.data.db.entities.Quote

data class QuotesResponse (
    val status: Int,
    val quotes: List<Quote>
)