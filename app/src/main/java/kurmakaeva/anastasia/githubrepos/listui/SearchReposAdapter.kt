package kurmakaeva.anastasia.githubrepos.listui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import kurmakaeva.anastasia.githubrepos.R

interface SelectableRepo {
    fun repoSelected(id: Long)
}

class SearchReposAdapter(private val context: Context, private val selectableRepo: SelectableRepo)
    : PagingDataAdapter<SearchRepoViewModel.RepoData, SearchResultViewHolder>(SearchResultViewHolder.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.search_result_viewholder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val repoItem = getItem(position)

        holder.repoName.text = repoItem?.name
        holder.repoDescription.text = repoItem?.description
        holder.repoLanguage.text = repoItem?.language
        holder.repoStarCount.text = repoItem?.stargazers_count.toString()

        holder.itemView.setOnClickListener {
            val repoId = repoItem?.id ?: return@setOnClickListener
            selectableRepo.repoSelected(repoId)
        }
    }
}