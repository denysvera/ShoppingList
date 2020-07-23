package com.example.android.shoppinglist.ui

import androidx.lifecycle.ViewModel
import com.example.android.shoppinglist.models.GroceryList
import com.example.android.shoppinglist.repository.ShoppingListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GroceryDetailsViewModel (private val shoppingListRepository: ShoppingListRepository): ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun updateGroceryList(groceryList: GroceryList){
        coroutineScope.launch {


            shoppingListRepository.updateGroceryList(groceryList)

        }
    }
}