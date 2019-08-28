package com.example.rxrepositorytutorial.base.repository

import android.util.Log
import com.example.rxrepositorytutorial.base.caching_policy.ICachePolicy
import io.reactivex.Observable

open class BaseRepository<T : Any>
    (
    private val api: RemoteDataSource<T>, private val db: LocalDS<T>
    , private val memory: LocalDS<T>, private val cachePolicy: ICachePolicy
) {
    fun get(identifier: String): Observable<T> {
        var apiObservable = api.get(identifier)
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

        var dbObservable = db.get(identifier)
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
                    }.subscribe()

            }

        var memoryObservable = memory.get(identifier)
            .doOnSubscribe {
                Log.wtf("RxRepo", "MEMORY_get()_doOnSubscribe()_id=${identifier}")
            }
            .doOnNext {
                Log.wtf("RxRepo", "MEMORY_get()_doOnNext()___=${it}")
            }

        return getObservable(apiObservable, dbObservable, memoryObservable)
    }


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
                    memory.saveAll(it).doOnNext { saved ->
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