package com.example.rxrepositorytutorial.repo

import android.util.Log
import com.example.rxrepositorytutorial.api.UserApi
import com.example.rxrepositorytutorial.db.UserDao
import com.example.rxrepositorytutorial.model.User
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class UserRepository(var userApi: UserApi, var userDao: UserDao) {


    private fun usersFromApi(): Observable<List<User>> {
        var disposable:Disposable?=null
        return userApi.getUsers()
            .doOnSubscribe {
                Log.wtf("RXRepo", "Getting data from API..... ")
            }
            .doOnNext {
                Log.wtf("RXRepo", "Number Loaded Users from server = ${it.size}")
                disposable=saveUsersInDb(it)
            }
            .doOnComplete {
                disposable?.dispose()
            }
    }

    private fun usersFromDb(): Observable<List<User>> {
        return userDao.getUsers().delay(5,TimeUnit.SECONDS)
            .doOnSubscribe {
                Log.wtf("RXRepo", "Getting data from Database..... ")
            }
            .filter {
                it.isNotEmpty()
            }
            .toObservable()
            .doOnNext {
                Log.wtf("RXRepo", "Number of Users from DB = ${it.size}")
            }
    }


    private fun saveUsersInDb(users: List<User>): Disposable {
        return Observable.fromCallable { userDao.insertAll(users) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                Log.wtf("RXRepo", "Saving Number of Users TO DB = ${users.size}")
            }, {
                Log.wtf("RXRepo", "Error in Saving Number of Users TO DB  ${it.message}")
            })

    }

    fun getUsers(): Observable<List<User>> {
        return Observable.concatArray(usersFromDb(), usersFromApi())
            .doOnSubscribe {
                Log.wtf("RXRepo", "Getting data from Repo..... ")
            }
    }
}