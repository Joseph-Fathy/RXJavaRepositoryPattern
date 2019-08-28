package com.example.rxrepositorytutorial.base.repository.datasources

import io.reactivex.Completable

interface RemoveAllIemsListDS<T : Any> {
    fun removeAll(): Completable
}