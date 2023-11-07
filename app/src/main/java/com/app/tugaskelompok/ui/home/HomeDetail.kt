package com.app.tugaskelompok.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import coil.load
import coil.transform.CircleCropTransformation
import com.app.tugaskelompok.LoginActivity
import com.app.tugaskelompok.MainActivity
import com.app.tugaskelompok.R
import com.app.tugaskelompok.model.ResponseUserDetail
import com.app.tugaskelompok.network.ApiConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_detail)

        val username = intent.getStringExtra("username")
        if (username != null) {
            getUser(username)
        }

        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this@HomeDetail, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getUser(usergit : String) {
        val client = ApiConfig.getApiService().getUserDetails(usergit)
        client.enqueue(object : Callback<ResponseUserDetail> {
            override fun onResponse(call: Call<ResponseUserDetail>, response: Response<ResponseUserDetail>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()
                    if (dataArray != null) {

                        val imgProfile = findViewById<ImageView>(R.id.detail_pp)
                        val txtName = findViewById<TextView>(R.id.detail_name)
                        val txtAddress = findViewById<TextView>(R.id.detail_address)
                        val txtFollowers = findViewById<TextView>(R.id.detail_followers)
                        val txtFollowing = findViewById<TextView>(R.id.detail_following)
                        val txtStatus = findViewById<TextView>(R.id.detail_status)
                        val txtBlog = findViewById<TextView>(R.id.detail_blog)
                        val txtTwitter = findViewById<TextView>(R.id.detail_x)
                        val txtCompany = findViewById<TextView>(R.id.detail_company)

                        Glide.with(this@HomeDetail)
                            .load(dataArray?.avatarUrl)
                            .transform(CircleCrop())
                            .into(imgProfile)
                        txtName.text = dataArray?.name
                        txtAddress.text = dataArray?.location
                        txtFollowers.text = dataArray?.followers.toString()
                        txtFollowing.text = dataArray?.following.toString()
                        txtStatus.text = dataArray?.type
                        txtBlog.text = dataArray?.blog
                        txtTwitter.text = "twitter.com/"+dataArray?.twitterUsername.toString()
                        txtCompany.text = dataArray?.company
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUserDetail>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}