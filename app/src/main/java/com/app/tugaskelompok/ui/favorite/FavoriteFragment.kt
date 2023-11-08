package com.app.tugaskelompok.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.app.tugaskelompok.database.FavoriteDao
import com.app.tugaskelompok.database.FavoriteDatabase
import com.app.tugaskelompok.databinding.FragmentFavoriteBinding

/**
 * A fragment representing a list of Items.
 */
class FavoriteFragment : Fragment() {

    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteDao: FavoriteDao


    private var _binding: FragmentFavoriteBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        favoriteDao = FavoriteDatabase.getInstance(requireContext()).favoriteDao()
        adapter = FavoriteAdapter(favoriteDao)
        binding.recycleViewer.adapter = adapter


        favoriteDao.getAllUsers().observe(viewLifecycleOwner, { favoriteUsers ->
            adapter.setFavoriteUsers(favoriteUsers)
        })

        return root

    }
}