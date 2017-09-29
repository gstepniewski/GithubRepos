package com.infunade.githubrepos

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_repo.view.*

class RepoAdapter: RecyclerView.Adapter<RepoViewHolder>() {

    val items = mutableListOf<Repo>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RepoViewHolder {
        return RepoViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_repo, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RepoViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    fun updateItems(newItems: List<Repo>?) {
        items.clear();
        newItems?.let { items.addAll(it) }
        notifyDataSetChanged()
    }
}

class RepoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(repo: Repo) {
        itemView.name.text = repo.name
        itemView.owner.text = repo.owner.login
        itemView.watchers.text = repo.watchers.toString()
    }
}