package com.example.rxrepositorytutorial.base_repository

import com.example.rxrepositorytutorial.base_repository.ICachePolicy

class DefaultCachingPolicy :ICachePolicy {
    override fun shouldGetFromApi()=true

    override fun shouldGetFromDatabase()=false

    override fun shouldGetFromMemoryCache()=false

    override fun shouldSaveInDatabase()=true

    override fun shouldSaveInMemoryCache()=true
}