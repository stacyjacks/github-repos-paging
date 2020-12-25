package kurmakaeva.anastasia.githubrepos.paging

import androidx.paging.PagingSource
import kurmakaeva.anastasia.githubrepos.listui.SearchRepoViewModel
import kurmakaeva.anastasia.githubrepos.repo.GitHubRepo
import java.lang.Exception

class ListPagingSource(private val query: String,
                       private val gitHubRepo: GitHubRepo)
    : PagingSource<Int, SearchRepoViewModel.RepoData>() {
    private var page = 1 // GitHub paging starts at 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchRepoViewModel.RepoData> {
        return try {
            page += params.loadSize
            val response = gitHubRepo.searchAllRepos(query, params.loadSize, params.key ?: 1)

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = page
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}