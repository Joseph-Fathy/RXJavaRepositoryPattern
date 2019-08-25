package com.example.rxrepositorytutorial.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.rxrepositorytutorial.App
import com.example.rxrepositorytutorial.repo.UserRepository
import io.reactivex.Observable
import io.reactivex.Scheduler

class UsersViewModel(private var repository: UserRepository) {
    var loadingLiveData = MutableLiveData<Boolean>()
    var responseLiveData = MutableLiveData<String>()


    var gson = App.injectGson()

    fun getUsers(): Observable<UsersListResponse> {
        return repository.getUsers()
            .doOnSubscribe{
                loadingLiveData.postValue(true)
            }
            .map {
                Log.wtf("RXRepo", "UsersViewModel getUsers()=--> map =--> ${(it.size)}")
                responseLiveData.postValue(gson.toJson(it))
                UsersListResponse(it)
            }
            .onErrorReturn {
                Log.wtf("RXRepo", "UsersViewModel getUsers()=--> error =-->${it.message}")
                UsersListResponse(emptyList(), "error happened", it)
            }
            .doOnComplete {
                loadingLiveData.postValue(false)
            }
    }
}