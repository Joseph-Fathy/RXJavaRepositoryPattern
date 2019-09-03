package com.example.rxrepositorytutorial.base.repository

import android.util.Log
import com.example.rxrepositorytutorial.base.caching_policy.ICachePolicy
import io.reactivex.Observable


// will be super class of other repositories
// it takes the implementations of data sources (API , DB , Memory)
open class BaseRepository<T : Any>
    (
    private val api: DataSource<T>, private val db: DataSource<T>,
    private val memory: DataSource<T>, private val cachePolicy: ICachePolicy
) {

    //get single object by id
    //if the id is null or empty call the method that does not take parameters in the DS
    fun get(identifier: String? = null): Observable<T> {

        var apiObservable = getApiObservable(identifier)
            .doOnSubscribe {
                Log.wtf("RxRepo", "API_get()_doOnSubscribe()_id=${identifier}")
            }
            .doOnNext {
                Log.wtf("RxRepo", "API_get()_doOnNext()___=${it}")
                if (cachePolicy.shouldSaveInDatabase()) {
                    Log.wtf("RxRepo", "API_get()_doOnNext()_saveInDatabase()_id=${identifier}")
                    db.save(it)
                        .doOnNext { saved ->
                            Log.wtf("RxRepo", "API_get()_doOnNext()_saveInDatabase()_doOnNext()_savedInDB__=${saved}")
                        }
                        .subscribe()
                }
                if (cachePolicy.shouldSaveInMemoryCache()) {
                    Log.wtf("RxRepo", "API_get()_doOnNext()_saveInMemoryCache()_id=${identifier}")
                    memory.save(it)
                        .doOnNext { saved ->
                            Log.wtf(
                                "RxRepo",
                                "API_get()_doOnNext()_saveInMemoryCache()_doOnNext()_cachedInMemory__=${saved}"
                            )
                        }.subscribe()
                }
            }

        var dbObservable = getDBObservable(identifier)
            .doOnSubscribe {
                Log.wtf("RxRepo", "DB_get()_doOnSubscribe()_id=${identifier}")
            }
            .doOnNext {
                Log.wtf("RxRepo", "DB_get()_doOnNext()___=${it}")
                if (cachePolicy.shouldSaveInMemoryCache())
                    Log.wtf("RxRepo", "DB_get()_doOnNext()_saveInMemoryCache()_id=${identifier}")

                memory.save(it)
                    .doOnNext { saved ->
                        Log.wtf(
                            "RxRepo",
                            "DB_get()_doOnNext()_saveInMemoryCache()_doOnNext()_cachedInMemory__=${saved}"
                        )
                    }
                    .subscribe()

            }

        var memoryObservable = getMemoryObservable(identifier)
            .doOnSubscribe {
                Log.wtf("RxRepo", "MEMORY_get()_doOnSubscribe()_id=${identifier}")
            }
            .doOnNext {
                Log.wtf("RxRepo", "MEMORY_get()_doOnNext()___=${it}")
            }

        return getObservable(apiObservable, dbObservable, memoryObservable)
    }

    private fun getApiObservable(identifier: String?): Observable<T> {
        return if (identifier.isNullOrEmpty()) api.get() else api.get(identifier)
    }

    private fun getDBObservable(identifier: String?): Observable<T> {
        return if (identifier.isNullOrEmpty()) db.get() else db.get(identifier)
    }

    private fun getMemoryObservable(identifier: String?): Observable<T> {
        return if (identifier.isNullOrEmpty()) memory.get() else memory.get(identifier)
    }


    //get list of objects
    fun getAll(): Observable<List<T>> {
        var apiObservable = api.getAll()
            .doOnSubscribe {
                Log.wtf("RxRepo", "API_getAll()_doOnSubscribe()")
            }
            .doOnNext {
                Log.wtf("RxRepo", "API_getAll()_doOnNext()")

                if (cachePolicy.shouldSaveInDatabase())
                    db.saveAll(it)
                        .doOnNext { saved ->
                            Log.wtf(
                                "RxRepo",
                                "API_get()_doOnNext()_saveAllInDatabase()_doOnNext()_savedInDB__=${saved}"
                            )
                        }
                        .subscribe()
                if (cachePolicy.shouldSaveInMemoryCache())
                    memory.saveAll(it)
                        .doOnNext { saved ->
                            Log.wtf(
                                "RxRepo",
                                "API_get()_doOnNext()_saveAllInMemoryCache()_doOnNext()_savedInDB__=${saved}"
                            )
                        }
                        .subscribe()
            }

        var dbObservable = db.getAll()
            .doOnSubscribe {
                Log.wtf("RxRepo", "DB_getAll()_doOnSubscribe()")
            }
            .doOnNext {
                Log.wtf("RxRepo", "DB_getAll()_doOnNext()")

                if (cachePolicy.shouldSaveInMemoryCache()) {
                    Log.wtf("RxRepo", "DB_getAll()_doOnNext()_saveAllInMemoryCache()")
                    memory.saveAll(it)
                        .doOnNext { saved ->
                            Log.wtf(
                                "RxRepo",
                                "DB_getAll()_doOnNext()_saveAllInMemoryCache()_doOnNext()_cachedInMemory__=${saved}"
                            )
                        }.subscribe()
                }
            }


        var memoryObservable = memory.getAll()
            .doOnSubscribe {
                Log.wtf("RxRepo", "MEMORY_getAll()_doOnSubscribe()")
            }
            .doOnNext {
                Log.wtf("RxRepo", "MEMORY_getAll()_doOnNext()___=${it}")
            }

        return getObservable(apiObservable, dbObservable, memoryObservable)
    }


    //get observables according to the caching policy
    private fun <T> getObservable(
        apiObservable: Observable<T>,
        dbObservable: Observable<T>,
        memoryObservable: Observable<T>
    ): Observable<T> {

        return Observable.concatArrayDelayError(
            if (cachePolicy.shouldGetFromMemoryCache()) memoryObservable else Observable.empty(),
            if (cachePolicy.shouldGetFromDatabase()) dbObservable else Observable.empty(),
            if (cachePolicy.shouldGetFromApi()) apiObservable else Observable.empty()
        )

    }
}