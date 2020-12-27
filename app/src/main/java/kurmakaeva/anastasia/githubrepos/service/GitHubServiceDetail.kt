package kurmakaeva.anastasia.githubrepos.service

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kurmakaeva.anastasia.githubrepos.model.GitHubApiResponse
import kurmakaeva.anastasia.githubrepos.model.Repo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

private const val BASE_URL = "https://api.github.com/repos/"

interface GitHubServiceDetail {
    @Headers("Accept: application/vnd.github.v3+json") // Recommended header by the GitHub API
    @GET("{owner}/{repo}")

    suspend fun getSingleRepo(
        @Header("Authorization") apiKey: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Repo

    companion object {
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        fun create(): GitHubServiceDetail {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(GitHubServiceDetail::class.java)
        }
    }
}