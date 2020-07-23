package com.example.android.shoppinglist.models

import android.os.Parcelable
import com.example.android.shoppinglist.database.UserEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val userName: String, val passcode: String): Parcelable

fun User.asUserEntity(): UserEntity{
    return UserEntity(userName, passcode)
}
