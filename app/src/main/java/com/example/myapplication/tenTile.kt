package com.example.myapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi

class tenTile : AppCompatActivity() {

    var activePlayer = 1
    var gameIsActive = true
    var count = 0
    var text = ""
    var turns = ""
    var turnhist = ""
    //change everything at n
    var n = 10
    //big changes
    var f = n-5 //f = 3 at 8 , f = 4 at 9 , f = 5 at 10
    var x = 0
    var gameState = arrayListOf<Int>()

    var winningPositions = arrayListOf<IntArray>()

    val turnlist = arrayListOf<String>()
    val history = arrayListOf<String>()

    @RequiresApi(Build.VERSION_CODES.N)
    fun dropIn(view: View) {

        //all tiles
        for (i in 0..((n*n)-1)) gameState.add(2)

        for(j in 0..(n-1)) { var j = j

            //horizontal 0..4 1..5 ,9..13 10..14 ... 72..76
            for (i in (n * j)..((n * j) + f)) {
                var i = i
                winningPositions.add(intArrayOf(i, i + 1, i + 2, i + 3, i + 4))
                i++
            }
            j++
        }

        //X
        for(g in x..((n+1)*(n-5))){winningPositions.add(intArrayOf(
            g,g+(n+1),g+2*(n+1),g+3*(n+1),g+4*(n+1)))
        }
        for (g in (x+4)..(((n+1)*(n-5))+4)){
            winningPositions.add(intArrayOf(
                g,g+(n-1),g+2*(n-1),g+3*(n-1),g+4*(n-1)))
        }

        //Vertical : switch logic with horizontal
        for (j in 0..f){ var j=j
            //inner loop for vertical
            for (i in 0..(n-1)) { var i = i
                winningPositions.add(
                    intArrayOf(    (
                            n * (j+0)) + i,
                        (n * (j+1)) + i,
                        (n * (j+2)) + i,
                        (n * (j+3)) + i,
                        (n * (j+4)) + i     )   )
                i++
            }
            j++
        }

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
        val log = findViewById<TextView>(R.id.logging)

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

            //for debug
            //log. text = tappedcounter.toString()
            text = tappedcounter.toString()
            history.add("\n"+text+"\n")
            log.text = turnhist + " moves to " + text

            counter.translationY = -1500f
            counter.animate().translationYBy(1500f).rotationY(2000f).duration = 1000
            for (winningposition in winningPositions) {
                //stay like this from now on cuz we make 5
                if (gameState[winningposition[0]] == gameState[winningposition[1]]
                    && gameState[winningposition[1]] == gameState[winningposition[2]]
                    && gameState[winningposition[2]] == gameState[winningposition[3]]
                    && gameState[winningposition[3]] == gameState[winningposition[4]]
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
        //add more tiles
        if (gameIsActive && count == (n*n)) {
            txt.text = "DRAW"
            layout.visibility = View.VISIBLE
            gameIsActive = false
        }
    }

    fun next(v: View){
        val intent = Intent(this@tenTile,moveslog::class.java)
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
        setContentView(R.layout.activity_ten_tile)
    }
}