package com.annisa.datamahasiswa

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MahasiswaDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mahasiswa.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME ="mahasiswa"
        private const val COLUMN_ID = "id"
        private  const val COLUMN_NAMA ="nama"
        private  const val COLUMN_NIM ="nim"
        private  const val COLUMN_JURUSAN ="jurusan"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAMA TEXT,
                $COLUMN_NIM TEXT,
                 $COLUMN_JURUSAN TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertMahasiswa(mhs : Mahasiswa) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAMA, mhs.nama)
            put(COLUMN_NIM, mhs.nim)
            put(COLUMN_JURUSAN, mhs.jurusan)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllMahasiswa(): List<Mahasiswa>{
        val mhs = mutableListOf<Mahasiswa>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
            val nim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM))
            val jurusan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JURUSAN))
            mhs.add(Mahasiswa(id, nama, nim, jurusan))
        }
        cursor.close()
        db.close()
        return mhs

    }

    fun deleteMahasiswa(mhsId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(mhsId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun updateMahasiswa(mhs: Mahasiswa){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAMA, mhs.nama)
            put(COLUMN_NIM, mhs.nim)
            put(COLUMN_JURUSAN, mhs.jurusan)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(mhs.id.toString())
        db.update(TABLE_NAME, values, whereClause,whereArgs)
        db.close()
    }
    fun getMahasiswaById(mhsId: Int): Mahasiswa{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $mhsId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
        val nim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM))
        val jurusan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JURUSAN))

        cursor.close()
        db.close()
        return Mahasiswa(id, nama, nim, jurusan)
    }

}