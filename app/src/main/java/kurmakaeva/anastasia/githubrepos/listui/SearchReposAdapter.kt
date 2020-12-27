package kurmakaeva.anastasia.githubrepos.listui

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.paging.PagingDataAdapter
import kurmakaeva.anastasia.githubrepos.databinding.SearchResultViewholderBinding

interface SelectableRepo {
    fun repoSelected(repoOwner: String, repoName: String)
}

class SearchReposAdapter(private val selectableRepo: SelectableRepo)
    : PagingDataAdapter<SearchRepoViewModel.RepoData, SearchResultViewHolder>(SearchResultViewHolder.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = SearchResultViewholderBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchResultViewHolder(binding)

    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val repoItem = getItem(position)

        if (repoItem != null) {
            holder.bind(repoItem)
        }

        holder.itemView.setOnClickListener {
            val repoName = repoItem?.name ?: return@setOnClickListener
            val repoOwner = repoItem.owner.login
            selectableRepo.repoSelected(repoOwner, repoName)
        }
    }
}