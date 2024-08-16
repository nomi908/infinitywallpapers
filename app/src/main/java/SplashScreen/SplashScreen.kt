package SplashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.infinitywallpapers.MainActivity
import com.example.infinitywallpapers.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        val splashtimeout : Long = 2500

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))

            finish()
        }, splashtimeout)



    }
}