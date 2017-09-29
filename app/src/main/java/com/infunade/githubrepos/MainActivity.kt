package com.infunade.githubrepos

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: SearchViewModel
    val bindings = CompositeDisposable()
    val repoAdapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        repos.adapter = repoAdapter
        RxTextView.textChanges(query).skipInitialValue().subscribe { viewModel.queryChanged(it.toString()) }
    }

    override fun onStart() {
        super.onStart()
        bindings.add(viewModel.viewState.subscribe(this::render))
    }

    override fun onStop() {
        bindings.clear()
        super.onStop()
    }

    private fun render(viewState: SearchViewState) {
        if (query.text.toString() != viewState.query) {
            query.setText(viewState.query, TextView.BufferType.EDITABLE)
        }

        repoAdapter.updateItems(viewState.items)

        progressBar.visibility = if (viewState.loading) View.VISIBLE else View.GONE
        error.visibility = if (viewState.error != null) View.VISIBLE else View.GONE
        error.text = viewState.error
    }
}
