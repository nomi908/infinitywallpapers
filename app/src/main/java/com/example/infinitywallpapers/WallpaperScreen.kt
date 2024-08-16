package com.example.infinitywallpapers

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import java.io.File

class WallpaperScreen : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 1
    private var downloadId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper_screen)
        val wpimgshow = findViewById<ImageView>(R.id.wallpaper_img_view)
        val wpdnbtn = findViewById<Button>(R.id.downloadbutton)
        enableEdgeToEdge()

        val imageUrl = intent.getStringExtra("imageurl")
        if (imageUrl.isNullOrEmpty()) {
            Log.e("WallpaperScreen", "Image URL is null or empty")
            Toast.makeText(this, "Image URL is not valid", Toast.LENGTH_SHORT).show()
            return
        }

        Glide.with(this).load(imageUrl).into(wpimgshow)
        Log.e("Imagecoming", "onCreate: imageUrl $imageUrl")

        wpdnbtn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // For Android 10 and above, download without requiring WRITE_EXTERNAL_STORAGE
                downloadImage(imageUrl)
            } else {

                // For Android 9 and below, request WRITE_EXTERNAL_STORAGE permission
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)

                } else {
                    downloadImage(imageUrl)
                }
            }
        }

        // Register receiver to listen for download completion
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun downloadImage(imageUrl: String) {
        val uri = Uri.parse(imageUrl)
        if (uri.scheme != "http" && uri.scheme != "https") {
            Log.e("WallpaperScreen", "Invalid URI scheme: ${uri.scheme}")
            Toast.makeText(this, "Invalid URL scheme", Toast.LENGTH_SHORT).show()
            return
        }

        val request = DownloadManager.Request(uri)
            .setTitle("Downloading Image")
            .setDescription("Downloading image from $imageUrl")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, uri.lastPathSegment)

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadId = downloadManager.enqueue(request)


        Log.i("WallpaperScreen", "Download started with ID: $downloadId")
        Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show()
    }

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == downloadId) {
                Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                val imageUrl = intent.getStringExtra("imageurl")
                if (!imageUrl.isNullOrEmpty()) {
                    downloadImage(imageUrl)
                } else {
                    Log.e("WallpaperScreen", "Image URL is null or empty")
                    Toast.makeText(this, "Image URL is not valid", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onDownloadComplete)
    }
}
