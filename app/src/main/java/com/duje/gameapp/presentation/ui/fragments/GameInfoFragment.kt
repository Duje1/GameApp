package com.duje.gameapp.presentation.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.duje.gameapp.R
import com.duje.gameapp.data.remote.api.RetrofitInstance
import com.duje.gameapp.data.remote.responses.GameInfoResponseModel
import com.duje.gameapp.utils.gameId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameInfoFragment : Fragment() {


    private lateinit var tvGameTitle: TextView
    private lateinit var tvGameGenre: TextView
    private lateinit var tvGameRating: TextView
    private lateinit var ivGameBackground: ImageView
    private lateinit var ivAdditionImage: ImageView
    private lateinit var tvGameDescription: TextView
    private lateinit var ivRedditLogo: ImageView

    private lateinit var redditLink: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_info, container, false)

        tvGameGenre = view.findViewById(R.id.tvGameGenre)
        tvGameTitle = view.findViewById(R.id.tvGameTitle)
        ivGameBackground = view.findViewById(R.id.ivGameBackground)
        tvGameRating = view.findViewById(R.id.tvGameRating)
        tvGameDescription = view.findViewById(R.id.tvGameDescription)
        ivAdditionImage = view.findViewById(R.id.ivGameImage)
        ivRedditLogo = view.findViewById(R.id.ivRedditLogo)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            fetchGameAndUpdateUI()
        }

        ivRedditLogo.setOnClickListener()
        {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(redditLink)
            startActivity(intent)
        }

    }

    private fun fetchGameAndUpdateUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val fetchedInfo = fetchGameDetails(gameId = gameId.toString())

                Log.d("FetchedGameInfo", "${fetchedInfo}")

                fetchedInfo.let { gameInfo ->
                    // Assuming gameInfo has a background_image property
                    Glide.with(this@GameInfoFragment)
                        .load(gameInfo.background_image)
                        .into(ivGameBackground)

                    redditLink = gameInfo.reddit_url

                    tvGameDescription.text =
                        Html.fromHtml(gameInfo.description, Html.FROM_HTML_MODE_COMPACT)
                    tvGameTitle.text = gameInfo.name
                    tvGameRating.text = "Rating: ${gameInfo.rating}"
                    tvGameGenre.text = ""
                    for (i in 0..<gameInfo.genres.size) {
                        tvGameGenre.append(gameInfo.genres[i].name + " ")
                    }
                    Glide.with(this@GameInfoFragment)
                        .load(gameInfo.background_image_additional)
                        .into(ivAdditionImage)
                }
            } catch (e: Exception) {
                Log.e("FetchGames", "Error fetching game info: ${e.message}", e)
            }
        }
    }


    suspend fun fetchGameDetails(gameId: String): GameInfoResponseModel {
        return withContext(Dispatchers.IO) {
            try {
                val apiKey = "YOUR_API"
                RetrofitInstance.api.getGameDetails(gameId, apiKey)
            } catch (e: Exception) {
                throw e
            }
        }
    }
}
