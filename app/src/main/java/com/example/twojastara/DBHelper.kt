package com.example.twojastara

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE Events (id INTEGER PRIMARY KEY, name TEXT, description TEXT, date DATE, time TIME);")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS Events")
        onCreate(db)
    }

    fun sql(query: String) {
        val db = this.writableDatabase
        db.execSQL(query)
        db.close()
    }

    fun query(query: String): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery(query, null)
    }

    companion object{
        private const val DATABASE_NAME = "TwojaStara"
        private const val DATABASE_VERSION = 1
    }
}