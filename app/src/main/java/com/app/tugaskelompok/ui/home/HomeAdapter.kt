package com.app.tugaskelompok.ui.home

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.app.tugaskelompok.R
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.tugaskelompok.database.Favorite
import com.app.tugaskelompok.database.FavoriteDao
import com.app.tugaskelompok.model.ResponseUserItem
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeAdapter(private val context: Context, private val favoriteDao: FavoriteDao) : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    private var userList: List<ResponseUserItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return ListViewHolder(
            view
        )
    }

    fun setUserList(users: List<ResponseUserItem>) {
        userList = users
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = userList[position]

        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .apply(RequestOptions().override(80, 80).placeholder(R.drawable.icon_avatar))
            .transform(CircleCrop())
            .into(holder.profileImage)

        holder.profileusername.text = user.login
        holder.profileusername.setOnClickListener {
            val intent = Intent(context, HomeDetail::class.java)
            intent.putExtra("username", user.login)
            context.startActivity(intent)
        }
        holder.saveButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                // Check if the user is already saved in the database
                val isSaved = user.login?.let { it1 -> isUserSaved(it1) }

                if (isSaved == true) {
                    // User is already saved, so delete it
                    user.login?.let { it1 -> deleteUser(it1) }
                } else {
                    // User is not saved, so insert it
                    val userEntity = Favorite(login = user.login, avatarUrl = user.avatarUrl)
                    insertUser(userEntity)
                }
            }
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profileusername: TextView = itemView.findViewById(R.id.userName)
        var profileImage: ImageView = itemView.findViewById(R.id.profileImage)
        var saveButton: ImageButton = itemView.findViewById(R.id.button_fav)
    }

    private suspend fun isUserSaved(login: String): Boolean {
        val userEntity = favoriteDao.getUserByLogin(login)
        return userEntity != null
    }

    private suspend fun insertUser(userEntity: Favorite) {
        favoriteDao.insertUser(userEntity)
    }

    private suspend fun deleteUser(login: String) {
        favoriteDao.deleteUserByLogin(login)
    }

}
