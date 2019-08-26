package com.example.rxrepositorytutorial.user.repo

import android.util.Log
import com.example.rxrepositorytutorial.App
import com.example.rxrepositorytutorial.user.model.User
import com.example.rxrepositorytutorial.base_repository.LocalDSRemove
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observable.just

class UserLocalDS : LocalDSRemove<User> {
    var dao = App.injectUserDao()

    override fun get(identifier: String): Observable<User> {
        return dao.getUser(identifier)
            .toObservable()
            .doOnSubscribe {
                Log.wtf("RXRepo", "Getting User with id: $identifier from DATABASE..... ")
            }
            .doOnNext{
                Log.wtf("RXRepo", "Found User with id: $identifier and email: ${it.email} IN DATABASE..... ")
            }
    }


    override fun getAll(): Observable<List<User>> {
        return dao.getUsers()
            .filter {
                it.isNotEmpty()
            }
            .toObservable()
            .doOnSubscribe {
                Log.wtf("RXRepo", "Getting data from DATABASE..... ")
            }
    }

    override fun save(t: User): Observable<User> {
        return Completable.fromCallable { dao.insert(t) }
            .andThen(just(t))
            .doOnSubscribe {
                Log.wtf("RXRepo", "Saving User with id: ${t.userId} To DATABASE..... ")
            }
    }

    override fun saveAll(t: List<User>): Observable<List<User>> {
        return Completable.fromCallable { dao.insertAll(t) }
            .andThen(just(t))
            .doOnSubscribe {
                Log.wtf("RXRepo", "Saving data To DATABASE..... ")
            }
    }

}
