package com.amila.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users"
)
data class User(

    @PrimaryKey
    var userName: String,
    var firstName: String,
    var lastName: String,
    var password: String
)
