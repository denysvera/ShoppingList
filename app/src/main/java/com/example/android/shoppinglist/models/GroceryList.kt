package com.example.android.shoppinglist.models

import android.os.Parcelable
import com.example.android.shoppinglist.database.GroceryListEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GroceryList(val listName: String, var groceryItemList: ArrayList<GroceryItem>) : Parcelable {

    fun getCompletion():String{
        var totalBought = 0
        val totalItems = groceryItemList.size
        for (groceryItem in groceryItemList){
            if (groceryItem.inCart){
                totalBought++
            }
        }
        return "$totalBought/$totalItems"
    }
    fun isCompleted():Boolean{
        val temp = getCompletion().split('/').toTypedArray()

        val totalBought = temp[0].toInt()
        val totalItems = temp[1].toInt()
        if (totalItems ==0){
            return false
        }
        return totalBought/totalItems ==1

    }
}

fun GroceryList.asGroceryListEntity():GroceryListEntity{
    return GroceryListEntity(listName, groceryItemList)
}