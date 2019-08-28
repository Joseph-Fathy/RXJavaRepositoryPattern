package com.example.rxrepositorytutorial.base_repository

import io.reactivex.Observable

open class BaseRepository<T : Any>
    (private val api: RemoteDataSource<T>, private val db: LocalDS<T>
     ,private val memory: LocalDS<T>, private val cachePolicy: ICachePolicy)
{
    fun get(identifier: String): Observable<T>
    {
        var apiObservable = api.get(identifier).doOnNext{db.save(it)}
        var dbObservable = db.get(identifier)
        var memoryObservable = memory.get(identifier)

//        return Observable.concatArray(dbObservable, apiObservable)
        return test(apiObservable,dbObservable,memoryObservable)
    }


    fun getAll():Observable<List<T>>{
        var apiObservable = api.getAll().doOnNext{db.saveAll(it).subscribe()}
        var dbObservable = db.getAll()
        var memoryObservable = memory.getAll()



        //return  Observable.concatArray(dbObservable,apiObservable)
        return test(apiObservable,dbObservable,memoryObservable)
    }



    fun <T>test(apiObservable:Observable<T>, dbObservable:Observable<T>, memoryObservable:Observable<T>):Observable<T>{

        var returnedObservable:Observable<T>


        when {
            //should call the api regardless there is cache or not
            cachePolicy.shouldCallApi() -> {
                returnedObservable = apiObservable
                if(cachePolicy.isMemoryCacheValid() && cachePolicy.isDiskCacheValid()){
                    returnedObservable=memoryObservable
                }
            }

            //Get the data from Memory
            cachePolicy.isMemoryCacheValid() -> {
                returnedObservable = memoryObservable
                if(cachePolicy.shouldCallApi()&&cachePolicy.isDiskCacheValid()){

                }
            }

            //Get the data from Database
            cachePolicy.isDiskCacheValid() -> {
                returnedObservable = dbObservable
                if(cachePolicy.isMemoryCacheValid()&&cachePolicy.shouldCallApi()){

                }
            }

            else -> //NO API , NO Memory , NO DB
                returnedObservable = Observable.empty()
        }

        return returnedObservable

    }
}