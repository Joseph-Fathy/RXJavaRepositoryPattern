package com.example.rxrepositorytutorial.base_repository

import com.example.rxrepositorytutorial.datasources.GetAllItemsDS
import com.example.rxrepositorytutorial.datasources.GetItemDS
import com.example.rxrepositorytutorial.datasources.SaveAllItemsDS
import com.example.rxrepositorytutorial.datasources.SaveItemDS

interface LocalDS<T : Any> : GetItemDS<T>,
    GetAllItemsDS<T>, SaveItemDS<T>, SaveAllItemsDS<T>