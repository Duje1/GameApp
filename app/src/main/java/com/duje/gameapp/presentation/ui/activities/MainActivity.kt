package com.duje.gameapp.presentation.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.duje.gameapp.R
import com.duje.gameapp.data.local.GenreRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var database: GenreRoomDatabase
    private var initialFragmentId = R.id.onboarding_fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        database = GenreRoomDatabase.getDatabase(this)
        // Check if database is empty
        lifecycleScope.launch {
            val isEmpty = withContext(Dispatchers.IO) {
                database.genreDao().getGenreCount() == 0
            }

            // Determine the initial fragment based on database state
            if (isEmpty) {
                initialFragmentId = R.id.onboarding_fragment
            } else {
                initialFragmentId = R.id.gamesFragment
            }

        }
    }
}