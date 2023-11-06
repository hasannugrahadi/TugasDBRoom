package com.app.tugaskelompok.ui.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.app.tugaskelompok.R
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tugaskelompok.model.ResponseUser
import com.app.tugaskelompok.model.ResponseUserItem
import com.bumptech.glide.Glide

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

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
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profileusername: TextView = itemView.findViewById(R.id.userName)
        var profileImage: ImageView = itemView.findViewById(R.id.profileImage)

    }
}
