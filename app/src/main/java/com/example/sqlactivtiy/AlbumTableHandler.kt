package com.example.sqlactivtiy

package com.example.uielementspart2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.uielementspart2.models.Album
import com.example.uielementspart2.models.Song

class AlbumTableHandler (var context: Context):  SQLiteOpenHelper(context, AlbumTableHandler.DATABASE_NAME, null, AlbumTableHandler.DATABASE_VERSION){
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "albums_database"
        private val TABLE_NAME = "albums"
        private val COL_ID = "id"
        private val COL_TITLE = "title"
        private val COL_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE "+ AlbumTableHandler.TABLE_NAME +" ("+ AlbumTableHandler.COL_ID +" INTEGER PRIMARY KEY, "+ AlbumTableHandler.COL_TITLE +" TEXT, "+ AlbumTableHandler.COL_DATE +" TEXT)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + AlbumTableHandler.TABLE_NAME)
        onCreate(db)
    }

    fun create(album: Album): Boolean{
        val database = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(AlbumTableHandler.COL_TITLE, album.title)
        contentValues.put(AlbumTableHandler.COL_DATE, album.date)

        val result = database.insert(AlbumTableHandler.TABLE_NAME, null, contentValues)

        if(result == (0).toLong()) {
            return false
        }
        return true
    }

    fun delete(album: Album): Boolean{
        val database = this.writableDatabase
        val result = database.delete(AlbumTableHandler.TABLE_NAME, "id=${album.id}", null)

        if(result == 0) {
            return false
        }
        return true
    }

    fun update(album: Album): Boolean{
        val database = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(AlbumTableHandler.COL_TITLE, album.title)
        contentValues.put(AlbumTableHandler.COL_DATE, album.date)

        val result = database.update(AlbumTableHandler.TABLE_NAME, contentValues, "id="+album.id, null)

        if(result == 0) {
            return false
        }
        return true
    }

    fun read(): MutableList<Album> {
        val albumsList: MutableList<Album> = ArrayList()
        val query = "SELECT * FROM "+ AlbumTableHandler.TABLE_NAME
        val database = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = database.rawQuery(query, null)
        }catch (e: SQLiteException) {
            return albumsList
        }

        var id: Int
        var title: String
        var date: String
        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(AlbumTableHandler.COL_ID))
                title = cursor.getString(cursor.getColumnIndex(AlbumTableHandler.COL_TITLE))
                date = cursor.getString(cursor.getColumnIndex(AlbumTableHandler.COL_DATE))
                val album = Album(id, title, date)
                albumsList.add(album)
            }while (cursor.moveToNext())
        }
        return albumsList
    }

    fun readOne(album_id: Int): Album {
        var album = Album(0, "", "")
        val query = "SELECT * FROM ${AlbumTableHandler.TABLE_NAME} WHERE id = $album_id"
        val database = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = database.rawQuery(query, null)
        }catch (e: SQLiteException) {
            return album
        }

        var id: Int
        var title: String
        var date: String

        if(cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(AlbumTableHandler.COL_ID))
            title = cursor.getString(cursor.getColumnIndex(AlbumTableHandler.COL_TITLE))
            date = cursor.getString(cursor.getColumnIndex(AlbumTableHandler.COL_DATE))
            album = Album(id, title, date)
        }
        return album
    }
}