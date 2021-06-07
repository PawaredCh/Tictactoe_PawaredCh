package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class moveslog : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moveslog)

    val play = intent.getSerializableExtra("player")
        val mov = intent.getSerializableExtra("moveset")

        val showplay = findViewById<TextView>(R.id.player)
        val showmov = findViewById<TextView>(R.id.moveset)

        showplay.text = play.toString().replace("[","").replace("]","")
        showmov.text = mov.toString().replace("[","").replace("]","")
    }
}