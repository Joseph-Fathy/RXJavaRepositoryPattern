package com.example.rxrepositorytutorial.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rxrepositorytutorial.app.screens.users_list.model.User
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("select * from users where user_id = :identifier limit 1")
    fun getUser(identifier: String): Single<User>

    @Query("select * from users")
    fun getUsers(): Single<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Query("delete from users")
    fun deleteAll()
}