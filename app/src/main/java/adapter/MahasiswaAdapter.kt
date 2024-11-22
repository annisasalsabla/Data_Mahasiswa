package adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Printer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.annisa.datamahasiswa.Mahasiswa
import com.annisa.datamahasiswa.MahasiswaDatabaseHelper
import com.annisa.datamahasiswa.R
import com.annisa.datamahasiswa.UpdateMahasiswaActivity

class MahasiswaAdapter(
    private var mhs: List<Mahasiswa>,
    private var context: Context

): RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder>() {

    private  var db: MahasiswaDatabaseHelper = MahasiswaDatabaseHelper(context)

    class MahasiswaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nama: TextView = itemView.findViewById(R.id.titleMhs)
        val nim: TextView = itemView.findViewById(R.id.contentMhs)
        val jurusan: TextView = itemView.findViewById(R.id.jurusanMhs)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MahasiswaViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_liste,parent, false)
        return MahasiswaViewHolder(view)
    }

    override fun getItemCount(): Int = mhs.size

    override fun onBindViewHolder(holder: MahasiswaViewHolder, position: Int) {
        val mhs2 = mhs[position]
        holder.nama.text = mhs2.nama
        holder.nim.text = mhs2.nim
        holder.jurusan.text = mhs2.jurusan

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateMahasiswaActivity::class.java).apply {
                putExtra("mhs2_id", mhs2.id)
            }
            holder.itemView.context.startActivity(intent)

        }

        holder.deleteButton.setOnClickListener {
            // Tampilkan dialog konfirmasi
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Konfirmasi")
                setMessage("Apakah anda yakin ingin menghapus ${mhs2.nama}?")
                setIcon(R.drawable.baseline_delete_24)

                setPositiveButton("Yakin") { dialogInterface, _ ->
                    // Hapus catatan dari database
                    db.deleteMahasiswa(mhs2.id)
                    // Refresh data di adapter
                    refreshData(db.getAllMahasiswa())

                    Toast.makeText(holder.itemView.context, "Data ${mhs2.nama} berhasil dihapus", Toast.LENGTH_SHORT).show()

                    dialogInterface.dismiss()
                }

                setNegativeButton("Batal") { dialogInterface, _ ->

                    dialogInterface.dismiss()
                }
            }.show()
        }
    }



    fun refreshData(newMhs: List<Mahasiswa>) {
        mhs = newMhs
        notifyDataSetChanged()
    }
}