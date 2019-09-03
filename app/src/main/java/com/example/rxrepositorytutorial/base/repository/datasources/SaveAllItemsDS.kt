package com.example.rxrepositorytutorial.base.repository.datasources

import io.reactivex.Observable

interface SaveAllItemsDS<T : Any> {
    fun saveAll(t: List<T>): Observable<List<T>>{
        return Observable.just(t)
    }
}