package com.example.rxrepositorytutorial.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rxrepositorytutorial.user.model.User

@Database(entities = [User::class],version = 1 ,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao():UserDao
}