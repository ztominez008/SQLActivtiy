package com.example.sqlactivtiy

package com.example.uielementspart2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class QueueActivity : AppCompatActivity() {
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queue)

        val songs = intent.getStringArrayListExtra( "Song")
        val temp = ArrayList<String>(0)
        if(songs == temp){
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val intent = Intent(applicationContext, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(
                    channelId,description,NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this,channelId)
                    .setContentTitle("Song queue empty")
                    .setContentText("Your song queue is empty, try adding some songs")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
            }else{

                builder = Notification.Builder(this)
                    .setContentTitle("Song queue empty")
                    .setContentText("Your song queue is empty, try adding some songs")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234,builder.build())
        }
        adapter =
            songs?.let { ArrayAdapter(this, android.R.layout.simple_list_item_1, it) }!!
        val queued = findViewById<ListView>(R.id.queuedSongsList)
        queued.adapter = adapter
        registerForContextMenu(queued)

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.delete_song, menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.queue_option -> {
                val songs = intent.getStringArrayListExtra( "Song")

                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                val listPosition = info.position
                val song = songs?.get(listPosition)
                songs!!.remove(song)

                adapter.notifyDataSetChanged()

                true
            }
            else -> super.onContextItemSelected(item)
        }
    }



}