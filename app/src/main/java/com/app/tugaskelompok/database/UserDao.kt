package com.app.tugaskelompok.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: User)

    @Update
    fun update(note: User)

    @Delete
    fun delete(note: User)
    
}