package com.duje.gameapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.duje.gameapp.domain.Genre

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(genre: Genre)

    @Query("DELETE FROM genre_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM genre_table ORDER BY name ASC")
    fun getAllGenres(): LiveData<List<Genre>>

    @Query("SELECT COUNT(*) FROM genre_table")
    suspend fun getGenreCount(): Int

    @Update
    suspend fun update(genre: Genre)

    @Delete
    suspend fun delete(genre: Genre)
}
