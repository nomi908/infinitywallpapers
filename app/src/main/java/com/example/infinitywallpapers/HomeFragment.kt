package com.example.infinitywallpapers

import Adapters.BomAdapter
import Adapters.Cat_Adapter
import Adapters.Tct_Adapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import Model.Bom_Model
import Model.Categories_Model
import Model.tct_model
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.infinitywallpapers.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()

        fetchBestOfMonths()
        fetchTheColorTone()
        fetchCategories()

        return binding.root
    }

    private fun fetchBestOfMonths() {
        db.collection("bestofmonths").addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("HomeFragment", "Error fetching bestofmonths: ${error.localizedMessage}")
                return@addSnapshotListener
            }

            val listBestOfMonths = arrayListOf<Bom_Model>()
            val data = value?.toObjects(Bom_Model::class.java)
            data?.let {
                listBestOfMonths.addAll(it)
            }
            Log.d("HomeFragment", "Best of months data: $listBestOfMonths")

            binding.rcvBom.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rcvBom.adapter = BomAdapter(requireContext(), listBestOfMonths)

        }
    }

    private fun fetchTheColorTone() {
        db.collection("thecolortone").addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("HomeFragment", "Error fetching thecolortone: ${error.localizedMessage}")
                return@addSnapshotListener
            }

            val theColorTone = arrayListOf<tct_model>()
            val data = value?.toObjects(tct_model::class.java)
            data?.let {
                theColorTone.addAll(it)
            }
            Log.d("HomeFragment", "The color tone data: $theColorTone")

            binding.rcvTct.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rcvTct.adapter = Tct_Adapter(requireContext(), theColorTone)
        }
    }

    private fun fetchCategories() {
        db.collection("categories").addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("HomeFragment", "Error fetching categories: ${error.localizedMessage}")
                return@addSnapshotListener
            }

            val categoriesData = arrayListOf<Categories_Model>()
            val data = value?.toObjects(Categories_Model::class.java)
            data?.let {
                categoriesData.addAll(it)
            }
            Log.d("HomeFragment", "Categories data: $categoriesData")

            binding.rcvCat.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.rcvCat.adapter = Cat_Adapter(requireContext(), categoriesData)
        }
    }
}
