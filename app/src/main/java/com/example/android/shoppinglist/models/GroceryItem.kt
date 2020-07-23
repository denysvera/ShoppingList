package com.example.android.shoppinglist.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  GroceryItem (var name: String,
                         var quantity: String,
                         var unit: String?,
                         var inCart: Boolean): Parcelable
