package kurmakaeva.anastasia.githubrepos.service

import kurmakaeva.anastasia.githubrepos.model.Repo
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface GitHubServiceDetail {
    @Headers("Accept: application/vnd.github.v3+json") // Recommended header by the GitHub API
    @GET("repos/{owner}/{repo}")

    suspend fun getSingleRepo(
        @Header("Authorization") apiKey: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Repo

    companion object {
        val instance: GitHubServiceDetail by lazy {
            retrofit.create(GitHubServiceDetail::class.java)
        }
    }
}