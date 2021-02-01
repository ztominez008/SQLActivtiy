package com.example.sqlactivtiy

package com.example.uielementspart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uielementspart2.models.Song

class CreateSongActivity : AppCompatActivity() {
    lateinit var createSongButton: Button
    lateinit var titleEt: EditText
    lateinit var artistEt:EditText
    lateinit var albumEt:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_song)

        val databaseHandler = SongsTableHandler(this)
        titleEt = findViewById(R.id.titleEt)
        artistEt = findViewById(R.id.artistEt)
        albumEt = findViewById(R.id.albumEt)

        createSongButton = findViewById(R.id.createSongButton)
        createSongButton.setOnClickListener{
            val title = titleEt.text.toString()
            val artist = artistEt.text.toString()
            val album = albumEt.text.toString()

            val song = Song(title = title, artist = artist, album = album)

            if(databaseHandler.create(song)){
                Toast.makeText(applicationContext, "Song created", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            } else {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
            clearFields()
        }

    }

    private fun clearFields() {
        titleEt.text.clear()
        artistEt.text.clear()
        albumEt.text.clear()
    }
}