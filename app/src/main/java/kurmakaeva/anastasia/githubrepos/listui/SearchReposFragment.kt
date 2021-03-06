package kurmakaeva.anastasia.githubrepos.listui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import kurmakaeva.anastasia.githubrepos.R
import kurmakaeva.anastasia.githubrepos.databinding.FragmentSearchReposBinding
import kurmakaeva.anastasia.githubrepos.hideKeyboard
import kurmakaeva.anastasia.githubrepos.paging.LoadStateAdapter

class SearchReposFragment : Fragment(), SelectableRepo {

    private lateinit var binding: FragmentSearchReposBinding
    private lateinit var adapter: SearchReposAdapter
    private lateinit var viewModel: SearchRepoViewModel

    companion object {
        private const val CURRENT_QUERY: String = "input_search_query"
        private const val DEFAULT_QUERY = "Kotlin"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_search_repos, container, false)

        viewModel = ViewModelProvider(this).get(SearchRepoViewModel::class.java)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = savedInstanceState?.getString(CURRENT_QUERY) ?: DEFAULT_QUERY

        adapter = SearchReposAdapter(this)
        binding.repoListRv.adapter = adapter.withLoadStateFooter(footer = LoadStateAdapter {
            adapter.retry()
        })

        viewModel.repos.observe(viewLifecycleOwner, Observer {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        performSearch(query)

        checkLoadingState()

        val decoration =
            DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL)
        binding.repoListRv.addItemDecoration(decoration)
    }

    override fun repoSelected(repoOwner: String, repoName: String) {
        val action = SearchReposFragmentDirections.actionGoToRepoDetailFragment(repoOwner, repoName)
        this.findNavController().navigate(action)
    }

    private fun performSearch(query: String) {
        binding.searchRepo.setText(query)

        binding.searchRepo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInputQuery()
                true
            } else {
                false
            }
        }

        binding.searchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard(requireActivity())
                updateRepoListFromInputQuery()
                true
            } else {
                false
            }
        }
    }

    private fun updateRepoListFromInputQuery() {
        binding.searchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                viewModel.searchRepos(it.toString())
                adapter.refresh()
                binding.repoListRv.smoothScrollToPosition(0)
            }
        }
    }

    private fun showSnackbarOnError() {
        Snackbar
            .make(requireActivity().findViewById(android.R.id.content),
                getString(R.string.loading_error_message),
                Snackbar.LENGTH_LONG)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            .show()
    }

    private fun checkLoadingState() {
        adapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                binding.progressCircular.visibility = View.VISIBLE
            } else {
                binding.progressCircular.visibility = View.GONE
            }
            val errorState = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                else -> null
            }
            errorState?.let {
                showSnackbarOnError()
            }
        }
    }
}