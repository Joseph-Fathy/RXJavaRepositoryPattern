package com.example.rxrepositorytutorial.app.screens.users_list.repo

import com.example.rxrepositorytutorial.base.repository.LocalDS
import com.example.rxrepositorytutorial.app.screens.users_list.model.User
import io.reactivex.Observable


class UserMemoryDS private constructor() : LocalDS<User> {

    var userList: ArrayList<User>? = null

    companion object {
        private val instance = UserMemoryDS()

        @Synchronized
        fun getInstance() = instance
    }

    override fun get(identifier: String): Observable<User> {
        if (!userList.isNullOrEmpty()) {
            for (user in userList!!) {
                if (user.userId.equals(identifier, true)) {
                    return Observable.just(user)
                }
            }
        }
        return Observable.empty()
    }

    override fun getAll(): Observable<List<User>> {
        if (!userList.isNullOrEmpty()) {
            return Observable.just(userList)
        }
        return Observable.empty()
    }

    override fun save(t: User): Observable<User> {
        if (userList.isNullOrEmpty())
            userList = ArrayList()

        if (!userList!!.contains(t))
            userList!!.add(t)

        return Observable.just(t)

    }

    override fun saveAll(t: List<User>): Observable<List<User>> {
        this.userList = t as? ArrayList<User>
        return getAll()
    }
}