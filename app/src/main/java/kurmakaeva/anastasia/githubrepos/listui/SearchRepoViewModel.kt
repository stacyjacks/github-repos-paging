package kurmakaeva.anastasia.githubrepos.listui

import androidx.lifecycle.*
import androidx.paging.*
import kotlinx.coroutines.launch
import kurmakaeva.anastasia.githubrepos.paging.ListPagingSource
import kurmakaeva.anastasia.githubrepos.repo.GitHubRepo
import kurmakaeva.anastasia.githubrepos.service.GitHubService
import java.lang.Exception

class SearchRepoViewModel: ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "Kotlin"
    }

    private fun getRepos(query: String) = Pager(PagingConfig(pageSize = 20, prefetchDistance = 1)) {
        ListPagingSource(query, gitHubRepo = GitHubRepo(gitHubService = GitHubService.instance))
    }.liveData

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val repos = currentQuery.switchMap {
        getRepos(it).cachedIn(viewModelScope)
    }

    fun searchRepos(query: String) {
        viewModelScope.launch {
            try {
                currentQuery.value = query
            } catch (e: Exception) {
                showSnackbarEvent()
            }
        }
    }

    private val _showSnackbar = MutableLiveData<Boolean>()
    val showSnackbar: LiveData<Boolean>
        get() = _showSnackbar

    private fun showSnackbarEvent() {
        _showSnackbar.value = true
    }

    data class RepoOwner(
        val login: String = ""
    )

    data class RepoData(
        val id: Long = 0,
        val name: String = "",
        val description: String = "",
        val language: String = "",
        val stargazers_count: Int = 0,
        val owner: RepoOwner
    )
}