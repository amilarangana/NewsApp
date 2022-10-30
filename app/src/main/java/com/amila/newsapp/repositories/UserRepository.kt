package com.amila.newsapp.repositories

import com.amila.newsapp.db.NewsDatabase
import com.amila.newsapp.models.User
import javax.inject.Inject

class UserRepository @Inject constructor(val db: NewsDatabase) {

    suspend fun insertUser(user: User) =
        db.getUserDao().insertUser(user)

    fun getSavedUser(userName: String, password: String) =
        db.getUserDao().getUser(userName, password)

    fun getUserList(userName: String) =
        db.getUserDao().getUserList(userName)
}