package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi

class eightTile : AppCompatActivity() {

    var activePlayer = 1
    var gameIsActive = true
    var count = 0
    var text = ""
    var turns = ""
    var turnhist = ""
    //change everything at n
    var n = 8
    //big changes
    var f = n-5 //f = 3 at 8 , f = 4 at 9 , f = 5 at 10
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

    var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                                2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                                2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                                2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                                2, 2 ,2, 2, 2, 2, 2, 2, 2, 2,
                                2, 2 ,2, 2, 2, 2, 2, 2, 2, 2,
                                2, 2, 2, 2)
    //add when have more tiles
    //tile of 5 from now on
    var winningPositions = arrayOf(
        //Horizontal

        intArrayOf( 0,  1,  2,  3,  4),
        intArrayOf( 1,  2,  3,  4,  5),
        intArrayOf( 2,  3,  4,  5,  6),
        intArrayOf( 3,  4,  5,  6,  7),

        intArrayOf( 8,  9, 10, 11, 12),
        intArrayOf( 9, 10, 11, 12, 13),
        intArrayOf(10, 11, 12, 13, 14),
        intArrayOf(11, 12, 13, 14, 15),

        intArrayOf(16, 17, 18, 19, 20),
        intArrayOf(17, 18, 19, 20, 21),
        intArrayOf(18, 19, 20, 21, 22),
        intArrayOf(19, 20, 21, 22, 23),

        intArrayOf(24, 25, 26, 27, 28),
        intArrayOf(25, 26, 27, 28, 29),
        intArrayOf(26, 27, 28, 29, 30),
        intArrayOf(27, 28, 29, 30, 31),

        intArrayOf(32, 33, 34, 35, 36),
        intArrayOf(33, 34, 35, 36, 37),
        intArrayOf(34, 35, 36, 37, 38),
        intArrayOf(35, 36, 37, 38, 39),

        intArrayOf(40, 41, 42, 43, 44),
        intArrayOf(41, 42, 43, 44, 45),
        intArrayOf(42, 43, 44, 45, 46),
        intArrayOf(43, 44, 45, 46, 47),

        intArrayOf(48, 49, 50, 51, 52),
        intArrayOf(49, 50, 51, 52, 53),
        intArrayOf(50, 51, 52, 53, 54),
        intArrayOf(51, 52, 53, 54, 55),

        //new eighth row
        intArrayOf(56, 57, 58, 59, 60),
        intArrayOf(57, 58, 59, 60, 61),
        intArrayOf(58, 59, 60, 61, 62),
        intArrayOf(59, 60, 61, 62, 63),

        //Vertical
        intArrayOf(0, 8, 16, 24, 32),
        intArrayOf(8, 16, 24, 32, 40),
        intArrayOf(16, 24, 32, 40, 48),
        intArrayOf(24, 32, 40, 48, 56),

        intArrayOf(1, 9, 17, 25, 33),
        intArrayOf(9, 17, 25, 33, 41),
        intArrayOf(17, 25, 33, 41, 49),
        intArrayOf(25, 33, 41, 49, 57),

        intArrayOf(2,10, 18, 26, 34),
        intArrayOf(10, 18, 26, 34, 42),
        intArrayOf(18, 26, 34, 42, 50),
        intArrayOf(26, 34, 42, 50, 58),

        intArrayOf(3, 11, 19, 27, 35),
        intArrayOf(11, 19, 27, 35, 43),
        intArrayOf(19, 27, 35, 43, 51),
        intArrayOf(27, 35, 43, 51, 59),

        intArrayOf(4, 12, 20, 28, 36),
        intArrayOf(12, 20, 28, 36, 44),
        intArrayOf(20, 28, 36, 44, 52),
        intArrayOf(28, 36, 44, 52, 60),

        intArrayOf(5, 13, 21, 29, 37),
        intArrayOf(13, 21, 29, 37, 45),
        intArrayOf(21, 29, 37, 45, 53),
        intArrayOf(29, 37, 45, 53, 61),

        intArrayOf(6, 14, 22, 30, 38),
        intArrayOf(14, 22, 30, 38, 46),
        intArrayOf(22, 30, 38, 46, 54),
        intArrayOf(30, 38, 46, 54, 62),

        //new eighth col
        intArrayOf(7, 15, 23, 31, 39),
        intArrayOf(15, 23, 31, 39, 47),
        intArrayOf(23, 31, 39, 47, 55),
        intArrayOf(31, 39, 47, 55, 63),

        //X
        intArrayOf(0, 9, 18 ,27, 36),
        intArrayOf(9, 18 ,27, 36, 45),
        intArrayOf(18 ,27, 36, 45, 54),
        intArrayOf(27 ,36, 45, 54, 63),

        intArrayOf(7, 14, 21, 28, 35),
        intArrayOf(14, 21, 28, 35, 42),
        intArrayOf(21, 28, 35, 42, 49),
        intArrayOf(28, 35, 42, 49, 56),
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
        if (gameIsActive && count == 64) {
            txt.text = "DRAW"
            layout.visibility = View.VISIBLE
            gameIsActive = false
        }
    }*/

    fun next(v: View){
        val intent = Intent(this@eightTile,moveslog::class.java)
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
        setContentView(R.layout.activity_eight_tile)

        val sharedPreference = getSharedPreferences("playername", Context.MODE_PRIVATE)
        val p1name = sharedPreference.getString("p1","Player one")

        val firstturn = findViewById<TextView>(R.id.turn)
        firstturn.text = """$p1name turn"""
    }
}