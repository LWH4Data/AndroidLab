package com.example.ch15_outer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MyAIDLService : Service() {
    private var player: MediaPlayer? = null

    override fun onDestroy() {
        player?.release()
        player = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return object : MyAIDLInterface.Stub() {
            override fun getMaxDuration(): Int {
                return if (player?.isPlaying == true)
                    player!!.duration
                else 0
            }

            override fun start() {
                if (player?.isPlaying != true) {
                    player?.release()
                    player = MediaPlayer.create(this@MyAIDLService, R.raw.music)
                    try {
                        player?.start()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun stop() {
                if (player?.isPlaying == true)
                    player?.stop()
            }
        }
    }
}
