package com.example.rxrepositorytutorial.app.api

import com.example.rxrepositorytutorial.app.screens.users_list.model.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET(API_ALL_USERS)
    fun getUsers(): Observable<List<User>>

    @GET(API_USER)
    fun getUser(@Path("identifier") identifier: String): Observable<User>
}