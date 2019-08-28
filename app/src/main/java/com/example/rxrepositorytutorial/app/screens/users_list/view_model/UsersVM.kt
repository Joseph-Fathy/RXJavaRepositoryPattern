package com.example.rxrepositorytutorial.app.screens.users_list.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.rxrepositorytutorial.app.App
import com.example.rxrepositorytutorial.app.screens.users_list.repo.UserRepository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class UsersVM(private var repository: UserRepository) {
    var loadingLiveData = MutableLiveData<Boolean>()
    var responseLiveData = MutableLiveData<String>()

    var gson = App.injectGson()

    fun getUsers(): Observable<UsersListResponse> {
        return repository.getUsers()
            .doOnSubscribe {
                loadingLiveData.postValue(true)
                responseLiveData.postValue("Loading.....")
            }
            .map {
                Log.wtf("RxRepo", "UsersViewModel getUsers()=--> map =--> ${(it.size)}")
                responseLiveData.postValue(gson.toJson(it))
                UsersListResponse(it)
            }
            .onErrorReturn {
                Log.wtf("RxRepo", "UsersViewModel getUsers()=--> error =-->${it.message}")
                UsersListResponse(
                    emptyList(),
                    "error happened",
                    it
                )
            }
            .doOnComplete {
                loadingLiveData.postValue(false)
            }
    }


    fun getUserWithId(identifier: String): Observable<UserItemResponse> {
        return repository.getUserWithId(identifier)
            .delay(5, TimeUnit.SECONDS)
            .doOnSubscribe {
                loadingLiveData.postValue(true)
                responseLiveData.postValue("Loading.....")
            }
            .map {
                Log.wtf("RxRepo", "UsersViewModel getUserWithId()=--> map =--> ${(it.userId)}")
                responseLiveData.postValue(gson.toJson(it))
                UserItemResponse(it)
            }
            .onErrorReturn {
                Log.wtf("RxRepo", "UsersViewModel getUserWithId()=--> error =-->${it.message}")
                UserItemResponse(
                    null,
                    "error happened",
                    it
                )
            }
            .doOnComplete {
                loadingLiveData.postValue(false)
            }
    }
}