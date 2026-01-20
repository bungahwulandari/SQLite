package com.example.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NAME TEXT,
                $COL_AGE INTEGER,
                $COL_GENDER TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // INSERT
    fun insertUser(name: String, age: Int, gender: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, name)
        values.put(COL_AGE, age)
        values.put(COL_GENDER, gender)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // READ
    fun getAllUsers(): Cursor {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    // UPDATE
    fun updateUser(id: Int, name: String, age: Int, gender: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, name)
        values.put(COL_AGE, age)
        values.put(COL_GENDER, gender)
        db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(id.toString()))
        db.close()
    }

    // DELETE
    fun deleteUser(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(id.toString()))
        db.close()
    }

    companion object {
        const val DATABASE_NAME = "crud_gender.db"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "users"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_AGE = "age"
        const val COL_GENDER = "gender"
    }
}
