package com.example.rxrepositorytutorial.base.repository

import com.example.rxrepositorytutorial.base.repository.datasources.GetAllItemsDS
import com.example.rxrepositorytutorial.base.repository.datasources.GetItemDS
import com.example.rxrepositorytutorial.base.repository.datasources.SaveAllItemsDS
import com.example.rxrepositorytutorial.base.repository.datasources.SaveItemDS

interface LocalDS<T : Any> : GetItemDS<T>,
    GetAllItemsDS<T>, SaveItemDS<T>, SaveAllItemsDS<T>