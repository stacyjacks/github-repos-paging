package kurmakaeva.anastasia.githubrepos.model

data class GitHubApiResponse(
    val total_count: Int = 0,
    val items: List<Repo> = emptyList(),
    val nextPage: Int? = null
)

data class Repo(
    val id: Long,
    val name: String,
    val description: String?,
    val html_url: String,
    val stargazers_count: Int,
    val forks_count: Int,
    val language: String?,
    val owner: RepoOwner
    )

data class RepoOwner(
    val login: String
)