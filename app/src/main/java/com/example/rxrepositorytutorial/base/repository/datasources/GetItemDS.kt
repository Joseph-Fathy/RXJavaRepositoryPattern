package com.example.rxrepositorytutorial.base.repository.datasources

import io.reactivex.Observable
//get item without passing any parameters
interface GetItemDS<T : Any> {
    fun get(): Observable<T> {
        return Observable.empty()
    }
}

//get item with passing any type of id as a parameter
interface GetItemWithAnyIdentifierDS<T : Any, Identifier> {
    fun get(identifier: Identifier): Observable<T> {
        return Observable.empty()
    }
}

//get item with passing string id as a parameter
interface GetItemWithStringIdentifierDS<T : Any> : GetItemWithAnyIdentifierDS<T, String>

