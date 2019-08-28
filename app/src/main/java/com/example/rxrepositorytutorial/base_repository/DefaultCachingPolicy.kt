package com.example.rxrepositorytutorial.base_repository

import com.example.rxrepositorytutorial.base_repository.ICachePolicy

class DefaultCachingPolicy :ICachePolicy {
    override fun shouldCallApi()=true

    override fun isDiskCacheValid()=false

    override fun isMemoryCacheValid()=false

    override fun shouldUseDatabaseCache()=true

    override fun shouldUseMemoryCache()=true
}