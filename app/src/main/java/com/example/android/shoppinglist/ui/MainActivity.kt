package com.example.android.shoppinglist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.android.shoppinglist.R


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        /*  val topLevelDestinations: MutableSet<Int> = HashSet()
         topLevelDestinations.add(R.id.fragment1)
         topLevelDestinations.add(R.id.fragment2)
      val appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations)
             .setDrawerLayout(drawerLayout)
             .build()*/
         NavigationUI.setupActionBarWithNavController(this,navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}