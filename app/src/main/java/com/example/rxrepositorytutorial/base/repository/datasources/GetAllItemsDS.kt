package com.example.rxrepositorytutorial.base.repository.datasources

import io.reactivex.Observable

interface GetAllItemsDS<T : Any> {

    fun getAll(): Observable<List<T>>{
        return Observable.empty()
    }
}