package com.example.infinitywallpapers

import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.infinitywallpapers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupDefaultState()

        binding.homebtn.setOnClickListener {
            setActiveButton(binding.homebtn, binding.homeicon)
            setInactiveButton(binding.btndownload, binding.dowloadicon)
            fragmentreplace(HomeFragment())
        }

        binding.btndownload.setOnClickListener {
            setActiveButton(binding.btndownload, binding.dowloadicon)
            setInactiveButton(binding.homebtn, binding.homeicon)
            fragmentreplace(DownloadFragment())
        }
    }

    private fun fragmentreplace(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.replacefragment, fragment)
            .commit()
    }

    private fun setupDefaultState() {
        setActiveButton(binding.homebtn, binding.homeicon)
        fragmentreplace(HomeFragment())
    }

    private fun setActiveButton(button: FrameLayout, icon: ImageView) {
        // Set the active color
        icon.setColorFilter(ContextCompat.getColor(this, R.color.nav_hilight), PorterDuff.Mode.SRC_IN)
    }

    private fun setInactiveButton(button: FrameLayout, icon: ImageView) {
        // Set the inactive color
        icon.setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.SRC_IN)
    }
}
