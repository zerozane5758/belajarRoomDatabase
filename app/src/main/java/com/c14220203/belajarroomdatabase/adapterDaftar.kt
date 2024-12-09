package com.c14220203.belajarroomdatabase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.view.ActionMode.Callback
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.c14220203.belajarroomdatabase.database.daftarBelanja
import com.c14220203.belajarroomdatabase.database.daftarBelanjaDB
import com.c14220203.belajarroomdatabase.database.historyBelanja
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class adapterDaftar(
    private val daftarBelanja: MutableList<daftarBelanja>,
    private val context: Context // Tambahkan context
) : RecyclerView.Adapter<adapterDaftar.ListViewHolder>() {

    private val DB: daftarBelanjaDB = daftarBelanjaDB.getDatabase(context) // Inisialisasi database

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(dtBelanja: daftarBelanja)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvItemBarang = itemView.findViewById<TextView>(R.id.tvItemBarang)
        var _tvJumlahBarang = itemView.findViewById<TextView>(R.id.tvJumlahBarang)
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        var _btnEdit = itemView.findViewById<TextView>(R.id.btnEdit)
        var _btnDelete = itemView.findViewById<TextView>(R.id.btnDelete)
        var _btnUpdate = itemView.findViewById<TextView>(R.id.btnUpdate) // Tambahkan tombol Update
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val daftar = daftarBelanja[position]

        holder._tvTanggal.text = daftar.tanggal
        holder._tvItemBarang.text = daftar.item
        holder._tvJumlahBarang.text = daftar.jumlah

        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahDaftar::class.java)
            intent.putExtra("id", daftar.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(daftar)
        }

        holder._btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                // Masukkan data ke historyBelanja
                val history = historyBelanja(
                    tanggal = daftar.tanggal,
                    item = daftar.item,
                    jumlah = daftar.jumlah
                )
                DB.funhistoryBelanjaDAO().insert(history)

                // Hapus data dari daftarBelanja
                DB.fundaftarBelanjaDAO().delete(daftar)

                // Perbarui data pada RecyclerView
                val updatedData = DB.fundaftarBelanjaDAO().selectAll()
                withContext(Dispatchers.Main) {
                    isiData(updatedData)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return daftarBelanja.size
    }

    fun isiData(data: List<daftarBelanja>) {
        daftarBelanja.clear()
        daftarBelanja.addAll(data)
        notifyDataSetChanged()
    }
}

