package com.example.rxrepositorytutorial.user.repo

import android.util.Log
import com.example.rxrepositorytutorial.App
import com.example.rxrepositorytutorial.user.model.User
import com.example.rxrepositorytutorial.base_repository.RemoteDataSource
import io.reactivex.Observable

class UserRemoteDS : RemoteDataSource<User> {
    var api= App.injectUserApi()

    override fun get(identifier: String): Observable<User> {
        return api.getUser(identifier).doOnSubscribe {
            Log.wtf("RXRepo", "Getting User with id: $identifier from API..... ")
        }
    }

    override fun getAll(): Observable<List<User>> {
        return api.getUsers().doOnSubscribe {
            Log.wtf("RXRepo", "Getting data from API..... ")
        }
    }

}