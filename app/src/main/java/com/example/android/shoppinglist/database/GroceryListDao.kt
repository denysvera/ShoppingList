package com.example.android.shoppinglist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GroceryListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroceryList(groceryListEntity: GroceryListEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg groceryListEntity: GroceryListEntity)

    @Query("SELECT * FROM groceryList")
    fun getGroceryLists() : LiveData<List<GroceryListEntity>>

    @Query("DELETE FROM groceryList")
    fun deleteAll()

    @Query("DELETE FROM groceryList WHERE listName = :listName")
    fun deleteGroceryList(listName: String)

    @Update
    fun updateGroceryList(groceryListEntity: GroceryListEntity)
}