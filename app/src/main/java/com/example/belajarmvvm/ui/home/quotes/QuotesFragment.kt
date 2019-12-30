package com.example.belajarmvvm.ui.home.quotes

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.belajarmvvm.R
import com.example.belajarmvvm.data.db.entities.Quote
import com.example.belajarmvvm.util.Coroutines
import com.example.belajarmvvm.util.hide
import com.example.belajarmvvm.util.show

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.quotes_fragment.*
import org.kodein.di.android.x.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance


class QuotesFragment : Fragment(), KodeinAware {
    override val kodein by kodein()


    private lateinit var viewModel: QuotesViewModel
    val factory : QuotesViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(QuotesViewModel::class.java)
        bindUI()

        /*(Coroutines.main {
            val quotes = viewModel.quotes.await()
            quotes.observe(this, Observer {
                context?.toast(it.size.toString())
            })
        }*/
    }

    private fun bindUI() = Coroutines.main {
        pb_rv_quotes.show()
        viewModel.quotes.await().observe(this, Observer {
            pb_rv_quotes.hide()
            initRecyclerView(it.toQuoteItem())
        })
    }

    private fun initRecyclerView(quoteItem: List<QuoteItem>) {
        val mAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(quoteItem)
        }
        rv_quotes.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun List<Quote>.toQuoteItem() : List<QuoteItem>{
        return this.map {
            QuoteItem(it)
        }
    }


}
