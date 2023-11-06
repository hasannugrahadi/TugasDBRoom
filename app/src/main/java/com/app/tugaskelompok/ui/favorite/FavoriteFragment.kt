package com.app.tugaskelompok.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.app.tugaskelompok.R
import com.app.tugaskelompok.database.Favorite
import com.app.tugaskelompok.database.FavoriteRoomDatabase
import com.app.tugaskelompok.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * A fragment representing a list of Items.
 */
class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteAdapter
    private lateinit var db: FavoriteRoomDatabase

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

//        recyclerView = requireView().findViewById(R.id.recycleViewer)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        adapter = FavoriteAdapter(emptyList())
//        recyclerView.adapter = adapter
//
//        db = Room.databaseBuilder(requireContext(), FavoriteRoomDatabase::class.java, "favorite_db").build()
//
//        val userList = loadFavoriteData()
//        adapter.userList = userList
//        adapter.notifyDataSetChanged()
//

        return root

    }

    private fun loadFavoriteData(): List<Favorite> {
        return runBlocking {
            return@runBlocking withContext(Dispatchers.IO) {
                db.FavoriteDao().getAllUsers()
            }
        }
    }
}