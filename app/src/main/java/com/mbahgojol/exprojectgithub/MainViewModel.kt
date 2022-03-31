package com.mbahgojol.exprojectgithub

import androidx.lifecycle.*
import com.mbahgojol.exprojectgithub.data.local.SettingPreferences
import com.mbahgojol.exprojectgithub.data.remote.ApiClient
import com.mbahgojol.exprojectgithub.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val preferences: SettingPreferences) : ViewModel() {

    val resultUser = MutableLiveData<Result>()

    fun getTheme() = preferences.getThemeSetting().asLiveData()

    fun getUser() {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .getUserGithub()

                emit(response)
            }.onStart {
                resultUser.value = Result.Loading(true)
            }.onCompletion {
                resultUser.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultUser.value = Result.Error(it)
            }.collect {
                resultUser.value = Result.Success(it)
            }
        }
    }

    fun getUser(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .searchUserGithub(
                        mapOf(
                            "q" to username,
                            "per_page" to 10
                        )
                    )

                emit(response)
            }.onStart {
                resultUser.value = Result.Loading(true)
            }.onCompletion {
                resultUser.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultUser.value = Result.Error(it)
            }.collect {
                resultUser.value = Result.Success(it.items)
            }
        }
    }

    class Factory(private val preferences: SettingPreferences) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
    }
}