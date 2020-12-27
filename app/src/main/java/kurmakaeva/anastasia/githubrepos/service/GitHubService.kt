package kurmakaeva.anastasia.githubrepos.service

import kurmakaeva.anastasia.githubrepos.model.GitHubApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


interface GitHubService {
    @Headers("Accept: application/vnd.github.v3+json") // Recommended header by the GitHub API
    @GET("search/repositories?sort=stars") // Sort loaded repos by stars
    suspend fun searchRepos(
        @Header("Authorization") apiKey: String,
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): GitHubApiResponse

    companion object {
        val instance: GitHubService by lazy {
            retrofit.create(GitHubService::class.java)
        }
    }
}

