package com.myapps.noteapp.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.BaseColumns
import androidx.appcompat.app.AppCompatActivity
import com.myapps.noteapp.Database.DatabaseHelper
import com.myapps.noteapp.databinding.ActivityNoteBinding
import com.myapps.noteapp.model.NoteEntity
import java.text.DateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    private lateinit var dbHelper: DatabaseHelper
    private var background:String="#E8AA42"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHelper=DatabaseHelper(this)
        dbHelper.readableDatabase
        binding.saveButton.setOnClickListener {
            insertData()
        }
            binding.color1.setOnClickListener{
               setBackgroundColor("0096FF")
            }
            binding.color2.setOnClickListener{
              setBackgroundColor("7DCE13")
            }
            binding.color3.setOnClickListener{
                setBackgroundColor("E8AA42")
            }
            binding.color4.setOnClickListener{
                setBackgroundColor("76BA99")
            }
            binding.color5.setOnClickListener{
                setBackgroundColor("9772FB")
            }
            binding.color6.setOnClickListener{
                setBackgroundColor("F87474")
            }
        binding.color7.setOnClickListener {
            setBackgroundColor("9EB23B")
        }


    }
    private fun setBackgroundColor(color:String){
        binding.editorBackground.setBackgroundColor(Color.parseColor("#$color"))
        background="#$color"
    }
    private fun insertData(){
        if (binding.title.text.toString().isNotEmpty() && binding.notesText.text.toString().isNotEmpty()){
            val title= binding.title.text.toString()
            val noteText= binding.notesText.text.toString()
            val time:Calendar=Calendar.getInstance()
            val currentDate=DateFormat.getDateInstance(DateFormat.FULL).format(time.time)
            val result=  dbHelper.insertData(this,
                NoteEntity(
                    "",
                    title,
                    noteText,
                    BaseColumns._ID,
                    background,
                    currentDate,
                    "null",
                    "null"
                )
            )
            if (result){
                binding.title.text.clear()
                binding.notesText.text.clear()

            }

        }
    }
    override fun onBackPressed(){
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }
}