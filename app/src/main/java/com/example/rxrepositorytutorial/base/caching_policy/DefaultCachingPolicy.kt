package com.example.rxrepositorytutorial.base.caching_policy

class DefaultCachingPolicy : ICachePolicy {
    /**Always Get Data from Api and cache it in both database and memory,
     * if you want to get from cache update the implementation
     * of shouldGetFromDatabase() or shouldGetFromMemoryCache()*/


    override fun shouldGetFromApi() = true

    override fun shouldGetFromDatabase() = false

    override fun shouldGetFromMemoryCache() = false

    override fun shouldSaveInDatabase() = true

    override fun shouldSaveInMemoryCache() = true
}