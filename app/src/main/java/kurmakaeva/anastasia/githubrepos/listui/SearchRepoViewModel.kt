package kurmakaeva.anastasia.githubrepos.listui

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kurmakaeva.anastasia.githubrepos.paging.ListPagingSource
import kurmakaeva.anastasia.githubrepos.repo.GitHubRepo
import kurmakaeva.anastasia.githubrepos.service.GitHubService

class SearchRepoViewModel: ViewModel() {

    fun getRepos(query: String): Flow<PagingData<RepoData>> = Pager(PagingConfig(pageSize = 50)) {
        ListPagingSource(query, gitHubRepo = GitHubRepo(gitHubService = GitHubService.create()))
    }.flow
        .cachedIn(viewModelScope)

    data class RepoData(
        val id: Long = 0,
        val name: String = "",
        val description: String = "",
        val language: String = "",
        val html_url: String = "",
        val stargazers_count: Int = 0
    )
}