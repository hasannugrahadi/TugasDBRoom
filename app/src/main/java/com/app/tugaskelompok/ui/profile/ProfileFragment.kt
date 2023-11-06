package com.app.tugaskelompok.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.app.tugaskelompok.LoginActivity
import com.app.tugaskelompok.PreferenceDataStore
import com.app.tugaskelompok.databinding.FragmentProfileBinding
import com.app.tugaskelompok.model.ResponseUserDetail
import com.app.tugaskelompok.network.ApiConfig
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("users")
        val preferenceDataStore = PreferenceDataStore(requireContext())

        val dataID = preferenceDataStore.getValue2()
        if(dataID != null) {
            val githubUsernameReference = reference.child(dataID).child("usergithub")
            githubUsernameReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val githubUsername = dataSnapshot.getValue(String::class.java)
                    if (githubUsername != null) {
                        getUser(githubUsername)
                    } else {
                        // The user does not have a GitHub username stored in the database
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(activity, "error database", Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.logoutButton.setOnClickListener {
            preferenceDataStore.eraseValues()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        return root
    }

    private fun getUser(usergit : String) {
        val client = ApiConfig.getApiService().getUserDetails(usergit)
        client.enqueue(object : Callback<ResponseUserDetail> {
            override fun onResponse(call: Call<ResponseUserDetail>, response: Response<ResponseUserDetail>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()
                    if (dataArray != null) {
                        binding.apply {
                            profileImage.load(dataArray.avatarUrl) {
                                transformations(CircleCropTransformation())
                            }
                            profileRealname.text = dataArray.name
                            profileBio.text = dataArray.bio
                            profileUsergithub.text = dataArray.login
                            profileCompany.text = dataArray.company
                            profileLocation.text = dataArray.location
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUserDetail>, t: Throwable) {
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