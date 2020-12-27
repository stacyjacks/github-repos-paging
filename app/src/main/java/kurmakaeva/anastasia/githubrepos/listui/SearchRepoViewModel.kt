package kurmakaeva.anastasia.githubrepos.listui

import androidx.lifecycle.*
import androidx.paging.*
import kurmakaeva.anastasia.githubrepos.paging.ListPagingSource
import kurmakaeva.anastasia.githubrepos.repo.GitHubRepo
import kurmakaeva.anastasia.githubrepos.service.GitHubService

class SearchRepoViewModel: ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "Kotlin"
    }

    private fun getRepos(query: String) = Pager(PagingConfig(pageSize = 20)) {
        ListPagingSource(query, gitHubRepo = GitHubRepo(gitHubService = GitHubService.instance))
    }.liveData

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val repos = currentQuery.switchMap {
        getRepos(it).cachedIn(viewModelScope)
    }

    fun searchRepos(query: String) {
        currentQuery.value = query
    }

    data class RepoOwner(
        val login: String = ""
    )

    data class RepoData(
        val id: Long = 0,
        val name: String = "",
        val description: String = "",
        val language: String = "",
        val html_url: String = "",
        val stargazers_count: Int = 0,
        val forks_count: Int = 0,
        val owner: RepoOwner
    )
}