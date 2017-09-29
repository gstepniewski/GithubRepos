package com.infunade.githubrepos

data class SearchViewState(val query: String = "",
                           val items: List<Repo>? = null,
                           val loading: Boolean = false,
                           val error: String? = null)