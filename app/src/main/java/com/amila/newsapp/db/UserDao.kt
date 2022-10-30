package com.amila.newsapp.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amila.newsapp.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE userName = :userName and password = :password")
    fun getUser(userName: String, password: String): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE userName = :userName")
    fun getUserList(userName: String) : LiveData<List<User>>
}