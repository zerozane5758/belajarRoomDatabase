package com.c14220203.belajarroomdatabase.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Dao
import androidx.room.Delete

@Dao
interface daftarBelanjaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(daftar: daftarBelanja)


    @Query(
        "UPDATE daftarBelanja SET tanggal = :isi_tanggal, item = :isi_item, jumlah = :isi_jumlah WHERE id = :pilihid"
    )
    fun update(isi_tanggal: String, isi_item: String, isi_jumlah: String, pilihid: Int)

    @Delete
    fun delete(daftar: daftarBelanja)

    @Query("SELECT * FROM daftarBelanja ORDER BY id ASC")
    fun selectAll(): MutableList<daftarBelanja>

    @Query("SELECT * FROM daftarBelanja WHERE id =:isi_id")
    suspend fun getItem(isi_id: Int): daftarBelanja
}
