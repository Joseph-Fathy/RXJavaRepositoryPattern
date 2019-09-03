package com.example.rxrepositorytutorial.app.screens.users_list.repo

import com.example.rxrepositorytutorial.app.App
import com.example.rxrepositorytutorial.app.screens.users_list.model.User
import io.reactivex.Observable
import com.example.rxrepositorytutorial.base.repository.DataSource

class UserRemoteDS : DataSource<User>() {
    var api = App.injectUserApi()

    override fun get(identifier: String): Observable<User> {
        return api.getUser(identifier)

    }

    override fun getAll(): Observable<List<User>> {
        return api.getUsers()
    }

}