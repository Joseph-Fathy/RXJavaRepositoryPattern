package com.example.rxrepositorytutorial.user.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    @SerializedName("user_id")
    var userId: String,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String,

    @ColumnInfo(name = "email")
    @SerializedName("email")
    var email: String,

    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    var phone: String
)