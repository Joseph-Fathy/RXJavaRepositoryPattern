package com.example.rxrepositorytutorial.user.repo

import com.example.rxrepositorytutorial.base_repository.BaseRepository
import com.example.rxrepositorytutorial.base_repository.DefaultCachingPolicy
import com.example.rxrepositorytutorial.user.model.User
import io.reactivex.Observable

class UserRepository(var remoteDS: UserRemoteDS, var localDS: UserLocalDS, var memoryDS: UserMemoryDS , cachingPolicy: DefaultCachingPolicy)
    : BaseRepository<User>(remoteDS, localDS , memoryDS,cachingPolicy) {

    /*fun _getUsers(): Observable<List<User>> {
        return Observable.concat(memoryDS.getAll(), getAll()).first(emptyList()).toObservable()
            .doOnNext { memoryDS.saveAll(it) }
    }

    fun getUserWithId(identifier: String): Observable<User> = Observable.concat(memoryDS.get(identifier),get(identifier)).first(User()).toObservable()

    */


    fun getUsers(): Observable<List<User>> = getAll()

    fun getUserWithId(identifier: String): Observable<User> = get(identifier)
}