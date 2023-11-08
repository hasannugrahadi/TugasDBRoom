package com.app.tugaskelompok.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: Favorite)

    @Query("SELECT * FROM Favorite WHERE login = :login")
    fun getUserByLogin(login: String): Favorite?

    @Query("DELETE FROM Favorite WHERE login = :login")
    suspend fun deleteUserByLogin(login: String)

    @Query("SELECT * FROM Favorite")
    fun getAllUsers(): LiveData<List<Favorite>>

}