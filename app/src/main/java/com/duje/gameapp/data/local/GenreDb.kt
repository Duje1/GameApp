package com.duje.gameapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.duje.gameapp.domain.Genre

@Database(entities = [Genre::class], version = 1, exportSchema = false)
abstract class GenreRoomDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao

    companion object {
        @Volatile
        private var INSTANCE: GenreRoomDatabase? = null

        fun getDatabase(context: Context): GenreRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GenreRoomDatabase::class.java,
                    "genre_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
