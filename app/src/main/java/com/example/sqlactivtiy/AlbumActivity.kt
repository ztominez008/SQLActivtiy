package com.example.sqlactivtiy

package com.example.uielementspart2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.uielementspart2.models.Album
import com.example.uielementspart2.models.Song

class AlbumsActivity : AppCompatActivity() {
    lateinit var albumTableHandler: AlbumTableHandler
    lateinit var albums: MutableList<Album>
    lateinit var adapter: ArrayAdapter<Album>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)

        albumTableHandler = AlbumTableHandler(this)
        albums = albumTableHandler.read()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, albums)
        val albumsListView = findViewById<ListView>(R.id.albumsListView)
        albumsListView.adapter = adapter
        registerForContextMenu(albumsListView)

        adapter.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.albums_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId){
            R.id.view_songs -> {
                true
            }R.id.edit_album -> {
                val album_id = albums[info.position].id
                val intent = Intent(applicationContext, EditAlbumActivity::class.java)
                intent.putExtra("album_id", album_id)
                startActivity(intent)
                true
            }R.id.delete_album -> {
                val album = albums[info.position]
                if(albumTableHandler.delete(album)){
                    albums.removeAt(info.position)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext, "Album deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.album_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.create_album -> {
                startActivity(Intent(this, CreateAlbumActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}