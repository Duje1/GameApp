package com.duje.gameapp.presentation.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.duje.gameapp.R
import com.duje.gameapp.data.remote.api.RetrofitInstance
import com.duje.gameapp.data.remote.responses.GameCategory
import com.duje.gameapp.presentation.adapter.GenresRecyclerViewAdapter
import com.duje.gameapp.data.model.GenreRecyclerViewItemModel
import com.duje.gameapp.data.viewModel.GenreViewModel
import com.duje.gameapp.data.viewModel.OnItemChangedListener
import com.duje.gameapp.domain.Genre
import com.duje.gameapp.utils.YOUR_API
import com.duje.gameapp.utils.loadedGames
import com.duje.gameapp.utils.pageNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OnboardingFragment : Fragment(), OnItemChangedListener {

    private lateinit var genresAdapter: GenresRecyclerViewAdapter
    private lateinit var rvGenres: RecyclerView
    private lateinit var continueButton: Button
    private lateinit var spinner: ProgressBar
    private lateinit var genreViewModel: GenreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding_fragment, container, false)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}
        spinner = view.findViewById(R.id.progressBar)
        continueButton = view.findViewById(R.id.btnContinue)
        rvGenres = view.findViewById(R.id.rvGenres)
        rvGenres.layoutManager = GridLayoutManager(requireContext(), 3)

        genreViewModel = ViewModelProvider(this@OnboardingFragment).get(GenreViewModel::class.java)

        fetchGenresAndUpdateUI()

        continueButton.setOnClickListener {
            removeUnselectedGenresFromViewModel()
            saveSelectedGenres()
            loadedGames.clear()
            pageNumber = 1
            Navigation.findNavController(view).navigate(R.id.navigate_to_games)
        }


        return view
    }

    // Populating RecyclerView with the data that is fetched from api
    private fun fetchGenresAndUpdateUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            // Loading data from remote database
            val fetchedGenres = fetchGenres(YOUR_API)
            try {
                // Observe saved genres
                getSavedGenres().observe(viewLifecycleOwner, Observer { savedGenres ->
                    savedGenres?.let {

                        Log.d("FetchGenres", "Saved Genres: $savedGenres")

                        if (savedGenres.isNotEmpty())
                            enableContinueButton(true)

                        val recyclerViewItems =
                            fetchedGenres?.map { mapGameCategoryToRecyclerViewItemModel(it) }
                        genresAdapter = recyclerViewItems?.let { GenresRecyclerViewAdapter(it) }
                            ?: return@Observer
                        rvGenres.adapter = genresAdapter

                        // Update UI based on saved genres
                        updateAdapterWithSavedGenres(savedGenres)

                        // Set listener for item changes
                        genresAdapter.setOnItemChangedListener(this@OnboardingFragment)
                        spinner.visibility = View.GONE
                    }
                })
            } catch (e: Exception) {
                Log.e("FetchGenres", "Error fetching genres: ${e.message}", e)
            }
        }
    }

    // Getting all the genres from RAWG.io api
    private suspend fun fetchGenres(apiKey: String): List<GameCategory>? {
        return withContext(Dispatchers.IO) {
            try {
                val genresResponse = RetrofitInstance.api.getGenres(apiKey)
                genresResponse.results
            } catch (e: Exception) {
                null
            }
        }
    }

    // Checking if items from RecyclerView are already saved in room db, and updating it's status
    private fun updateAdapterWithSavedGenres(savedGenres: List<Genre>) {
        for (i in 0 until genresAdapter.itemCount) {
            val adapterItem = genresAdapter.getItem(i)

            // Check if adapterItem id exists in savedGenres
            val savedGenre = savedGenres.find { it.id == adapterItem.id }

            if (savedGenre != null) {
                adapterItem.isPressed = true
                genresAdapter.notifyItemChanged(i)
            }
        }
    }

    // Loading data from remote db to our RecyclerView items so it can be displayed
    private fun mapGameCategoryToRecyclerViewItemModel(gameCategory: GameCategory): GenreRecyclerViewItemModel {
        return GenreRecyclerViewItemModel(
            id = gameCategory.id,
            name = gameCategory.name,
            isPressed = false
        )
    }

    // This method is responsible for every user input directed to RecyclerView items
    override fun onItemChanged(position: Int) {

        var activeItems = false
        var numberOfActiveItems = 0
        // Checking if there are pressed items in recycler view
        for (i in 0 until genresAdapter.itemCount) {
            if (genresAdapter.getItem(i).isPressed) {
                numberOfActiveItems++
                if (numberOfActiveItems >= 2) {
                    activeItems = true
                    break
                }
            }
        }

        enableContinueButton(activeItems)
    }

    private fun enableContinueButton(activeItems: Boolean) {
        if (activeItems) {
            continueButton.isEnabled = true
            continueButton.setBackgroundColor(Color.rgb(50, 145, 168))
            continueButton.setTextColor(Color.BLACK)
        } else {
            continueButton.isEnabled = false
            continueButton.setBackgroundColor(Color.rgb(119, 141, 169))
        }
    }

    // Saving selected items (genres) to local db
    private fun saveSelectedGenres() {
        val selectedItems = genresAdapter.getSelectedItems()
        for (item in selectedItems) {
            val genre = Genre(id = item.id, name = item.name)
            genreViewModel.insert(genre)
        }
    }

    // Remove any genres from the ViewModel that were previously saved but are no longer selected in the RecyclerView.
    private fun removeUnselectedGenresFromViewModel() {
        val selectedItems = genresAdapter.getSelectedItems() // Get currently selected items

        // Get saved genres from ViewModel
        val savedGenres = genreViewModel.allGenres.value ?: emptyList()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                for (genre in savedGenres) {

                    // Check if the saved genre is not in the currently selected items inside RecyclerView
                    if (!selectedItems.any { it.id == genre.id }) {
                        genreViewModel.delete(genre)
                    }
                }
            } catch (e: Exception) {
                Log.e("CheckAndRemove", "Error checking and removing genres: ${e.message}", e)
            }
        }
    }

    private fun getSavedGenres(): LiveData<List<Genre>> {
        return genreViewModel.allGenres
    }

}
