package com.example.rxrepositorytutorial.app.screens.users_list.repo

import com.example.rxrepositorytutorial.base.repository.BaseRepository
import com.example.rxrepositorytutorial.base.caching_policy.DefaultCachingPolicy
import com.example.rxrepositorytutorial.app.screens.users_list.model.User
import io.reactivex.Observable

class UserRepository(var remoteDS: UserRemoteDS, var localDS: UserLocalDS, var memoryDS: UserMemoryDS, cachingPolicy: DefaultCachingPolicy)
    : BaseRepository<User>(remoteDS, localDS , memoryDS,cachingPolicy) {


    fun getUsers(): Observable<List<User>> = getAll()
    fun getUserWithId(identifier: String): Observable<User> = get(identifier)
}