package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val p1name = findViewById<EditText>(R.id.playerone).text
        val p2name = findViewById<EditText>(R.id.playertwo).text
        val spinner = findViewById<Spinner>(R.id.select)
        val start = findViewById<ImageButton>(R.id.start)
        val random = findViewById<Button>(R.id.random)

        var board = ""
        var rand = 0

        val sharedPreference = getSharedPreferences("playername", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()

        if (spinner != null) {
            val box = resources.getStringArray(R.array.board_size)
            val adapter = ArrayAdapter.createFromResource(
                this,
                R.array.board_size, android.R.layout.simple_spinner_item
            )
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    if (box[position] == "3*3") board = "3"
                    if (box[position] == "4*4") board = "4"
                    if (box[position] == "5*5") board = "5"
                    if (box[position] == "6*6") board = "6"
                    if (box[position] == "7*7") board = "7"
                    if (box[position] == "8*8") board = "8"
                    if (box[position] == "9*9") board = "9"
                    if (box[position] == "10*10") board = "10"
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                    Toast.makeText(
                        this@Menu,
                        "Please select board size", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        random.setOnClickListener() {
            rand = (3 until 11).shuffled().last()
            rand = rand
            val test = findViewById<TextView>(R.id.textView)
            test.text = """randomized board size is $rand"""
        }

        start.setOnClickListener() {

            if (p1name.isEmpty() || p2name.isEmpty()){
                Toast.makeText(applicationContext, "Please enter both player name", Toast.LENGTH_SHORT)
                    .show()
            }else{

            if (board == "3" || rand == 3) {
                val intent = Intent(this@Menu, MainActivity::class.java)
                editor.putString("p1", p1name.toString())
                editor.putString("p2", p2name.toString())
                editor.commit()
                startActivity(intent)
            }
            if (board == "4" || rand == 4){
                val intent = Intent(this@Menu, fourTile::class.java)
                editor.putString("p1", p1name.toString())
                editor.putString("p2", p2name.toString())
                editor.commit()
                startActivity(intent)
            }
            if(board == "5" || rand == 5){
                val intent = Intent(this@Menu, five_tile::class.java)
                editor.putString("p1", p1name.toString())
                editor.putString("p2", p2name.toString())
                editor.commit()
                startActivity(intent)
            }
            if(board == "6" || rand == 6){
                val intent = Intent(this@Menu, sixTile::class.java)
                editor.putString("p1", p1name.toString())
                editor.putString("p2", p2name.toString())
                editor.commit()
                startActivity(intent)
            }
            if(board == "7" || rand == 7){
                val intent = Intent(this@Menu, sevenTile::class.java)
                editor.putString("p1", p1name.toString())
                editor.putString("p2", p2name.toString())
                editor.commit()
                startActivity(intent)
            }
            if(board == "8" || rand == 8){
                val intent = Intent(this@Menu, eightTile::class.java)
                editor.putString("p1", p1name.toString())
                editor.putString("p2", p2name.toString())
                editor.commit()
                startActivity(intent)
            }
            if(board == "9" || rand == 9){
                val intent = Intent(this@Menu, nineTile::class.java)
                editor.putString("p1", p1name.toString())
                editor.putString("p2", p2name.toString())
                editor.commit()
                startActivity(intent)
            }
            if(board == "10" || rand == 10) {
                val intent = Intent(this@Menu, tenTile::class.java)
                editor.putString("p1", p1name.toString())
                editor.putString("p2", p2name.toString())
                editor.commit()
                startActivity(intent)
            }
            }
        }

    }

}