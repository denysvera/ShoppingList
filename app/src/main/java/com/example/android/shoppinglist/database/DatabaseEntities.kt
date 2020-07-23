package com.example.android.shoppinglist.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.android.shoppinglist.helpers.DataConverter
import com.example.android.shoppinglist.models.GroceryItem
import com.example.android.shoppinglist.models.GroceryList
import com.example.android.shoppinglist.models.User
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user")
data class UserEntity(@ColumnInfo(name = "userName" )  val userName: String,
                      @PrimaryKey
                      @ColumnInfo(name = "passcode") val passcode: String

): Parcelable
fun UserEntity.asUserModel(): User{
    return User(userName = userName,passcode = passcode)
}

@Parcelize
@Entity(tableName = "groceryList")
data class GroceryListEntity(@PrimaryKey
                            @ColumnInfo(name = "listName") val listName: String,
                             @TypeConverters(DataConverter::class)
                             @ColumnInfo(name = "groceryItemList") val groceryItemList: ArrayList<GroceryItem>
)   : Parcelable
fun List<GroceryListEntity>.asGroceryListModel(): List<GroceryList>{
    return map { GroceryList(listName = it.listName, groceryItemList = it.groceryItemList) }
}