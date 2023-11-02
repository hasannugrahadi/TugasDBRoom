package com.app.tugaskelompok.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.tugaskelompok.R
import com.app.tugaskelompok.database.Favorite

class FavoriteAdapter(var userList: List<Favorite>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImageView: ImageView = itemView.findViewById(R.id.profileImage)
        val usernameTextView: TextView = itemView.findViewById(R.id.userName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.usernameTextView.text = user.username
        // Load and display the avatar using your preferred image loading library.
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
