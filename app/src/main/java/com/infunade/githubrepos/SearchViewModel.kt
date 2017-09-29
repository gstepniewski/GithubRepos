package com.infunade.githubrepos

import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel: ViewModel() {

    val viewState = BehaviorSubject.createDefault(SearchViewState())

    val queryText = PublishSubject.create<String>()

    @Inject lateinit var githubApi: GithubApi

    init {
        GithubApp.githubComponent.inject(this)

        queryText.filter { it.length >= 3 }
                 .debounce(500, TimeUnit.MILLISECONDS)
                 .switchMap { search(it) }
                 .subscribe { viewState.onNext(it) }
    }

    private fun search(query: String): Observable<SearchViewState> {
        return githubApi.search(query)
                        .map { SearchViewState(query, it.items) }
                        .onErrorReturn { SearchViewState(query, error = it.message) }
                        .startWith(SearchViewState(query, loading = true))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
    }

    fun queryChanged(query: String) {
        viewState.onNext(viewState.value.copy(query = query))
        queryText.onNext(query)
    }
}