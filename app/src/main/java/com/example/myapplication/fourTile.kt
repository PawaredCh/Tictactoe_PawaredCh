package com.example.myapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi

class fourTile : AppCompatActivity() {

    var activePlayer = 1
    var gameIsActive = true
    var count = 0
    var text = ""
    var turns = ""
    var turnhist = ""

    var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                                2, 2, 2, 2, 2, 2)
    //add when have more tiles
    var winningPositions = arrayOf(
        intArrayOf(0, 1, 2, 3),
        intArrayOf(4, 5, 6, 7),
        intArrayOf(8, 9, 10, 11),
        intArrayOf(12, 13, 14, 15),

        intArrayOf(0, 5, 10 ,15),
        intArrayOf(0, 4, 8, 12),
        intArrayOf(1, 5, 9, 13),
        intArrayOf(2, 6, 10, 14),
        intArrayOf(3, 7, 11, 15),
        intArrayOf(3, 6, 9, 12)
    )
    val turnlist = arrayListOf<String>()
    val history = arrayListOf<String>()

    @RequiresApi(Build.VERSION_CODES.N)
    fun dropIn(view: View) {
        val counter = view as ImageView
        val txt = findViewById<TextView>(R.id.winner1)
        val indicate = findViewById<TextView>(R.id.turn)
        val layout = findViewById<LinearLayout>(R.id.winner)
        val next = findViewById<Button>(R.id.Next)
        val turn = findViewById<TextView>(R.id.turn)

        val xturn = findViewById<ImageView>(R.id.xturn)
        val oturn = findViewById<ImageView>(R.id.oturn)

        val box = findViewById<LinearLayout>(R.id.indicator)
        val now = findViewById<TextView>(R.id.textView3)
        xturn.visibility = View.INVISIBLE
        oturn.visibility = View.INVISIBLE
        //understand
        val tappedcounter = counter.tag.toString().toInt()

        if (gameState[tappedcounter] == 2 && gameIsActive) {
            box.visibility = View.VISIBLE
            if (activePlayer == 1) {
                counter.setImageResource(R.drawable.clear)
                activePlayer = 0
                count++
                gameState[tappedcounter] = 1
                turnhist = "X"

                turns = "O player turn"
                turn.text = turns
                oturn.visibility = View.VISIBLE
            } else {
                counter.setImageResource(R.drawable.o)
                activePlayer = 1
                count++
                gameState[tappedcounter] = 0
                turnhist = "O"

                turns = "X player turn"
                turn.text = turns
                xturn.visibility = View.VISIBLE
            }
            turnlist.add("\n"+turnhist+"\n")
            val log = findViewById<TextView>(R.id.logging)

            //for debug
            //log.
            text = tappedcounter.toString()
            history.add("\n"+text+"\n")
            log.text = turnhist + " moves to " + text

            counter.translationY = -1500f
            counter.animate().translationYBy(1500f).rotationY(2000f).duration = 1000
            for (winningposition in winningPositions) {
                //add when have more tiles
                if (gameState[winningposition[0]] == gameState[winningposition[1]]
                    && gameState[winningposition[1]] == gameState[winningposition[2]]
                    && gameState[winningposition[2]] == gameState[winningposition[3]]
                    && gameState[winningposition[0]] != 2
                ) {
                    if (gameState[winningposition[0]] == 0)
                        txt.text = "O Player Wins"
                    else if (gameState[winningposition[0]] == 1)
                        txt.text = "X Player Wins"

                    indicate.visibility = View.INVISIBLE
                    layout.visibility = View.VISIBLE
                    gameIsActive = false
                    box.visibility = View.INVISIBLE
                    now.visibility = View.INVISIBLE
                }
            }
        }
        if (gameIsActive && count == 16) {
            txt.text = "DRAW"
            layout.visibility = View.VISIBLE
            gameIsActive = false
        }
    }

    fun next(v: View){
        val intent = Intent(this@fourTile,moveslog::class.java)
        intent.putExtra("player",turnlist)
        intent.putExtra("moveset",history)
        startActivity(intent)

    }

    fun playAgain(view: View?) {
        activePlayer = 1
        gameIsActive = true
        count= 0
        val linearLayout = findViewById<LinearLayout>(R.id.winner)
        val gridLayout =
            findViewById<GridLayout>(R.id.gridLayout)
        val log = findViewById<TextView>(R.id.logging)
        for (i in gameState.indices) {
            gameState[i] = 2
        }
        linearLayout.visibility = View.INVISIBLE
        for (i in 0 until gridLayout.childCount) {
            (gridLayout.getChildAt(i) as ImageView).setImageResource(0) //p t n
        }
        log.text = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_four_tile)
    }
}