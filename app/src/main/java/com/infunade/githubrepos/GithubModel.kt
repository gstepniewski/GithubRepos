package com.infunade.githubrepos

data class SearchResponse(val items: List<Repo>)

data class Repo(val name: String, val owner: Owner, val watchers: Int)

data class Owner(val login: String)