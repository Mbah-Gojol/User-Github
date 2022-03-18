package com.mbahgojol.exprojectgithub.data.remote

import com.mbahgojol.exprojectgithub.data.model.ResponseDetailUser
import com.mbahgojol.exprojectgithub.data.model.ResponseUserGithub
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUserGithub(): MutableList<ResponseUserGithub.Item>

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUserGithub(@Path("username") username: String): ResponseDetailUser
}