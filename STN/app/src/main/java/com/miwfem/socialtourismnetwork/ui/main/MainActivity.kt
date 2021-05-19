package com.miwfem.socialtourismnetwork.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.ui.addPost.AddPostFragment
import com.miwfem.socialtourismnetwork.ui.auth.AuthFragment
import com.miwfem.socialtourismnetwork.ui.base.BaseFragment
import com.miwfem.socialtourismnetwork.ui.home.HomeFragment
import com.miwfem.socialtourismnetwork.ui.profile.ProfileFragment
import com.miwfem.socialtourismnetwork.ui.settings.SettingsFragment
import com.miwfem.socialtourismnetwork.utils.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(HomeFragment.newInstance("", ""), R.id.fragmentComplete, TAG_HOME)
        bottomNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_navigation, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_login -> {
                supportFragmentManager.findFragmentByTag(TAG_AUTH)?.let { fragment ->
                    if (!fragment.isVisible) fragmentNavigation(
                        AuthFragment.newInstance(),
                        TAG_AUTH
                    )
                } ?: fragmentNavigation(AuthFragment.newInstance(), TAG_AUTH)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment, layout: Int, tag: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(layout, fragment, tag)
            commit()
        }
    }

    fun addFragment(fragment: Fragment, layout: Int, tag: String) {
        supportFragmentManager.beginTransaction().apply {
            add(layout, fragment, tag)
            addToBackStack(fragment::class.java.name)
            commit()
        }
    }

    private fun showFragment(tag: String) {
        with(supportFragmentManager) {
            findFragmentByTag(tag)?.let { fragment ->
                beginTransaction().show(fragment).commit()
            }
        }
    }

    private fun bottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    fragmentNavigation(HomeFragment.newInstance("", ""), TAG_HOME)
                }
                R.id.nav_add -> {
                    fragmentNavigation(AddPostFragment.newInstance("", ""), TAG_ADD_POST)
                }
                R.id.nav_profile -> {
                    fragmentNavigation(ProfileFragment.newInstance("", ""), TAG_PROFILE)
                }
                R.id.nav_settings -> {
                    fragmentNavigation(SettingsFragment.newInstance("", ""), TAG_SETTINGS)
                }
            }
            true
        }
    }

    private fun fragmentNavigation(selectedFragment: Fragment, tag: String) {
        supportFragmentManager.findFragmentByTag(tag)?.let { fragment ->
            when (tag) {
                TAG_HOME -> {
                }
                TAG_ADD_POST -> {
                }
                TAG_PROFILE -> {
                }
                TAG_SETTINGS -> {
                }
                else -> {
                }
            }
            //supportFragmentManager.beginTransaction().show(fragment).commit()
            replaceFragment(fragment, R.id.fragmentComplete, tag)
        } ?: addFragment(selectedFragment, R.id.fragmentComplete, tag)
    }

    fun hideBottomMenu() {
        bottom_navigation.isVisible = false
    }

    fun showBottomMenu() {
        bottom_navigation.isVisible = true
    }

    override fun onBackPressed() {
        supportFragmentManager.findFragmentById(R.id.fragmentComplete)?.let { fragment ->
            if (fragment is BaseFragment) {
                fragment.handleOnBackPressed()
            }
        }
        super.onBackPressed()
    }
}