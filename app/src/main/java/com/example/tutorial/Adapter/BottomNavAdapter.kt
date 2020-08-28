package com.example.tutorial.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.tutorial.Fragment.*

class BottomNavAdapter(fm : FragmentManager, private val fragmentCount : Int) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> MyPageFragment()
            1 -> SearchFragment()
            2 -> HomeFragment()
            3 -> LikeFragment()
            4 -> SettingsFragment()
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int = fragmentCount

}