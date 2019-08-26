package com.example.rxrepositorytutorial.datasources

import io.reactivex.Observable

interface GetAllItemsDS<T : Any> {

    fun getAll(): Observable<List<T>>
}