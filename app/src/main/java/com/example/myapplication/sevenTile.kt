package com.example.myapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi

class sevenTile : AppCompatActivity() {

    var activePlayer = 1
    var gameIsActive = true
    var count = 0
    var text = ""
    var turns = ""
    var turnhist = ""
    //change everything at n
    var n = 7
    //big changes
    var f = n-5 //f = 2 at 7 , f = 3 at 8 , f = 4 at 9 , f = 5 at 10
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
    //hardcoded
        /*var activePlayer = 1
        var gameIsActive = true
        var count = 0
        var text = ""
        var turns = ""
        var turnhist = ""

/*   val test = findViewById<TextView>(R.id.debug)
fugame (gamestate: Array<Int>) {
       for (i in 0..49) {
           val k = 2
       }
test.text = gamestate.toString()
}*/

   var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                               2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                               2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                               2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                               2, 2 ,2, 2, 2, 2, 2, 2, 2)
   //add when have more tiles
   //tile of 5 from now on
   var winningPositions = arrayOf(
       //Horizontal
       intArrayOf( 0,  1,  2,  3,  4),
       intArrayOf( 1,  2,  3,  4,  5),
       intArrayOf( 2,  3,  4,  5,  6),

       intArrayOf( 7,  8,  9, 10, 11),
       intArrayOf( 8,  9, 10, 11, 12),
       intArrayOf( 9, 10, 11, 12, 13),

       intArrayOf(14, 15, 16, 17, 18),
       intArrayOf(15, 16, 17, 18, 19),
       intArrayOf(16, 17, 18, 19, 20),

       intArrayOf(21, 22, 23, 24, 25),
       intArrayOf(22, 23, 24, 25, 26),
       intArrayOf(23, 24, 25, 26, 27),

       intArrayOf(28, 29, 30, 31, 32),
       intArrayOf(29, 30, 31, 32, 33),
       intArrayOf(30, 31, 32, 33, 34),

       intArrayOf(35, 36, 37, 38, 39),
       intArrayOf(36, 37, 38, 39, 40),
       intArrayOf(37, 38, 39, 40, 41),

       intArrayOf(42, 43, 44, 45, 46),
       intArrayOf(43, 44, 45, 46, 47),
       intArrayOf(44, 45, 46, 47, 48),

       //Vertical
       intArrayOf(0, 7, 14, 21, 28),
       intArrayOf(7, 14, 21, 28, 35),
       intArrayOf(14, 21, 28, 35, 42),

       intArrayOf(1, 8,15, 22, 29),
       intArrayOf(8,15, 22, 29, 36),
       intArrayOf(15, 22, 29, 36, 43),

       intArrayOf(2, 9, 16, 23, 30),
       intArrayOf(9, 16, 23, 30, 37),
       intArrayOf(16, 23, 30, 37, 44),

       intArrayOf(3, 10, 17, 24, 31),
       intArrayOf(10, 17, 24, 31, 38),
       intArrayOf(17, 24, 31, 38, 45),

       intArrayOf(4, 11, 18, 25, 32),
       intArrayOf(11, 18, 25, 32, 39),
       intArrayOf(18, 26, 32, 39, 46),

       intArrayOf(5, 12, 19, 26, 33),
       intArrayOf(12, 19, 26, 33, 40),
       intArrayOf(19, 26, 33, 40, 47),

       intArrayOf(6, 13, 20, 27, 34),
       intArrayOf(13, 20, 27, 34, 41),
       intArrayOf(20, 27, 34, 41, 48),

       //X
       intArrayOf(0, 8, 16 ,24, 32),
       intArrayOf(8, 16 ,24, 32, 40),
       intArrayOf(16 ,24, 32, 40, 48),

       intArrayOf(6, 12, 18, 24, 30),
       intArrayOf(12, 18, 24, 30, 36),
       intArrayOf(18, 24, 30, 36, 42),
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
       if (gameIsActive && count == 49) {
           txt.text = "DRAW"
           layout.visibility = View.VISIBLE
           gameIsActive = false
       }
   }*/

   fun next(v: View){
       val intent = Intent(this@sevenTile,moveslog::class.java)
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
   setContentView(R.layout.activity_seven_tile)
}

}