package com.example.rxrepositorytutorial.datasources

import io.reactivex.Observable

interface SaveAllItemsDS<T : Any> {
    fun saveAll(t: List<T>): Observable<List<T>>
}