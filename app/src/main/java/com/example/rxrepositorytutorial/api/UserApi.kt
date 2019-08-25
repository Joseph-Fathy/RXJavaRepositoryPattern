package com.example.rxrepositorytutorial.api

import com.example.rxrepositorytutorial.model.User
import io.reactivex.Observable
import retrofit2.http.GET

interface UserApi {
    @GET(API_USERS)
    fun getUsers(): Observable<List<User>>
}