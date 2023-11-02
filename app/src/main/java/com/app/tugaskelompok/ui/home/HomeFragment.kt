package com.app.tugaskelompok.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.tugaskelompok.ui.home.utils.Result
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.tugaskelompok.R
import com.app.tugaskelompok.databinding.FragmentHomeBinding
import com.app.tugaskelompok.ui.home.model.ResponseUserGithub

class HomeFragment : Fragment() {

    private lateinit var adapter: HomeAdapter

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = HomeAdapter(mutableListOf()){item ->
            val detailFragment = HomeDetailFragment()
            val bundle = Bundle()
            bundle.putString("username", item.login)
            detailFragment.arguments = bundle

            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment_activity_main, detailFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        // Set up RecyclerView
        binding.recycleViewer.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleViewer.setHasFixedSize(true)
        binding.recycleViewer.adapter = adapter

        viewModel.resultUser.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success<*> -> {
                    adapter.setData(it.data as MutableList<ResponseUserGithub.Item>)
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }

        viewModel.getUser()

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}