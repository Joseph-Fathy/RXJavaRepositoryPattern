package com.example.rxrepositorytutorial.base_repository

import io.reactivex.Observable

open class BaseRepository<T : Any>
    (
    private val api: RemoteDataSource<T>, private val db: LocalDS<T>
    , private val memory: LocalDS<T>, private val cachePolicy: ICachePolicy
) {
    fun get(identifier: String): Observable<T> {
        var apiObservable = api.get(identifier).doOnNext { db.save(it) }
        var dbObservable = db.get(identifier)
        var memoryObservable = memory.get(identifier)

//        return Observable.concatArray(dbObservable, apiObservable)
        return getObservable(apiObservable, dbObservable, memoryObservable)
    }


    fun getAll(): Observable<List<T>> {
        var apiObservable = api.getAll().doOnNext {

            db.saveAll(it).subscribe()
        }
        var dbObservable = db.getAll()
        var memoryObservable = memory.getAll()


        //return  Observable.concatArray(dbObservable,apiObservable)
        return getObservable(apiObservable, dbObservable, memoryObservable)
    }


    private fun <T> getObservable(
        apiObservable: Observable<T>, dbObservable: Observable<T>,
        memoryObservable: Observable<T>
    ): Observable<T> {

        var returnedObservable: Observable<T>


        when {
            //should call the api regardless there is cache or not
            cachePolicy.shouldGetFromApi() -> {
                returnedObservable = apiObservable
                if (cachePolicy.shouldGetFromMemoryCache() && cachePolicy.shouldGetFromDatabase()) {
                    returnedObservable = memoryObservable
                }
            }

            //Get the data from Memory
            cachePolicy.shouldGetFromMemoryCache() -> {
                returnedObservable = memoryObservable
                if (cachePolicy.shouldGetFromApi() && cachePolicy.shouldGetFromDatabase()) {

                }
            }

            //Get the data from Database
            cachePolicy.shouldGetFromDatabase() -> {
                returnedObservable = dbObservable
                if (cachePolicy.shouldGetFromMemoryCache() && cachePolicy.shouldGetFromApi()) {

                }
            }

            else -> //NO API , NO Memory , NO DB
                returnedObservable = Observable.empty()
        }

        return returnedObservable

    }
}