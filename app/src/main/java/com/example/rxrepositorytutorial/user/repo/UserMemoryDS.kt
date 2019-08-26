package com.example.rxrepositorytutorial.user.repo

import android.util.Log
import com.example.rxrepositorytutorial.base_repository.MemoryDSRemove
import com.example.rxrepositorytutorial.user.model.User
import io.reactivex.Observable


class UserMemoryDS private constructor() : MemoryDSRemove<User> {

    var userList: List<User>? = null

    companion object {
        private val instance = UserMemoryDS()

        @Synchronized
        fun getInstance() = instance
    }

    override fun get(identifier: String): Observable<User> {
        Log.wtf("RXRepo", "UserMemoryDS - Getting User with id: $identifier from Memory..... ")

        if (!userList.isNullOrEmpty()) {
            for (user in userList!!) {
                if (user.userId.equals(identifier, true)) {
                    Log.wtf("RXRepo", "UserMemoryDS - Found ${user!!.email} Items In Memory..... ")

                    return Observable.just(user)
                }
            }
        }
        return Observable.empty()
    }

    override fun getAll(): Observable<List<User>> {
        Log.wtf("RXRepo", "UserMemoryDS - Getting data from Memory..... ")

        if (!userList.isNullOrEmpty()) {
            Log.wtf("RXRepo", "UserMemoryDS - Found ${userList!!.size} Items In Memory..... ")
            return Observable.just(userList)
        }
        return Observable.empty()
    }

    override fun save(t: User): Observable<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveAll(t: List<User>): Observable<List<User>> {
        Log.wtf("RXRepo", "UserMemoryDS - Save data to Memory..... ")

        this.userList = t
        return getAll()
    }
}