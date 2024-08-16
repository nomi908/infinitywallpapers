package com.example.infinitywallpapers

import Adapters.CatDataAdapter
import Model.Bom_Model
import Model.Categories_Model
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.infinitywallpapers.databinding.ActivityCategoriesDataBinding
import com.google.firebase.firestore.FirebaseFirestore

class CategoriesDataActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoriesDataBinding
    lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoriesDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wpcattxt = findViewById<TextView>(R.id.cat_text_wp)
        val wpcattxtcount = findViewById<TextView>(R.id.cat_count_wp)
        val db = FirebaseFirestore.getInstance()
        val catname = intent.getStringExtra("allcatname")
        val colid = intent.getStringExtra("id")

        if (colid == null) {
            Log.e("CategoriesDataActivity", "colid is null")
            return
        }

        Log.d("dataid", "onCreate: $colid")
        wpcattxt.text = catname

        db.collection("categories").document(colid).collection("wallpapers")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("CategoriesDataActivity", "Error fetching wallpapers: ${error.message}")
                    return@addSnapshotListener
                }

                val allcatcoldata = arrayListOf<Bom_Model>()
                val data = value?.toObjects(Bom_Model::class.java)

                if (data != null) {
                    allcatcoldata.addAll(data)
                    Log.d("CategoriesDataActivity", "allcatcoldata size: ${allcatcoldata.size}")

                    wpcattxtcount.text = "${allcatcoldata.size} Wallpapers Found"

                    binding.rcvCatHolder.layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    binding.rcvCatHolder.adapter = CatDataAdapter(this, allcatcoldata)
                } else {
                    Log.e("CategoriesDataActivity", "data is null")
                }

            }



    }
}
