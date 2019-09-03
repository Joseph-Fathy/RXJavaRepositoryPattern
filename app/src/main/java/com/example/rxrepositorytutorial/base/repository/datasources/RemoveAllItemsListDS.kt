package com.example.rxrepositorytutorial.base.repository.datasources

import io.reactivex.Completable

interface RemoveAllItemsListDS<T : Any> {
    fun removeAll(): Completable{
        return Completable.complete()
    }
}