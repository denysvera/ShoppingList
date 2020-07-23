package com.example.android.shoppinglist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.shoppinglist.database.AppDatabase
import com.example.android.shoppinglist.database.GroceryListEntity
import com.example.android.shoppinglist.database.UserEntity
import com.example.android.shoppinglist.database.asGroceryListModel
import com.example.android.shoppinglist.models.GroceryList
import com.example.android.shoppinglist.models.User
import com.example.android.shoppinglist.models.asGroceryListEntity
import com.example.android.shoppinglist.models.asUserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShoppingListRepository(val database: AppDatabase) {


    suspend fun addUser(user: User){
        withContext(Dispatchers.IO){
            database.userDao().insertUser(user.asUserEntity())
        }
    }

    suspend fun addGroceryList(groceryList: GroceryList){
        withContext(Dispatchers.IO){
            database.groceryListDao().insertGroceryList(groceryList.asGroceryListEntity())
        }
    }

    suspend fun deleteGroceryList(groceryList: GroceryList){
        withContext(Dispatchers.IO){
            database.groceryListDao().deleteGroceryList(groceryList.listName)
        }
    }

    suspend fun updateGroceryList(groceryList: GroceryList){
        withContext(Dispatchers.IO){
            database.groceryListDao().updateGroceryList(groceryList.asGroceryListEntity())
        }
    }
    val appUser: LiveData<UserEntity> = database.userDao().getUser()

    val groceryLists : LiveData<List<GroceryList>> = Transformations.map(database.groceryListDao().getGroceryLists()){
        it.asGroceryListModel()
    }


}