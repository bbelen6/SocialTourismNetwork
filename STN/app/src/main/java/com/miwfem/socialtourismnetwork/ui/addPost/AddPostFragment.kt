package com.miwfem.socialtourismnetwork.ui.addPost

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.data.api.ComunidadMadridServer
import com.miwfem.socialtourismnetwork.databinding.FragmentAddPostBinding
import com.miwfem.socialtourismnetwork.ui.base.BaseFragment
import com.miwfem.socialtourismnetwork.utils.COMUNIDAD_MADRID_BASE_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddPostFragment : BaseFragment(R.layout.fragment_add_post) {

    private lateinit var addPostBinding: FragmentAddPostBinding
    private var user: String? = null
    private val categories = listOf("Restaurantes", "Eventos", "Cultura", "Otros")
    private val locations = listOf("Madrid", "Getafe", "Leganes", "Alcalá")

    override fun setUpDataBinding(view: View) {
        addPostBinding = FragmentAddPostBinding.bind(view).apply {
            categorySelector.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
            )
            locationSelector.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                locations
            )
            locationSelector.setTitle(getString(R.string.location))
            savePostButton.setOnClickListener {
                //TODO: SAVE POST IN FIREBASE
                if (addPostEdit.text.isEmpty()) addPostEdit.error =
                    getString(R.string.add_post_error)
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            getLocations()
        }
    }

    override fun getBundleExtras() {
        arguments?.let {
            user = it.getString(USER)
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(COMUNIDAD_MADRID_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private suspend fun getLocations() {
        val call = getRetrofit().create(ComunidadMadridServer::class.java).getLocations()
        val locations = call.body()
        if (call.isSuccessful) {
            Log.d("LOCATIONS", locations.toString())
        } else {
            Log.d("LOCATIONS", "ERROR")
        }
    }

    companion object {
        fun newInstance(user: String?) = AddPostFragment().apply {
            arguments = Bundle().apply {
                putString(USER, user)
            }
        }

        private const val USER = "user"
    }

}