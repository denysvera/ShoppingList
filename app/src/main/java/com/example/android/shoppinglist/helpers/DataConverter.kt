package com.example.android.shoppinglist.helpers

import android.os.Parcelable
import androidx.room.TypeConverter
import com.example.android.shoppinglist.models.GroceryItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize

@Parcelize
class DataConverter : Parcelable{

    @TypeConverter
    fun fromGroceryItemList(value: ArrayList<GroceryItem>): String?{
        val gson = Gson()
        val type = object : TypeToken<ArrayList<GroceryItem>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toGroceryItemList(value: String?): ArrayList<GroceryItem>?{
        val gson = Gson()
        val type = object : TypeToken<ArrayList<GroceryItem>>() {}.type
        return gson.fromJson(value, type)
    }
}