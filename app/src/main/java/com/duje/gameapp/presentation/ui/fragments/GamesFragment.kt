package com.duje.gameapp.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.duje.gameapp.R
import com.duje.gameapp.data.model.GameRecyclerViewItemModel
import com.duje.gameapp.data.remote.api.RetrofitInstance
import com.duje.gameapp.data.remote.responses.Games
import com.duje.gameapp.data.remote.responses.GamesResponseModel
import com.duje.gameapp.data.viewModel.GenreViewModel
import com.duje.gameapp.data.viewModel.OnItemChangedListener
import com.duje.gameapp.domain.Genre
import com.duje.gameapp.presentation.adapter.GamesRecyclerViewAdapter
import com.duje.gameapp.utils.gameId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GamesFragment : Fragment(), OnItemChangedListener {

    private lateinit var gamesAdapter: GamesRecyclerViewAdapter
    private lateinit var settings: ImageView
    private lateinit var rvGames: RecyclerView
    private lateinit var spinner: ProgressBar
    private lateinit var genreViewModel: GenreViewModel
    private val savedGenresID: MutableList<Int> = mutableListOf()
    private var savedGenres: List<Genre>? = null

    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreViewModel = ViewModelProvider(this@GamesFragment).get(GenreViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_games, container, false)


        // Disable back button
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}

        settings = view.findViewById(R.id.ivSettingsButton)
        rvGames = view.findViewById(R.id.rvGames)
        spinner = view.findViewById(R.id.spinner)

        settings.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigate_to_onboarding)
        }

        rvGames.layoutManager = LinearLayoutManager(context)

        // Load genres and fetch games once genres are loaded
        loadSavedGenres { genres ->
            savedGenres = genres
            fetchGamesAndUpdateUI()
        }


        return view
    }

    private fun loadSavedGenres(onLoaded: (List<Genre>) -> Unit) {
        try {
            getSavedGenres(genreViewModel).observe(viewLifecycleOwner) { g ->
                g?.let {
                    Log.d("FetchGenres", "Saved Genres: $g")
                    onLoaded(g)
                }
            }
        } catch (e: Exception) {
            Log.e("FetchGenres", "Error fetching genres: ${e.message}", e)
        }
    }

    fun getSavedGenres(genreViewModel: GenreViewModel): LiveData<List<Genre>> {
        return genreViewModel.allGenres
    }

    private fun fetchGamesAndUpdateUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                savedGenres?.let { genres ->
                    for (i in genres) {
                        savedGenresID.add(i.id)
                    }
                    val genresArray: Array<Int> = savedGenresID.toTypedArray()
                    val genresString = genresArray.joinToString(",") // Converts [4, 1] to "4,1"
                    // Fetch games from remote database
                    val fetchedGames = fetchGames("YOUR_API", genresString)

                    Log.d("FetchedGames", "${fetchedGames}")

                    // Map the fetched games to RecyclerView items and set the adapter
                    fetchedGames?.let { games ->
                        val recyclerViewItems =
                            games.results.map { mapGameToGameRecyclerViewModel(it) }
                        gamesAdapter = GamesRecyclerViewAdapter(recyclerViewItems)
                        rvGames.adapter = gamesAdapter
                        gamesAdapter.setOnItemChangedListener(this@GamesFragment)
                    }

                    spinner.visibility = View.GONE
                } ?: run {
                    Log.e("FetchGames", "Saved genres not initialized")
                }
            } catch (e: Exception) {
                Log.e("FetchGames", "Error fetching games: ${e.message}", e)
                // Handle error or show appropriate message to the user
            }
        }
    }

    // Loading data from remote db to our RecyclerView items so it can be displayed
    private fun mapGameToGameRecyclerViewModel(game: Games): GameRecyclerViewItemModel {
        return GameRecyclerViewItemModel(
            gameId = game.id,
            gameName = game.name,
            gameGenre = game.genres,
            gameRating = game.rating,
            gameBackgroud = game.backgroundImage
        )
    }

    private suspend fun fetchGames(apiKey: String, genres: String): GamesResponseModel? {
        return withContext(Dispatchers.IO) {
            try {
                val gamesResponse = RetrofitInstance.api.getGame(genres, apiKey)
                Log.d("FetchGames", gamesResponse.count.toString())
                gamesResponse
            } catch (e: Exception) {
                Log.d("FetchGames", e.message.toString())
                null
            }
        }
    }

    // This method is responsible for every user input directed to RecyclerView items
    override fun onItemChanged(position: Int) {

        Navigation.findNavController(view).navigate(R.id.navigate_to_specific_game)
        val clickedGameId = gamesAdapter.getItem(position).gameId
        gameId = clickedGameId

    }


}