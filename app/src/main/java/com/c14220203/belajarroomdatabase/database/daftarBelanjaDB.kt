package com.c14220203.belajarroomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [daftarBelanja::class, historyBelanja::class], version = 2)
abstract class daftarBelanjaDB : RoomDatabase() {
    abstract fun fundaftarBelanjaDAO(): daftarBelanjaDAO
    abstract fun funhistoryBelanjaDAO(): historyBelanjaDAO

    companion object {
        @Volatile
        private var INSTANCE: daftarBelanjaDB? = null

        @JvmStatic
        fun getDatabase(context: Context): daftarBelanjaDB {
            if (INSTANCE == null) {
                synchronized(daftarBelanjaDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        daftarBelanjaDB::class.java, "daftarBelanja-db"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration() // Tambahkan ini untuk menangani perubahan versi database
                        .build()
                }
            }
            return INSTANCE as daftarBelanjaDB
        }
    }
}

