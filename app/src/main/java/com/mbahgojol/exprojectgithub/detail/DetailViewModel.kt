package com.mbahgojol.exprojectgithub.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mbahgojol.exprojectgithub.data.local.DbModule
import com.mbahgojol.exprojectgithub.data.model.ResponseUserGithub
import com.mbahgojol.exprojectgithub.data.remote.ApiClient
import com.mbahgojol.exprojectgithub.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(private val db: DbModule) : ViewModel() {
    val resultDetailUser = MutableLiveData<Result>()
    val resultFollowersUser = MutableLiveData<Result>()
    val resultFollowingUser = MutableLiveData<Result>()
    val resultSuksesFavorite = MutableLiveData<Boolean>()
    val resultDeleteFavorite = MutableLiveData<Boolean>()

    private var isFavorite = false

    fun setFavorite(item: ResponseUserGithub.Item?) {
        viewModelScope.launch {
            item?.let {
                if (isFavorite) {
                    db.userDao.delete(item)
                    resultDeleteFavorite.value = true
                } else {
                    db.userDao.insert(item)
                    resultSuksesFavorite.value = true
                }
            }
            isFavorite = !isFavorite
        }
    }

    fun findFavorite(id: Int, listenFavorite: () -> Unit) {
        viewModelScope.launch {
            val user = db.userDao.findById(id)
            if (user != null) {
                listenFavorite()
                isFavorite = true
            }
        }
    }

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .getDetailUserGithub(username)

                emit(response)
            }.onStart {
                resultDetailUser.value = Result.Loading(true)
            }.onCompletion {
                resultDetailUser.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultDetailUser.value = Result.Error(it)
            }.collect {
                resultDetailUser.value = Result.Success(it)
            }
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .getFollowersUserGithub(username)

                emit(response)
            }.onStart {
                resultFollowersUser.value = Result.Loading(true)
            }.onCompletion {
                resultFollowersUser.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultFollowersUser.value = Result.Error(it)
            }.collect {
                resultFollowersUser.value = Result.Success(it)
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .getFollowingUserGithub(username)

                emit(response)
            }.onStart {
                resultFollowingUser.value = Result.Loading(true)
            }.onCompletion {
                resultFollowingUser.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultFollowingUser.value = Result.Error(it)
            }.collect {
                resultFollowingUser.value = Result.Success(it)
            }
        }
    }

    class Factory(private val db: DbModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(db) as T
    }
}