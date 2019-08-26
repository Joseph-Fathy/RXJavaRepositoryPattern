package com.example.rxrepositorytutorial.datasources

import io.reactivex.Observable

interface SaveItemDS<T : Any> {
    fun save(t: T): Observable<T>
}