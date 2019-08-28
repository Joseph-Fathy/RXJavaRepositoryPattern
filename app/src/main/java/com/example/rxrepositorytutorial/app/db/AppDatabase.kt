package com.example.rxrepositorytutorial.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rxrepositorytutorial.app.screens.users_list.model.User

@Database(entities = [User::class],version = 1 ,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}