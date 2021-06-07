package com.miwfem.socialtourismnetwork.presentation.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.view.AddPostFragment
import com.miwfem.socialtourismnetwork.presentation.ui.auth.view.AuthFragment
import com.miwfem.socialtourismnetwork.presentation.ui.home.view.HomeFragment
import com.miwfem.socialtourismnetwork.presentation.ui.profile.ProfileFragment
import com.miwfem.socialtourismnetwork.presentation.ui.settings.SettingsFragment
import com.miwfem.socialtourismnetwork.utils.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var topMenu: Menu
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE)
        replaceFragment(
            HomeFragment.newInstance(sharedPreferences.getString(EMAIL, null)),
            R.id.fragmentComplete,
            TAG_HOME
        )
        bottomNavigation()
        setBottomOptions(sharedPreferences.getString(EMAIL, null))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let { topMenu = menu }
        menuInflater.inflate(R.menu.top_navigation, menu)
        val user = sharedPreferences.getString(EMAIL, null)
        user?.let {
            changeTopIcon(true)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_login -> {
                val user = sharedPreferences.getString(EMAIL, null)
                user?.let {
                    showCloseSessionAlert()
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

    fun setBottomOptions(user: String? = null) {
        bottom_navigation.menu.apply {
            user?.let {
                findItem(R.id.nav_settings).isVisible = true
                findItem(R.id.nav_add).isVisible = true
                findItem(R.id.nav_profile).isVisible = true
            } ?: kotlin.run {
                findItem(R.id.nav_settings).isVisible = false
                findItem(R.id.nav_add).isVisible = false
                findItem(R.id.nav_profile).isVisible = false
            }
        }
    }

    private fun bottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    if (supportFragmentManager.findFragmentByTag(TAG_HOME) == null)
                        replaceFragment(
                            HomeFragment.newInstance(sharedPreferences.getString(EMAIL, null)),
                            R.id.fragmentComplete,
                            TAG_HOME
                        )
                }
                R.id.nav_add -> {
                    if (supportFragmentManager.findFragmentByTag(TAG_ADD_POST) == null)
                        replaceFragment(
                            AddPostFragment.newInstance(sharedPreferences.getString(EMAIL, null)),
                            R.id.fragmentComplete,
                            TAG_ADD_POST
                        )
                }
                R.id.nav_profile -> {
                    if (supportFragmentManager.findFragmentByTag(TAG_PROFILE) == null)
                        replaceFragment(
                            ProfileFragment.newInstance(),
                            R.id.fragmentComplete,
                            TAG_PROFILE
                        )
                }
                R.id.nav_settings -> {
                    if (supportFragmentManager.findFragmentByTag(TAG_SETTINGS) == null)
                        replaceFragment(
                            SettingsFragment.newInstance(),
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

    private fun closeSession() {
        changeTopIcon(false)
        val sharedEdit = sharedPreferences.edit()
        sharedEdit.remove(EMAIL)
        sharedEdit.apply()
    }

    private fun showCloseSessionAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("")
            .setMessage(R.string.logout_message)
            .setPositiveButton(getString(R.string.accept)) { _, _ ->
                closeSession()
                setBottomOptions()
                navigateToHome()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    fun navigateToHome() {
        if (supportFragmentManager.findFragmentByTag(TAG_HOME) == null)
            replaceFragment(
                HomeFragment.newInstance(sharedPreferences.getString(EMAIL, null)),
                R.id.fragmentComplete,
                TAG_HOME
            )
        bottom_navigation.selectedItemId = R.id.nav_home
    }
}