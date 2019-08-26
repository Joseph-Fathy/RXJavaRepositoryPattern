package com.example.rxrepositorytutorial.user.view_model

import com.example.rxrepositorytutorial.user.model.User

data class UsersListResponse(var users: List<User>, var error: String? = null, var throwable: Throwable? = null)
data class UserItemResponse(var user: User?, var error: String? = null, var throwable: Throwable? = null)