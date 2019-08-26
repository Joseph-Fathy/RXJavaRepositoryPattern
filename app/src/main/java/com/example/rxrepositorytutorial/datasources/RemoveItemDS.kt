package com.example.rxrepositorytutorial.datasources

import io.reactivex.Completable

interface RemoveItemDS<T : Any> {
    fun remove(t: T): Completable
}