package com.example.sqlactivtiy

package com.example.uielementspart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uielementspart2.models.Album
import com.example.uielementspart2.models.Song

class EditAlbumActivity : AppCompatActivity() {
    lateinit var editAlbumButton: Button
    lateinit var editAlbumTitleEt: EditText
    lateinit var editAlbumDate: EditText
    lateinit var album: Album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_album)

        val album_id = intent.getIntExtra("album_id", 0)

        val databaseHandler = AlbumTableHandler(this)
        album = databaseHandler.readOne(album_id)

        editAlbumTitleEt = findViewById(R.id.editAlbumTitleEt)
        editAlbumDate = findViewById(R.id.editAlbumDate)
        editAlbumButton = findViewById(R.id.editAlbumButton)

        editAlbumTitleEt.setText(album.title)
        editAlbumDate.setText(album.date)

        editAlbumButton.setOnClickListener{
            val title = editAlbumTitleEt.text.toString()
            val date = editAlbumDate.text.toString()

            val updatedAlbum = Album(id = album.id, title = title, date = date)

            if(databaseHandler.update(updatedAlbum)){
                Toast.makeText(applicationContext, "Album updated", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, AlbumsActivity::class.java))
            } else {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}