package com.example.rxrepositorytutorial.base_repository

import com.example.rxrepositorytutorial.datasources.GetAllItemsDS
import com.example.rxrepositorytutorial.datasources.GetItemDS

interface RemoteDataSource<T : Any> : GetItemDS<T>,
    GetAllItemsDS<T>