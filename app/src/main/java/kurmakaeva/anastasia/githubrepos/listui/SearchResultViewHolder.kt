package kurmakaeva.anastasia.githubrepos.listui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kurmakaeva.anastasia.githubrepos.R

class SearchResultViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val repoName: TextView = view.findViewById(R.id.repoNameTextView)
    val repoDescription: TextView = view.findViewById(R.id.repoDescriptionTextView)
    val repoLanguage: TextView = view.findViewById(R.id.repoLanguageTextView)
    val repoStarCount: TextView = view.findViewById(R.id.repoStarCountTextView)

    object DiffCallback: DiffUtil.ItemCallback<SearchRepoViewModel.RepoData>() {
        override fun areItemsTheSame(oldItem: SearchRepoViewModel.RepoData, newItem: SearchRepoViewModel.RepoData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchRepoViewModel.RepoData, newItem: SearchRepoViewModel.RepoData): Boolean {
            return oldItem.id == newItem.id
        }
    }
}