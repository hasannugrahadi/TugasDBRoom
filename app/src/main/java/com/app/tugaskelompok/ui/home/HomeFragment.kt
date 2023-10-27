package com.app.tugaskelompok.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.tugaskelompok.databinding.FragmentHomeBinding
import com.app.tugaskelompok.ui.home.model.DataItem
import com.app.tugaskelompok.ui.home.model.ResponseUser
import com.app.tugaskelompok.ui.home.network.apiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: HomeAdapter

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

        val recyclerView = binding.recycleViewer
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = HomeAdapter(mutableListOf())

        recyclerView.adapter = adapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filterUsers(newText.orEmpty())
                return true
            }
        })
        getUser()

        val searchView = binding.searchView // Inisialisasi searchView di sini

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getUser() {
        val client = apiConfig.getApiService().getListUsers("1")

        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()?.data as List<DataItem>
                    for (data in dataArray) {
                        adapter.addUser(data)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {

                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()

                t.printStackTrace()
            }


        })
    }
}