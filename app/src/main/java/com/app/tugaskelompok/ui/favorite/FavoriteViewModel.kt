//package com.app.tugaskelompok.ui.favorite
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import com.app.tugaskelompok.database.Favorite
//import com.app.tugaskelompok.database.FavoriteDao
//import com.app.tugaskelompok.database.FavoriteDatabase
//
//class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
//    private val favoriteDao: FavoriteDao
//
//    private val savedUsers: LiveData<List<Favorite>>
//
//    init {
//        val database = FavoriteDatabase.getDatabase(application)
//        favoriteDao = database.favoriteDao()
//        savedUsers = favoriteDao.getAllUsers()
//    }
//    fun getSavedUsersLiveData(): LiveData<List<Favorite>> {
//        return savedUsers
//    }
//}