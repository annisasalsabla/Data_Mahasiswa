package com.annisa.datamahasiswa

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.annisa.datamahasiswa.databinding.ActivityUpdateMahasiswaBinding


class UpdateMahasiswaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateMahasiswaBinding
    private lateinit var db : MahasiswaDatabaseHelper
    private var mhsId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateMahasiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MahasiswaDatabaseHelper(this)

        mhsId = intent.getIntExtra("mhs2_id", -1)
        if(mhsId == -1){
            finish()
            return
        }

        val mhs = db.getMahasiswaById(mhsId)
        binding.updateNama.setText(mhs.nama)
        binding.updateNim.setText(mhs.nim)
        binding.updateJurusan.setText(mhs.jurusan)

        binding.btnUpdate.setOnClickListener{
            val newNama = binding.updateNama.text.toString()
            val newNim = binding.updateNim.text.toString()
            val newJurusan = binding.updateJurusan.text.toString()

            val updateMahasiswa = Mahasiswa(mhsId, newNama, newNim, newJurusan)
            db.updateMahasiswa(updateMahasiswa)

            Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}