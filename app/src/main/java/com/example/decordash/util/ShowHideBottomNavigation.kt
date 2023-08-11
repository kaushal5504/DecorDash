package com.example.decordash.util

import androidx.fragment.app.Fragment
import com.example.decordash.R
import com.example.decordash.activities.ShoppingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(
            com.example.decordash.R.id.bottomNavigation
        )
    bottomNavigationView.visibility = android.view.View.GONE
}

fun Fragment.showBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(
            com.example.decordash.R.id.bottomNavigation
        )
    bottomNavigationView.visibility = android.view.View.VISIBLE
}