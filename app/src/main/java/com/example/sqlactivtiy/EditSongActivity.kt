package com.example.sqlactivtiy

package com.example.uielementspart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uielementspart2.models.Song

class EditSongActivity : AppCompatActivity() {
    lateinit var editSongButton: Button
    lateinit var editTitleEt: EditText
    lateinit var editArtistEt: EditText
    lateinit var editAlbumEt: EditText
    lateinit var song: Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_song)

        val song_id = intent.getIntExtra("song_id", 0)

        val databaseHandler = SongsTableHandler(this)
        song = databaseHandler.readOne(song_id)

        editTitleEt = findViewById(R.id.editTitleEt)
        editArtistEt = findViewById(R.id.editArtistEt)
        editAlbumEt = findViewById(R.id.editAlbumEt)
        editSongButton = findViewById(R.id.editSongButton)

        editTitleEt.setText(song.title)
        editArtistEt.setText(song.artist)
        editAlbumEt.setText(song.album)

        editSongButton.setOnClickListener{
            val title = editTitleEt.text.toString()
            val artist = editArtistEt.text.toString()
            val album = editAlbumEt.text.toString()

            val updatedSong = Song(id = song.id, title = title, artist = artist, album = album)

            if(databaseHandler.update(updatedSong)){
                Toast.makeText(applicationContext, "Song updated", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            } else {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}