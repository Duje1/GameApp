package com.duje.gameapp.presentation.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.duje.gameapp.R
import com.duje.gameapp.data.remote.api.RetrofitInstance
import com.duje.gameapp.data.remote.responses.GameCategory
import com.duje.gameapp.presentation.adapter.GenresRecyclerViewAdapter
import com.duje.gameapp.data.model.RecyclerViewItemModel
import com.duje.gameapp.data.viewModel.OnItemChangedListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class onboarding_fragment : Fragment(), OnItemChangedListener {

    private lateinit var genresAdapter: GenresRecyclerViewAdapter
    private lateinit var rvGenres: RecyclerView
    private lateinit var continueButton: Button
    private lateinit var spinner: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding_fragment, container, false)

        spinner = view.findViewById(R.id.progressBar)
        continueButton = view.findViewById(R.id.btnContinue)
        rvGenres = view.findViewById(R.id.rvGenres)
        rvGenres.layoutManager = GridLayoutManager(requireContext(), 3)

        continueButton.setOnClickListener {
        }

        fetchGenresAndUpdateUI()

        return view
    }

    private fun fetchGenresAndUpdateUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val fetchedGenres = fetchGenres("17e1938ed62248b19c0399eb1a1f43da")
                val recyclerViewItems = fetchedGenres?.map { mapGameCategoryToRecyclerViewItemModel(it) }
                genresAdapter = recyclerViewItems?.let { GenresRecyclerViewAdapter(it) } ?: return@launch
                rvGenres.adapter = genresAdapter
                genresAdapter.setOnItemChangedListener(this@onboarding_fragment)
                spinner.visibility = View.GONE
            } catch (e: Exception) {
                Log.e("FetchGenres", "Error fetching genres: ${e.message}", e)
                // Handle error, show toast or retry mechanism
            }
        }
    }

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

    private fun mapGameCategoryToRecyclerViewItemModel(gameCategory: GameCategory): RecyclerViewItemModel {
        return RecyclerViewItemModel(
            id = gameCategory.id,
            name = gameCategory.name,
            isPressed = false
        )
    }

    override fun onItemChanged(position: Int) {
        var isAnyItemPressed = false

        for (i in 0 until genresAdapter.itemCount) {
            if (genresAdapter.getItem(i).isPressed) {
                isAnyItemPressed = true
                break  // Exit loop early if any item is pressed
            }
        }

        if (isAnyItemPressed) {
            continueButton.isEnabled = true
            continueButton.setBackgroundColor(Color.rgb(50, 145, 168))
            continueButton.setTextColor(Color.BLACK)
        } else {
            continueButton.isEnabled = false
            continueButton.setBackgroundColor(Color.rgb(119, 141, 169))
        }
    }
}
