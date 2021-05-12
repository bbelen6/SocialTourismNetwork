package com.miwfem.socialtourismnetwork.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.utils.EMAIL
import com.miwfem.socialtourismnetwork.utils.PROVIDER
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bundle = intent.extras
        val email = bundle?.getString(EMAIL)
        val provider = bundle?.getString(PROVIDER)
        if (email != null && provider != null)
            setUpView(email, provider)
    }

    private fun setUpView(email: String, provider: String) {
        title = "Home"

        email_text_view.text = email
        provider_text_view.text = provider

        close_session_button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}

enum class ProviderType {
    BASIC
}