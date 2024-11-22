package com.annisa.datamahasiswa

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.annisa.datamahasiswa.databinding.ActivityAddMahasiswaBinding


class AddMahasiswaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMahasiswaBinding
    private lateinit var db: MahasiswaDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MahasiswaDatabaseHelper(this)

        binding.btnSimpan.setOnClickListener(){
            val nama = binding.addNama.text.toString()
            val nim = binding.addNim.text.toString()
            val jurusan = binding.addJurusan.text.toString()

            if(nama.isNotBlank() && nim.isNotBlank() && jurusan.isNotBlank()){
                val mhs = Mahasiswa(0,nama, nim, jurusan)
                db.insertMahasiswa(mhs)
                Toast.makeText(this,"Data Disimpan", Toast.LENGTH_SHORT).show()
                finish()
            }
            else {
                Toast.makeText(this, "Tolong isi semua data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}