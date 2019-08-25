package com.example.rxrepositorytutorial.view_model

import com.example.rxrepositorytutorial.model.User

data class UsersListResponse(var users: List<User>, var error: String? = null, var throwable: Throwable? = null)