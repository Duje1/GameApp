package com.duje.gameapp.presentation.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.duje.gameapp.R
import com.duje.gameapp.data.remote.api.RetrofitInstance
import com.duje.gameapp.data.remote.responses.Game
import com.duje.gameapp.data.remote.responses.GameCategory
import com.duje.gameapp.data.remote.responses.GenresResponse
import com.duje.gameapp.presentation.adapter.GenresRecyclerViewAdapter
import com.duje.gameapp.presentation.adapter.RecyclerViewItemModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [onboarding_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class onboarding_fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var genresAdapter: GenresRecyclerViewAdapter
    private lateinit var rvGenres: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_onboarding_fragment, container, false)

        rvGenres = view.findViewById(R.id.rvGenres)
        rvGenres.setLayoutManager(GridLayoutManager(context, 3))


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchGenresAndUpdateUI()
    }

    private fun fetchGenresAndUpdateUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val fetchedGenres = fetchGenres("api")
                val recyclerViewItems = fetchedGenres?.map { mapGameCategoryToRecyclerViewItemModel(it) }
                genresAdapter = recyclerViewItems?.let { GenresRecyclerViewAdapter(it) }!!
                rvGenres.adapter = genresAdapter
            } catch (e: Exception) {
                Toast.makeText(context, "Error fetching genres: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.d("resda", e.message.toString())
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment onboarding_fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            onboarding_fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
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


}