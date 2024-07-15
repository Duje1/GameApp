package com.duje.gameapp.data.repository

import androidx.lifecycle.LiveData
import com.duje.gameapp.data.local.GenreDao
import com.duje.gameapp.domain.Genre

class GenreRepository(private val genreDao: GenreDao) {

    val allGenres: LiveData<List<Genre>> = genreDao.getAllGenres()

    suspend fun insert(genre: Genre) {
        genreDao.insert(genre)
    }

    suspend fun update(genre: Genre) {
        genreDao.update(genre)
    }

    suspend fun delete(genre: Genre) {
        genreDao.delete(genre)
    }
}
