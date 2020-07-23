package com.example.android.shoppinglist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.shoppinglist.repository.ShoppingListRepository
import java.lang.IllegalArgumentException

class ViewModelFactory (private val shoppingListRepository: ShoppingListRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroceryListsViewModel::class.java)){
            return GroceryListsViewModel(shoppingListRepository)as T
        }else if(modelClass.isAssignableFrom(GroceryDetailsViewModel::class.java)){
            return GroceryDetailsViewModel(shoppingListRepository)as T
        }else if (modelClass.isAssignableFrom(CreatePasscodeViewModel::class.java)){
            return CreatePasscodeViewModel(shoppingListRepository)as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}