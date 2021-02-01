package com.example.sqlactivtiy

package com.example.uielementspart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uielementspart2.models.Album
import com.example.uielementspart2.models.Song

class CreateAlbumActivity : AppCompatActivity() {
    lateinit var createAlbumButton: Button
    lateinit var albumTitleEt: EditText
    lateinit var albumDate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_album)

        val databaseHandler = AlbumTableHandler(this)
        albumTitleEt = findViewById(R.id.albumTitleEt)
        albumDate = findViewById(R.id.albumDate)

        createAlbumButton = findViewById(R.id.createAlbumButton)
        createAlbumButton.setOnClickListener{
            val title = albumTitleEt.text.toString()
            val date = albumDate.text.toString()

            val album = Album(title = title, date = date)

            if(databaseHandler.create(album)){
                Toast.makeText(applicationContext, "Album created", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, AlbumsActivity::class.java))
            } else {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

        }
    }


}