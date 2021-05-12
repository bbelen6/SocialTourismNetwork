package com.miwfem.socialtourismnetwork.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.ui.auth.AuthFragment
import com.miwfem.socialtourismnetwork.utils.TAG_AUTH

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(AuthFragment.newInstance(), R.id.fragmentComplete, TAG_AUTH)
    }

    fun replaceFragment(fragment: Fragment, layout: Int, tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(layout, fragment, tag)
        fragmentTransaction.commit()
    }

    fun addFragment(fragment: Fragment, layout: Int, tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(layout, fragment, tag)
        fragmentTransaction.addToBackStack(fragment::class.java.name)
        fragmentTransaction.commit()
    }
}