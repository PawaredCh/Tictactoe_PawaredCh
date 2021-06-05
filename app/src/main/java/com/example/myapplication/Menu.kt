package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val spinner = findViewById<Spinner>(R.id.select)
        val start = findViewById<ImageButton>(R.id.start)

        if (spinner != null) {
            val box = resources.getStringArray(R.array.board_size)
            val adapter = ArrayAdapter.createFromResource(this,
                R.array.board_size, android.R.layout.simple_spinner_item)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {

                    if (box[position]=="4*4"){
                        val intent = Intent(this@Menu,fourTile::class.java)
                        startActivity(intent)
                    }
                    if (box[position]=="5*5"){
                        val intent = Intent(this@Menu,five_tile::class.java)
                        startActivity(intent)
                    }
                    if (box[position]=="6*6"){
                        val intent = Intent(this@Menu,sixTile::class.java)
                        startActivity(intent)
                    }
                    if (box[position]=="7*7"){
                        val intent = Intent(this@Menu,sevenTile::class.java)
                        startActivity(intent)
                    }
                    if (box[position]=="8*8"){
                        val intent = Intent(this@Menu,eightTile::class.java)
                        startActivity(intent)
                    }
                    if (box[position]=="9*9"){
                        val intent = Intent(this@Menu,nineTile::class.java)
                        startActivity(intent)
                    }
                    if (box[position]=="10*10"){
                        val intent = Intent(this@Menu,tenTile::class.java)
                        startActivity(intent)
                    }

                start.setOnClickListener(){
                    val intent = Intent(this@Menu,MainActivity::class.java)
                    startActivity(intent)

                }
                    val random = findViewById<Button>(R.id.random)

                    random.setOnClickListener(){

                        val rand = (3 until 10).shuffled().last()

                        if (rand == 3) {
                            val intent = Intent(this@Menu,MainActivity::class.java)
                            startActivity(intent)
                        }
                        if (rand == 4){
                            val intent = Intent(this@Menu,fourTile::class.java)
                            startActivity(intent)
                        }
                        if (rand == 5){
                            val intent = Intent(this@Menu,five_tile::class.java)
                            startActivity(intent)
                        }
                        if (rand == 6 ){
                            val intent = Intent(this@Menu,sixTile::class.java)
                            startActivity(intent)
                        }
                        if (rand == 7){
                            val intent = Intent(this@Menu,sevenTile::class.java)
                            startActivity(intent)
                        }
                        if (rand == 8){
                            val intent = Intent(this@Menu,eightTile::class.java)
                            startActivity(intent)
                        }
                        if (rand == 9){
                            val intent = Intent(this@Menu,nineTile::class.java)
                            startActivity(intent)
                        }
                        if (rand == 10){
                            val intent = Intent(this@Menu,tenTile::class.java)
                            startActivity(intent)
                        }
                    }
            }


                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                    Toast.makeText(this@Menu,
                        "Please select something", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}