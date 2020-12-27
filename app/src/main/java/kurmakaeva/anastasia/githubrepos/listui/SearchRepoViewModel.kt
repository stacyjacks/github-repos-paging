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
    }.flow.cachedIn(viewModelScope)

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

//    private val _singleRepoData = MutableLiveData<Repo>()
//    val singleRepoData: LiveData<Repo>
//        get() = _singleRepoData
//
//    val errorMessage: String = App.context?.resources?.getString(R.string.loading_error_message)!!
//
//    fun getRepo(): Repo {
//        viewModelScope.launch {
//            try {
//                _singleRepoData.value =
//
//            } catch (e: Exception) {
//                Toast.makeText(App.context, errorMessage, Toast.LENGTH_LONG).show()
//            }
//        }
//    }
}