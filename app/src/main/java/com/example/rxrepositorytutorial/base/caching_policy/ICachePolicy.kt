package com.example.rxrepositorytutorial.base.caching_policy

interface ICachePolicy {
    /** Source of the data */
    // Should return true if the data needs to be updated, either if it's valid or not.
    fun shouldGetFromApi(): Boolean

    // Should return false only if the disk cache is not valid anymore and should not be used before updating.
    fun shouldGetFromDatabase(): Boolean

    //Should return false only if the memory cache is not valid anymore and should not be used.
    fun shouldGetFromMemoryCache(): Boolean


    /** Destination of the data */
    // Should return true only if the data should be cached in a persistent storage,
    // otherwise, it will not be cached.
    fun shouldSaveInDatabase(): Boolean

    // Should return true only if the data should be cached in memory,
    // otherwise, it will fetched again every time.
    fun shouldSaveInMemoryCache(): Boolean
}