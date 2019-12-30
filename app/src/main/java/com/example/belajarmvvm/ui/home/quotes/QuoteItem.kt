package com.example.belajarmvvm.ui.home.quotes

import com.example.belajarmvvm.R
import com.example.belajarmvvm.data.db.entities.Quote
import com.example.belajarmvvm.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(
    private val quote: Quote
): BindableItem<ItemQuoteBinding>(){
    override fun getLayout(): Int = R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)

    }

}