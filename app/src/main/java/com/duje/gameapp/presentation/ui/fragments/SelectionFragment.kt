package com.duje.gameapp.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.duje.gameapp.R
import com.duje.gameapp.data.viewModel.GenreViewModel
import com.duje.gameapp.domain.Genre
import com.duje.gameapp.utils.firstLogin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SelectionFragment : Fragment() {

    private lateinit var genreViewModel: GenreViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the ViewModel
        genreViewModel = ViewModelProvider(this@SelectionFragment).get(GenreViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}

        navController = findNavController()

        try {
            // Observe saved genres
            getSavedGenres(genreViewModel).observe(viewLifecycleOwner) { savedGenres ->
                savedGenres?.let {
                    Log.d("FetchGenres", "Saved Genres: $savedGenres")
                    lifecycleScope.launch {
                        delay(2000) // Delay for 2 seconds

                        if (savedGenres.isNotEmpty()) {
                            Log.d("FetchGenres", "Not empty")
                            firstLogin = false
                            navController.navigate(R.id.gamesFragment)
                        } else {
                            Log.d("FetchGenres", "Empty")
                            navController.navigate(R.id.onboarding_fragment)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("FetchGenres", "Error fetching genres: ${e.message}", e)
        }
    }

    fun getSavedGenres(genreViewModel: GenreViewModel): LiveData<List<Genre>> {
        return genreViewModel.allGenres
    }
}
