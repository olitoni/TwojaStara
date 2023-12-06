package com.example.twojastara

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.twojastara.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.bottomNavigationView.setOnItemSelectedListener {
//            when(it.itemId) {
//                R.id.home -> replaceFragment(Home())
//                else -> {
//
//                }
//            }
//            true
//        }
//
//        val bottomNav = findViewById<View>(R.id.bottomNavigationView) as BottomNavigationView
//        bottomNav.menu.findItem(R.id.home).setChecked(true)
//
//        replaceFragment(Home())
    }

//    private fun replaceFragment(fragment: Fragment) {
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.frame, fragment)
//        fragmentTransaction.commit()
//    }
}