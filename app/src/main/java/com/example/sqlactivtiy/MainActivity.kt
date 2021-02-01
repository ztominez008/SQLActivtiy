package com.example.sqlactivity
package com.example.uielementpart2

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uielementspart2.models.Song
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var songsTableHandler: SongsTableHandler
    lateinit var songsNew: MutableList<Song>
    lateinit var adapter: ArrayAdapter<Song>
    var songs = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        songsTableHandler = SongsTableHandler(this)
        songsNew = songsTableHandler.read()


        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, songsNew)
        val songsListView = findViewById<ListView>(R.id.songsListView)
        songsListView.adapter = adapter
        registerForContextMenu(songsListView)

        adapter.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.songs_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.queue_option -> {
                val song = songsNew[info.position]
                songs.add(song.toString())
                val snackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Song added to queue", Snackbar.LENGTH_LONG)
                snackbar.setAction("Go to queue", View.OnClickListener{
                    val intent = Intent(applicationContext, QueueActivity::class.java)
                    intent.putExtra("Song", songs)
                    startActivity(intent)
                })
                snackbar.show()
                true
            }R.id.edit_song -> {
                val song_id = songsNew[info.position].id
                val intent = Intent(applicationContext, EditSongActivity::class.java)
                intent.putExtra("song_id", song_id)
                startActivity(intent)
                true
            }R.id.delete_song -> {
                val song = songsNew[info.position]
                if(songsTableHandler.delete(song)){
                    songsNew.removeAt(info.position)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext, "Song deleted", Toast.LENGTH_SHORT).show()
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
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.go_to_albums_page -> {
                startActivity(Intent(this, AlbumsActivity::class.java))
                true
            }R.id.go_to_queue_page -> {
                val intent = Intent(this, QueueActivity::class.java)
                intent.putExtra("Song", songs)
                startActivity(intent)
                true
            }R.id.create_song -> {
                startActivity(Intent(applicationContext, CreateSongActivity::class.java))
                true
            }R.id.go_to_songs_page -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}