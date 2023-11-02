package com.app.tugaskelompok.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.app.tugaskelompok.databinding.FragmentHomeDetailBinding
import com.app.tugaskelompok.ui.home.model.ResponseDetailUser
import com.app.tugaskelompok.ui.home.utils.Result

class HomeDetailFragment : Fragment() {
    private lateinit var binding: FragmentHomeDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeDetailBinding.inflate(inflater, container, false)

        val username = requireArguments().getString("username") ?: ""

        viewModel.resultDetailUser.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success<*> -> {
                    val user = it.data as ResponseDetailUser
                    binding.profileImage.load(user.avatar_url) {
                        transformations(CircleCropTransformation())
                    }
                    binding.nameTextView.text = user.name
                    binding.blogTextView.text = user.blog
                    binding.companyTextView.text = user.blog
                    binding.locationTextView.text = user.blog
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
        viewModel.getDetailUser(username)

        return binding.root
    }
}