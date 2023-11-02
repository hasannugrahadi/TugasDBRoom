package com.app.tugaskelompok.ui.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.app.tugaskelompok.R
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tugaskelompok.databinding.ItemHomeBinding
import com.app.tugaskelompok.ui.home.model.ResponseUserGithub
import com.bumptech.glide.Glide



class HomeAdapter(
    private val data : MutableList<ResponseUserGithub.Item> = mutableListOf(),
    private val listener : (ResponseUserGithub.Item) -> Unit
) :
    RecyclerView.Adapter<HomeAdapter.UserViewHolder>() {

    fun setData(data: MutableList<ResponseUserGithub.Item>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val v: ItemHomeBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind(item: ResponseUserGithub.Item) {
            // Menggunakan Glide untuk mengisi gambar pengguna
            Glide.with(v.profileImage)
                .load(item.avatar_url)
                .circleCrop()
                .into(v.profileImage)
            v.userName.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            listener(item)
        }
    }

    override fun getItemCount(): Int = data.size
}
