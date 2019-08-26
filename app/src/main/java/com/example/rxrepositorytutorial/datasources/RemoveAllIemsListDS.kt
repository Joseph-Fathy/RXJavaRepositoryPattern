package com.example.rxrepositorytutorial.datasources

import io.reactivex.Completable

interface RemoveAllIemsListDS<T : Any> {
    fun removeAll(): Completable
}