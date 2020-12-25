package kurmakaeva.anastasia.githubrepos.repo

import kurmakaeva.anastasia.githubrepos.App
import kurmakaeva.anastasia.githubrepos.R
import kurmakaeva.anastasia.githubrepos.listui.SearchRepoViewModel
import kurmakaeva.anastasia.githubrepos.service.GitHubService
import kurmakaeva.anastasia.githubrepos.service.api_key

class GitHubRepo(private val gitHubService: GitHubService) {

    suspend fun searchAllRepos(query: String, loadSize: Int, key: Int): List<SearchRepoViewModel.RepoData> {
        val response = gitHubService.searchRepos(api_key, query, key, loadSize)
        val listOfRepos = response.items

        val missingLanguage = App.context?.getString(R.string.no_description)
        val missingDescription = App.context?.getString(R.string.no_language)

        return listOfRepos.map {
            SearchRepoViewModel.RepoData(
                id = it.id,
                name = it.name,
                description = it.description ?: "$missingDescription",
                language = it.language ?: "$missingLanguage",
                html_url = it.html_url,
                stargazers_count = it.stargazers_count
            )
        }
    }
}