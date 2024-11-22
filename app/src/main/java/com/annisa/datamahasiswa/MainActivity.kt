package com.annisa.datamahasiswa

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var btnHome: Button
    private val handler = Handler(Looper.getMainLooper()) // Inisialisasi handler
    private val navigateToListPage = Runnable {
        val intent = Intent(this, listMahasiswaActivity::class.java)
        startActivity(intent)
        finish() // Hanya jika ingin menutup MainActivity
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnHome = findViewById(R.id.btnHome)
        btnHome.setOnClickListener {
            handler.removeCallbacks(navigateToListPage) // Batalkan Handler jika tombol ditekan
            val intent = Intent(this, listMahasiswaActivity::class.java)
            startActivity(intent)
        }

        // Jalankan Handler dengan delay 3 detik
        handler.postDelayed(navigateToListPage, 1000)

        // Atur insets untuk layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(navigateToListPage) // Pastikan handler dibatalkan saat aktivitas dihancurkan
    }
}