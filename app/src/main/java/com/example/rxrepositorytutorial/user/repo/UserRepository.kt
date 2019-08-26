package com.example.rxrepositorytutorial.user.repo

import com.example.rxrepositorytutorial.base_repository.BaseRepository
import com.example.rxrepositorytutorial.user.model.User
import io.reactivex.Observable

class UserRepository(var remoteDS: UserRemoteDS, var localDS: UserLocalDS) : BaseRepository<User>(remoteDS, localDS) {
    var memoryDS = UserMemoryDS.getInstance()

    fun getUsers(): Observable<List<User>> {
        return Observable.concat(memoryDS.getAll(), getAll()).first(emptyList()).toObservable()
            .doOnNext { memoryDS.saveAll(it) }
    }

    fun getUserWithId(identifier: String): Observable<User> = Observable.concat(memoryDS.get(identifier),get(identifier)).first(User()).toObservable()
}