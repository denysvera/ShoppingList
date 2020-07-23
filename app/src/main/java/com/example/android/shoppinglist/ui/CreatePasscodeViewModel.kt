package com.example.android.shoppinglist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.shoppinglist.models.User
import com.example.android.shoppinglist.repository.ShoppingListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class ShoppingListStatus {LOADING, ERROR, DONE}
class CreatePasscodeViewModel(private val shoppingListRepository: ShoppingListRepository) : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val appUser = shoppingListRepository.appUser

    fun createUser(userName: String,passcode: String){
        coroutineScope.launch {
            shoppingListRepository.addUser(User(userName, passcode))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}