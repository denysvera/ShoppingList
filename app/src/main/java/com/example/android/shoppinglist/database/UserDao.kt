package com.example.android.shoppinglist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): LiveData<UserEntity>

    @Query("DELETE FROM user")
    fun deleteUser()
}