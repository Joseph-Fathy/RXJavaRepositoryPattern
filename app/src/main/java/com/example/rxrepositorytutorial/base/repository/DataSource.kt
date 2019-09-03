package com.example.rxrepositorytutorial.base.repository

import com.example.rxrepositorytutorial.base.repository.datasources.*


abstract class DataSource<T : Any> :
    GetItemDS<T>,
    GetItemWithStringIdentifierDS<T>,
    GetAllItemsDS<T>,
    SaveItemDS<T>,
    SaveAllItemsDS<T>,
    RemoveItemDS<T>,
    RemoveAllItemsListDS<T> {
}