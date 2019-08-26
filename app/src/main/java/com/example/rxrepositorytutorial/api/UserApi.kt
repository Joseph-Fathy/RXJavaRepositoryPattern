package com.example.rxrepositorytutorial.api

import com.example.rxrepositorytutorial.user.model.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET(API_USERS)
    fun getUsers(): Observable<List<User>>

    @GET(API_USER)
    fun getUser(@Path("identifier") identifier: String): Observable<User>
}