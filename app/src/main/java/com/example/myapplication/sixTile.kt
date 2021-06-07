package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi

class sixTile : AppCompatActivity() {

    var activePlayer = 1
    var gameIsActive = true
    var count = 0
    var text = ""
    var turns = ""
    var turnhist = ""
    //change everything at n
    var n = 6
    //big changes
    var f = n-5 // f = 1 at 6, f = 2 at 7 , f = 3 at 8 , f = 4 at 9 , f = 5 at 10
    var x = 0
    var gameState = arrayListOf<Int>()

    var winningPositions = arrayListOf<IntArray>()

    val turnlist = arrayListOf<String>()
    val history = arrayListOf<String>()
    var pname = ""

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

        val sharedPreference = getSharedPreferences("playername", Context.MODE_PRIVATE)
        val p1name = sharedPreference.getString("p1","Player one")
        val p2name = sharedPreference.getString("p2","Player two")

        if (gameState[tappedcounter] == 2 && gameIsActive) {
            box.visibility = View.VISIBLE
            if (activePlayer == 1) {
                counter.setImageResource(R.drawable.clear)
                activePlayer = 0
                count++
                gameState[tappedcounter] = 1
                turnhist = """$p1name : X"""

                turn.text = """$p2name turn"""
                oturn.visibility = View.VISIBLE
                pname = p1name!!
            } else {
                counter.setImageResource(R.drawable.o)
                activePlayer = 1
                count++
                gameState[tappedcounter] = 0
                turnhist = """$p2name : O"""

                turn.text = """$p1name turn"""
                xturn.visibility = View.VISIBLE
                pname = p2name!!
            }
            turnlist.add("\n"+turnhist+"\n")

            //for debug
            //log. text = tappedcounter.toString()
            text = (tappedcounter+1).toString()
            history.add("\n"+text+"\n")
            log.text = """ $pname moves to $text"""

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
                    if (gameState[winningposition[0]] == 0) txt.text = """$p2name : O Wins"""
                    else if (gameState[winningposition[0]] == 1) txt.text = """$p1name : X Wins"""

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

    //hardcoded
    /*var activePlayer = 1
    var gameIsActive = true
    var count = 0
    var text = ""
    var turns = ""
    var turnhist = ""

    //more tiles
    var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                                2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                                2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                                2, 2, 2, 2, 2, 2)
    //add when have more tiles
    //tile of 5 from now on
    var winningPositions = arrayOf(
        //Horizontal
        intArrayOf( 0,  1,  2,  3,  4),
        intArrayOf( 1,  2,  3,  4,  5),

        intArrayOf( 6,  7,  8,  9, 10),
        intArrayOf( 7,  8,  9, 10, 11),

        intArrayOf(12, 13, 14, 15, 16),
        intArrayOf(13, 14, 15, 16, 17),

        intArrayOf(18, 19, 20, 21, 22),
        intArrayOf(19, 20, 21, 22, 23),

        intArrayOf(24, 25, 26, 27, 28),
        intArrayOf(25, 26, 27, 28, 29),

        intArrayOf(30, 31, 32, 33, 34),
        intArrayOf(31, 32, 33, 34, 35),

        //Vertical
        intArrayOf(0, 6, 12, 18, 24),
        intArrayOf(6, 12, 18, 24, 30),

        intArrayOf(1, 7,13, 19, 25),
        intArrayOf(7, 13,19, 25, 31),

        intArrayOf(2, 8, 14, 20, 26),
        intArrayOf(8, 14, 20, 26, 32),

        intArrayOf(3, 9, 15, 21, 27),
        intArrayOf(9, 15, 21, 27, 33),

        intArrayOf(4, 10, 16, 22, 28),
        intArrayOf(10, 16, 22, 28, 34),

        intArrayOf(5, 11, 17, 23, 29),
        intArrayOf(11, 17, 23, 29, 35),

        //X
        intArrayOf(0, 7, 14 ,21, 28),
        intArrayOf(7, 14 ,21, 28, 35),

        intArrayOf(5, 10, 15, 20, 25),
        intArrayOf(10, 15, 20, 25, 30),
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
        if (gameIsActive && count == 36) {
            txt.text = "DRAW"
            layout.visibility = View.VISIBLE
            gameIsActive = false
        }
    }*/

    fun next(v: View){
        val intent = Intent(this@sixTile,moveslog::class.java)
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
        setContentView(R.layout.activity_six_tile)

        val sharedPreference = getSharedPreferences("playername", Context.MODE_PRIVATE)
        val p1name = sharedPreference.getString("p1","Player one")

        val firstturn = findViewById<TextView>(R.id.turn)
        firstturn.text = """$p1name turn"""
    }
}