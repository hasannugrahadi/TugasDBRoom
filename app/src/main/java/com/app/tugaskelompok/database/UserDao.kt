package com.app.tugaskelompok.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM User WHERE user_app = :username AND password = :password")
    suspend fun getUser(username: String, password: String): User?

    @Query("SELECT email FROM User WHERE user_app = :username")
    suspend fun getEmail(username: String): String

    @Query("SELECT * FROM User WHERE user_app = :username")
    suspend fun getUserByUsername(username: String): User?

}