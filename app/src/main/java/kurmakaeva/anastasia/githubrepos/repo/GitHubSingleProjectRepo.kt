package kurmakaeva.anastasia.githubrepos.repo

import kurmakaeva.anastasia.githubrepos.App
import kurmakaeva.anastasia.githubrepos.R
import kurmakaeva.anastasia.githubrepos.detailui.RepoDetailViewModel
import kurmakaeva.anastasia.githubrepos.service.GitHubServiceDetail
import kurmakaeva.anastasia.githubrepos.service.api_key

class GitHubSingleProjectRepo(private val gitHubServiceDetail: GitHubServiceDetail) {
    suspend fun getRepoDetail(owner: String, repo: String): RepoDetailViewModel.RepoData {
        val repoDetail = gitHubServiceDetail.getSingleRepo(api_key, owner, repo)
        val repoDetailObject = repoDetail

        val missingLanguage = App.context?.getString(R.string.no_language)
        val missingDescription = App.context?.getString(R.string.no_description)

        return RepoDetailViewModel.RepoData(
            repoDetailObject.id,
            repoDetailObject.name,
            repoDetailObject.description ?: "$missingDescription",
            repoDetailObject.language ?: "$missingLanguage",
            repoDetailObject.html_url,
            repoDetailObject.stargazers_count,
            repoDetailObject.forks_count,
            RepoDetailViewModel.RepoOwner(
                repoDetailObject.owner.login,
                repoDetailObject.owner.avatar_url)
        )
    }
}