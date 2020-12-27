package kurmakaeva.anastasia.githubrepos.service

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kurmakaeva.anastasia.githubrepos.model.GitHubApiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

