package kurmakaeva.anastasia.githubrepos.listui

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kurmakaeva.anastasia.githubrepos.databinding.SearchResultViewholderBinding

class SearchResultViewHolder(private val binding: SearchResultViewholderBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(searchResultItem: SearchRepoViewModel.RepoData) {
        binding.apply {
            repoNameTextView.text = searchResultItem.name
            repoDescriptionTextView.text = searchResultItem.description
            repoLanguageTextView.text = searchResultItem.language
            repoStarCountTextView.text = searchResultItem.stargazers_count.toString()
        }
    }

    object DiffCallback: DiffUtil.ItemCallback<SearchRepoViewModel.RepoData>() {
        override fun areItemsTheSame(oldItem: SearchRepoViewModel.RepoData, newItem: SearchRepoViewModel.RepoData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchRepoViewModel.RepoData, newItem: SearchRepoViewModel.RepoData): Boolean {
            return oldItem.id == newItem.id
        }
    }
}