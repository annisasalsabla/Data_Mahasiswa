package com.annisa.datamahasiswa

import adapter.MahasiswaAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.annisa.datamahasiswa.databinding.ActivityListMahasiswaBinding


class listMahasiswaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListMahasiswaBinding
    private lateinit var db: MahasiswaDatabaseHelper
    private lateinit var mahasiswaAdapter: MahasiswaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MahasiswaDatabaseHelper(this)
        mahasiswaAdapter = MahasiswaAdapter(emptyList(),this)

        binding.mhsRecycleView.layoutManager = LinearLayoutManager(this)
        binding.mhsRecycleView.adapter = mahasiswaAdapter

        binding.addData.setOnClickListener{
            val intent = Intent(this, AddMahasiswaActivity::class.java)
            startActivity(intent)
        }
        loadMahasiswa()
    }

    private fun loadMahasiswa() {
        val mhs = db.getAllMahasiswa()
        Log.d("listMahasiswaActivity", "Loaded ${mhs.size} mhs.")
        mahasiswaAdapter.refreshData(mhs)
    }

    override fun onResume() {
        super.onResume()
        loadMahasiswa()  // Load updated notes after adding a new one
    }


}