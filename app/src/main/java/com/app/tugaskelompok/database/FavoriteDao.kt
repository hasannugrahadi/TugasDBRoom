package com.app.tugaskelompok.database

import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: Favorite)

    @Query("SELECT * FROM Favorite")
    suspend fun getAllUsers(): List<Favorite>

}