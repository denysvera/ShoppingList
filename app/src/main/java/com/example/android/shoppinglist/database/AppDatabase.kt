package com.example.android.shoppinglist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.shoppinglist.helpers.DataConverter

@Database(entities = [UserEntity::class,GroceryListEntity::class],version = 5)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun groceryListDao(): GroceryListDao
    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getDatabse(context: Context): AppDatabase?{
            if(INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"shopping.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}