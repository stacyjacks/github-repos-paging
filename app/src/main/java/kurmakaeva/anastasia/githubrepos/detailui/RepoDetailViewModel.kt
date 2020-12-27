package kurmakaeva.anastasia.githubrepos.detailui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kurmakaeva.anastasia.githubrepos.App
import kurmakaeva.anastasia.githubrepos.R
import kurmakaeva.anastasia.githubrepos.repo.GitHubSingleProjectRepo
import kurmakaeva.anastasia.githubrepos.service.GitHubServiceDetail

class RepoDetailViewModel(application: Application): AndroidViewModel(application) {

    private val _singleRepoData = MutableLiveData<RepoData>()
    val singleRepoData: LiveData<RepoData>
        get() = _singleRepoData

    private val repo = GitHubSingleProjectRepo(GitHubServiceDetail.instance)
    private val errorMessage: String = App.context?.resources?.getString(R.string.loading_error_message)!!

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

    fun getRepo(owner: String, repoName: String) {
        viewModelScope.launch {
            try {
                _singleRepoData.value = repo.getRepoDetail(owner, repoName)

            } catch (e: Exception) {
                Toast.makeText(App.context, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

}