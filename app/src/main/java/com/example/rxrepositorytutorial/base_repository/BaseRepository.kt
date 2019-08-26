package com.example.rxrepositorytutorial.base_repository

import io.reactivex.Observable

open class BaseRepository<T : Any>
    (private val api: RemoteDataSource<T>, private val db: LocalDSRemove<T>)
{
    fun get(identifier: String): Observable<T> {
        var apiObservable = api.get(identifier).doOnNext{db.save(it)}
        var dbObservable = db.get(identifier)
        return Observable.concatArray(dbObservable, apiObservable)
    }


    fun getAll():Observable<List<T>>{
        var apiObservable = api.getAll().doOnNext{db.saveAll(it).subscribe()}
        var dbObservable = db.getAll()
        return  Observable.concatArray(dbObservable,apiObservable)
    }


}