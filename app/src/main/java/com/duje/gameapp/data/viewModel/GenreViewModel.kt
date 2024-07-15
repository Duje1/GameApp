package com.duje.gameapp.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.duje.gameapp.data.local.GenreRoomDatabase
import com.duje.gameapp.domain.Genre
import com.duje.gameapp.data.repository.GenreRepository
import kotlinx.coroutines.launch

class GenreViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GenreRepository
    val allGenres: LiveData<List<Genre>>

    init {
        val genreDao = GenreRoomDatabase.getDatabase(application).genreDao()
        repository = GenreRepository(genreDao)
        allGenres = repository.allGenres
    }

    fun insert(genre: Genre) = viewModelScope.launch {
        repository.insert(genre)
    }

    fun update(genre: Genre) = viewModelScope.launch {
        repository.update(genre)
    }

    fun delete(genre: Genre) = viewModelScope.launch {
        repository.delete(genre)
    }

}
