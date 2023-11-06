package com.app.tugaskelompok.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tugaskelompok.R
import com.app.tugaskelompok.databinding.FragmentHomeBinding
import com.app.tugaskelompok.model.ResponseUser
import com.app.tugaskelompok.model.ResponseUserItem
import com.app.tugaskelompok.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var adapter: HomeAdapter

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = HomeAdapter()

        binding.recycleViewer.adapter = adapter

        getUser()

        return root

    }

    private fun getUser() {
        val client = ApiConfig.getApiService().getUserGithub()

        client.enqueue(object : Callback<List<ResponseUserItem>> {
            override fun onResponse(call: Call<List<ResponseUserItem>>, response: Response<List<ResponseUserItem>>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()
                    if (dataArray != null) {
                        dataArray?.let { adapter.setUserList(it) }
                    }
                }
            }
            override fun onFailure(call: Call<List<ResponseUserItem>>, t: Throwable) {
                Toast.makeText(activity, "error client.enqueue", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}