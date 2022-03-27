package com.example.testapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.testapplication.ui.topic.SearchTopicFragment
import com.example.testapplication.ui.news.NewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val manager by lazy { supportFragmentManager }
    private val fragmentContainerId by lazy { R.id.fragment_container }
    lateinit var bottomNav: BottomNavigationView
    private lateinit var transaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = findViewById(R.id.bottom_nav)
        setMenuClickListener()
        replaceFragment(NewsFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment): Boolean {
        transaction = manager.beginTransaction()
        transaction.replace(fragmentContainerId, fragment).commit()
        return true
    }

    private fun setMenuClickListener() {
        bottomNav.setOnItemSelectedListener { selectedItem ->
            when (selectedItem.itemId) {
                R.id.headlines -> {
                    replaceFragment(NewsFragment.newInstance())
                    true
                }
                R.id.search_topic -> {
                    replaceFragment(SearchTopicFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }
}