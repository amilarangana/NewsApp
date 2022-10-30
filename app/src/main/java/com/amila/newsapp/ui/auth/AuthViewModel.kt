package com.amila.newsapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amila.newsapp.models.User
import com.amila.newsapp.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository) : ViewModel() {

    fun insertUser(user: User) = viewModelScope.launch {
        userRepository.insertUser(user)
    }

    fun getSavedUser(userName: String, password: String) =
        userRepository.getSavedUser(userName, password)

    fun getUserList(userName: String) =
        userRepository.getUserList(userName)
}