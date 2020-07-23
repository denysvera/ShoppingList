package com.example.android.shoppinglist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.shoppinglist.models.GroceryList
import com.example.android.shoppinglist.models.User
import com.example.android.shoppinglist.repository.ShoppingListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GroceryListsViewModel(private val shoppingListRepository: ShoppingListRepository ): ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ShoppingListStatus>()
    val appUser = shoppingListRepository.appUser

    val groceryList = shoppingListRepository.groceryLists
    val status: LiveData<ShoppingListStatus>
        get() = _status
    fun addGroceryListToDB(groceryList: GroceryList){
        coroutineScope.launch {
            _status.value = ShoppingListStatus.LOADING

            shoppingListRepository.addGroceryList(groceryList)
            _status.value = ShoppingListStatus.DONE
        }
    }

    fun deleteGroceryList(groceryList: GroceryList) {
        coroutineScope.launch {
            shoppingListRepository.deleteGroceryList(groceryList)
        }
    }


}