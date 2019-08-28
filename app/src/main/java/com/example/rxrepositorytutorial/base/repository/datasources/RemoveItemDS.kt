package com.example.rxrepositorytutorial.base.repository.datasources

import io.reactivex.Completable

interface RemoveItemDS<T : Any> {
    fun remove(t: T): Completable
}