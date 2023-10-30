package com.app.tugaskelompok.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.tugaskelompok.R
import com.app.tugaskelompok.ui.home.model.DataItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions


class HomeAdapter(private val users: MutableList<DataItem>) :
    RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    private val filteredUsers: MutableList<DataItem> = mutableListOf()
    private var isFilterEmpty: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return ListViewHolder(
            view
        )
    }

    fun addUser(newUsers: DataItem) {
        users.add(newUsers)
        notifyItemInserted(users.lastIndex)
    }

    fun clear() {
        users.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = if (filteredUsers.isNotEmpty()) filteredUsers.size else users.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        if (isFilterEmpty) {
            holder.tvUserName.text = ""
            holder.tvEmail.text = ""
            holder.ivAvatar.setImageResource(R.color.white)
        } else {
            val user = if (filteredUsers.isNotEmpty()) filteredUsers[position] else users[position]

            Glide.with(holder.itemView.context)
                .load(user.avatar)
                .apply(RequestOptions().override(80, 80).placeholder(R.drawable.icon_avatar))
                .transform(CircleCrop())
                .into(holder.ivAvatar)

            holder.tvUserName.text = "${user.firstName} ${user.lastName}"
            holder.tvEmail.text = user.email
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUserName: TextView = itemView.findViewById(R.id.itemName)
        var tvEmail: TextView = itemView.findViewById(R.id.itemEmail)
        var ivAvatar: ImageView = itemView.findViewById(R.id.itemAvatar)

    }
    fun filterUsers(query: String) {
        filteredUsers.clear()
        for (user in users) {
            if (user.firstName?.toLowerCase()?.contains(query) == true ||
                user.lastName?.toLowerCase()?.contains(query) == true ||
                user.email?.toLowerCase()?.contains(query) == true
            ) {
                filteredUsers.add(user)
            }
        }
        isFilterEmpty = filteredUsers.isEmpty()

        notifyDataSetChanged()
    }

    fun resetFilter() {
        filteredUsers.clear()
        notifyDataSetChanged()
    }
}
