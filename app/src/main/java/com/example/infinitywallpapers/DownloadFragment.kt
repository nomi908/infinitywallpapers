package com.example.infinitywallpapers

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.infinitywallpapers.Adapters.DownloadsAdapter
import com.example.infinitywallpapers.databinding.FragmentDownloadBinding


class DownloadFragment : Fragment() {
    private lateinit var binding: FragmentDownloadBinding
    private lateinit var dwncount: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDownloadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermissions()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
            fetchDownloadImages()
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchDownloadImages()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchDownloadImages() {
        // Check if the permission is granted
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {

            // Define the directory to fetch images from
            val picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

            // Check if the directory exists
            if (picturesDirectory.exists()) {
                // Get a list of all files in the directory
                val imageFiles = picturesDirectory.listFiles()?.filter { it.isFile && it.extension in listOf("jpg", "jpeg", "png") }
                val dwncount = binding.dwncount
                dwncount.text = "${imageFiles?.size} images foundmm"
                // Check if there are image files
                if (imageFiles != null && imageFiles.isNotEmpty()) {
                    // Pass the image file paths to the adapter
                    val imagePaths = imageFiles.map { it.absolutePath }
                    displayImages(imagePaths)
                } else {
                    // Handle case where no images are found
                    Toast.makeText(requireContext(), "No images found", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Handle case where directory doesn't exist
                Toast.makeText(requireContext(), "Directory does not exist", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Request permissions if not granted
            checkPermissions()
        }
    }

    private fun displayImages(imagePaths: List<String>) {
        // Initialize the adapter with image paths
        val adapter = DownloadsAdapter(imagePaths)
        // Set the adapter to RecyclerView
        binding.rcvDwnimg.adapter = adapter
        binding.rcvDwnimg.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }
}
