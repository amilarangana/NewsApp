package com.amila.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amila.newsapp.models.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun getUserDao() : UserDao

    companion object {
        @Volatile
        private var newsDBInstance: NewsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = newsDBInstance ?: synchronized(LOCK){
            newsDBInstance ?: createDatabase(context).also { newsDBInstance = it  }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                NewsDatabase::class.java,
            "news_db.db").build()
    }

}