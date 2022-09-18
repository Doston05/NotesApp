package com.myapps.noteapp.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.myapps.noteapp.Database.DatabaseHelper
import com.myapps.noteapp.R
import com.myapps.noteapp.databinding.ActivityEditorBinding
import com.myapps.noteapp.model.NoteEntity

class EditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditorBinding
    private lateinit var backgroundColor: String
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var time: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bind()
        val screenType=intent.getStringExtra("edit")
        if (screenType.equals("edit")){
            binding.notesTextview.visibility = View.INVISIBLE
            binding.titleTextview.visibility = View.INVISIBLE
            binding.title.visibility = View.VISIBLE
            binding.notesText.visibility = View.VISIBLE
            binding.backgroundLayout.visibility = View.VISIBLE
        }
        else{
            binding.notesTextview.visibility = View.VISIBLE
            binding.titleTextview.visibility = View.VISIBLE
            binding.saveButton.visibility=View.INVISIBLE
            binding.title.visibility = View.INVISIBLE
            binding.notesText.visibility = View.INVISIBLE
            binding.backgroundLayout.visibility = View.VISIBLE
        }

        backgroundColor = intent.getStringExtra("background").toString()
        binding.editorBackground.setBackgroundColor(Color.parseColor(backgroundColor))
        dbHelper = DatabaseHelper(this)
        time = intent.getStringExtra("time").toString()


        binding.saveButton.setOnClickListener {
           alertDialog()
        }

        binding.color1.setOnClickListener{
            setBackgroundColor1("0096FF")
        }
        binding.color2.setOnClickListener{
            setBackgroundColor1("7DCE13")
        }
        binding.color3.setOnClickListener{
            setBackgroundColor1("E8AA42")
        }
        binding.color4.setOnClickListener{
            setBackgroundColor1("76BA99")
        }
        binding.color5.setOnClickListener{
            setBackgroundColor1("9772FB")
        }
        binding.color6.setOnClickListener{
            setBackgroundColor1("F87474")
        }
        binding.color7.setOnClickListener {
            setBackgroundColor1("9EB23B")
        }


    }

    private fun setBackgroundColor1(color:String){
        binding.editorBackground.setBackgroundColor(Color.parseColor("#$color"))
        backgroundColor="#$color"
    }

    private fun bind(){
        val title=intent.getStringExtra("title")
        val note= intent.getStringExtra("text")
        binding.title.setText(title)
        binding.notesText.setText(note)
        binding.notesTextview.text = note
        binding.titleTextview.text=title
    }
    private fun update() {
        if (binding.title.text.toString().isNotEmpty() && binding.notesText.text.toString()
                .isNotEmpty()
        ) {
            val title = binding.title.text.toString()
            val noteText = binding.notesText.text.toString()
            val createdTime = time
            val index=intent.getStringExtra("index").toString()
            val id = intent.getStringExtra("id").toString()
            val favorite=intent.getStringExtra("favorite").toString()
            val archive=intent.getStringExtra("archive").toString()
            val currentItem =
                NoteEntity(id, title, noteText, index,backgroundColor, createdTime,favorite,archive)
            val result = dbHelper.updateData(currentItem)
            if (result) {
                binding.title.text.clear()
                binding.notesText.text.clear()

            }
        }
        else{
            Toast.makeText(this,"There is no text to save ",Toast.LENGTH_SHORT).show()
        }
    }

            private fun alertDialog(){
                val builder= MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                builder.setTitle("Save changes")
                    .setMessage("Do you really want to save changes")
                builder.setPositiveButton("Yes"){ _, _ ->
                    Toast.makeText(applicationContext,"this note successfully changed", Toast.LENGTH_SHORT).show()
                        update()
                    startActivity(Intent(this, MainActivity::class.java))



                }
                builder.setNegativeButton("No"){_,_->

                }
                builder.create()

                val alertDialog=builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()

            }
    override fun onBackPressed(){
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }

        }

