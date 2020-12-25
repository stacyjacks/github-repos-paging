package kurmakaeva.anastasia.githubrepos.listui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kurmakaeva.anastasia.githubrepos.R
import kurmakaeva.anastasia.githubrepos.databinding.FragmentSearchReposBinding

class SearchReposFragment: Fragment(), SelectableRepo {

    private lateinit var binding: FragmentSearchReposBinding
    private lateinit var adapter: SearchReposAdapter
    private lateinit var viewModel: SearchRepoViewModel

    companion object {
        private const val CURRENT_QUERY: String = "input_search_query"
        private const val DEFAULT_QUERY = "Kotlin"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_repos,
            container,
            false)

        viewModel = ViewModelProvider(this).get(SearchRepoViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner

        val query = savedInstanceState?.getString(CURRENT_QUERY) ?: DEFAULT_QUERY

        adapter = SearchReposAdapter(this.requireContext(), this)
        binding.repoListRv.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            performSearch(query)
            viewModel.getRepos(query).collectLatest {
                adapter.submitData(it)
            }
        }

        val decoration = DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL)
        binding.repoListRv.addItemDecoration(decoration)

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_QUERY, binding.searchRepo.text.trim().toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // checkLoadingState()
    }

    override fun repoSelected(id: Long) {
        TODO("Not yet implemented")
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
                binding.repoListRv.scrollToPosition(0)
                viewModel.getRepos(it.toString())
            }
        }
    }
}