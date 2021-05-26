package com.miwfem.socialtourismnetwork.ui.main

import android.content.SharedPreferences
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

    private lateinit var topMenu: Menu
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(HomeFragment.newInstance("", ""), R.id.fragmentComplete, TAG_HOME)
        bottomNavigation()
        sharedPreferences = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let { topMenu = menu }
        menuInflater.inflate(R.menu.top_navigation, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_login -> {
                val user = sharedPreferences.getString(EMAIL, null)
                user?.let {
                    changeTopIcon(false)
                    val sharedEdit = sharedPreferences.edit()
                    sharedEdit.remove(EMAIL)
                    sharedEdit.apply()
                } ?: kotlin.run {
                    supportFragmentManager.findFragmentByTag(TAG_AUTH)?.let { fragment ->
                        if (!fragment.isVisible) addFragment(
                            AuthFragment.newInstance(),
                            R.id.fragmentComplete,
                            TAG_AUTH
                        )
                    } ?: addFragment(AuthFragment.newInstance(), R.id.fragmentComplete, TAG_AUTH)
                }
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

    private fun addFragment(fragment: Fragment, layout: Int, tag: String) {
        supportFragmentManager.beginTransaction().apply {
            add(layout, fragment, tag)
            addToBackStack(fragment::class.java.name)
            commit()
        }
    }

    private fun bottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    replaceFragment(
                        HomeFragment.newInstance("", ""),
                        R.id.fragmentComplete,
                        TAG_HOME
                    )
                }
                R.id.nav_add -> {
                    replaceFragment(
                        AddPostFragment.newInstance("", ""),
                        R.id.fragmentComplete,
                        TAG_ADD_POST
                    )
                }
                R.id.nav_profile -> {
                    replaceFragment(
                        ProfileFragment.newInstance("", ""),
                        R.id.fragmentComplete,
                        TAG_PROFILE
                    )
                }
                R.id.nav_settings -> {
                    replaceFragment(
                        SettingsFragment.newInstance("", ""),
                        R.id.fragmentComplete,
                        TAG_SETTINGS
                    )
                }
            }
            true
        }
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

    fun changeTopIcon(log: Boolean) {
        topMenu.findItem(R.id.nav_login)?.setIcon(
            if (log) R.drawable.ic_baseline_logout_24
            else R.drawable.ic_baseline_login_24
        )
    }
}