package com.example.rxrepositorytutorial.base.repository

import com.example.rxrepositorytutorial.base.repository.datasources.GetAllItemsDS
import com.example.rxrepositorytutorial.base.repository.datasources.GetItemDS

interface RemoteDataSource<T : Any> : GetItemDS<T>,
    GetAllItemsDS<T>