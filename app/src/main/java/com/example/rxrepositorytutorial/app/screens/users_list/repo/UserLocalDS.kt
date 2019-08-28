package com.example.rxrepositorytutorial.app.screens.users_list.repo

import com.example.rxrepositorytutorial.app.App
import com.example.rxrepositorytutorial.app.screens.users_list.model.User
import com.example.rxrepositorytutorial.base.repository.LocalDS
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observable.just

class UserLocalDS : LocalDS<User> {
    var dao = App.injectUserDao()

    override fun get(identifier: String): Observable<User> {
        return dao.getUser(identifier)
            .toObservable()
    }


    override fun getAll(): Observable<List<User>> {
        return dao.getUsers()
            .filter {
                it.isNotEmpty()
            }
            .toObservable()
    }

    override fun save(t: User): Observable<User> {
        return Completable.fromCallable { dao.insert(t) }
            .andThen(just(t))
    }

    override fun saveAll(t: List<User>): Observable<List<User>> {
        return Completable.fromCallable { dao.insertAll(t) }
            .andThen(just(t))
    }

}
