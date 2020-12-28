package kurmakaeva.anastasia.githubrepos.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import kurmakaeva.anastasia.githubrepos.R
import kurmakaeva.anastasia.githubrepos.databinding.PageStateItemBinding

class LoadStateAdapter(private val retry: () -> Unit): LoadStateAdapter<kurmakaeva.anastasia.githubrepos.paging.LoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = PageStateItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: PageStateItemBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressCircularPaging.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState !is LoadState.Loading

                progressCircularPaging.isInvisible = loadState !is LoadState.Loading
                retryButton.isInvisible = loadState is LoadState.Loading
            }
        }
    }
}