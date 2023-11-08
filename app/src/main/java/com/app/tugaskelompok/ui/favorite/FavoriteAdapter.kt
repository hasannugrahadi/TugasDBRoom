package com.app.tugaskelompok.ui.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.tugaskelompok.R
import com.app.tugaskelompok.database.Favorite
import com.app.tugaskelompok.database.FavoriteDao
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteAdapter(private val favoriteDao: FavoriteDao) :
    RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    private var favoriteUsers: List<Favorite> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return ListViewHolder(view)
    }

    fun setFavoriteUsers(users: List<Favorite>) {
        favoriteUsers = users
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = favoriteUsers.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = favoriteUsers[position]
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .apply(RequestOptions().override(80, 80).placeholder(R.drawable.icon_avatar))
            .transform(CircleCrop())
            .into(holder.profileImage)

        holder.profileUsername.text = user.login
        holder.favTrash.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                user.login?.let { it1 -> deleteUser(it1) }
            }
        }
    }
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profileUsername: TextView = itemView.findViewById(R.id.fav_username)
        var profileImage: ImageView = itemView.findViewById(R.id.fav_pp)
        var favTrash: ImageButton = itemView.findViewById(R.id.fav_trash)
    }

    private suspend fun deleteUser(login: String) {
        favoriteDao.deleteUserByLogin(login)
    }
}
