package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    //1=green  0 =red
    var activePlayer = 1
    var gameIsActive = true
    var count = 0
    var text = ""
    var turnhist = ""
    var ghist = ""
    var rhist = ""
    var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
    var winningPositions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )
    val turnlist = arrayListOf<String>()
    val history = arrayListOf<String>()
    //val maphist = mutableMapOf<String,String>()
    var pname = ""

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
            val log = findViewById<TextView>(R.id.logging)

            //for debug
            //log.text = tappedcounter.toString()
            if(tappedcounter == 0)
                text =  "1: upper left "
            else if(tappedcounter == 1)
                text =  "2: upper center "
            else if(tappedcounter == 2)
                text =  "3: upper right "
            else if(tappedcounter == 3)
                text =  "4: center left "
            else if(tappedcounter == 4)
                text =  "5: center "
            else if(tappedcounter == 5)
                text =  "6: center right "
            else if(tappedcounter == 6)
                text =  "7: below left "
            else if(tappedcounter == 7)
                text =  "8: below center "
            else if(tappedcounter == 8)
                text =  "9: below right "
            history.add("\n"+text+"\n")

            //try to use map but failed
            /*for(z in 0..8 step 2) {
                if (turnhist == "Green" && z%2==0){
                    ghist = turnhist + (z + 1)
                    maphist.put(ghist , text)
                }
                else //if (turnhist == "Red" && z%2 != 0)
                    {
                     rhist = turnhist + (z + 2)
                        maphist.put(rhist ,text)
                }
            }
            maphist.remove("Red10")*/

            //debug before intent
            /*for (i in 0 until turnlist.size) {
                //log.append(history[i]+"\n") //1 1,2 1,2,3
                log.text = turnlist.toString() + "moves to " + history.toString()+"\n" //1,2,3
            }*/

            //try to use map but failed
            /*for ((k,v) in maphist) {
                log.text = maphist.toString()+"\n"
            }*/
            log.text = """ $pname moves to $text"""

            counter.translationY = -1500f
            counter.animate().translationYBy(1500f).rotationY(2000f).duration = 1000
            for (winningposition in winningPositions) {
                if (gameState[winningposition[0]] == gameState[winningposition[1]] && gameState[winningposition[1]] == gameState[winningposition[2]] && gameState[winningposition[0]] != 2
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
        if (gameIsActive && count == 9) {
            txt.text = "DRAW"
            layout.visibility = View.VISIBLE
            gameIsActive = false
        }
    }

    fun next(v: View){
        val intent = Intent(this@MainActivity,moveslog::class.java)
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
        //maphist.clear()
        log.text = ""
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreference = getSharedPreferences("playername", Context.MODE_PRIVATE)
        val p1name = sharedPreference.getString("p1","Player one")

        val firstturn = findViewById<TextView>(R.id.turn)
        firstturn.text = """$p1name turn"""
    }
}