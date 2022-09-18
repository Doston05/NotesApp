package com.myapps.noteapp.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.myapps.noteapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySettingsBinding
    private lateinit var prefs:SharedPreferences
    private lateinit var editor:SharedPreferences.Editor
    private lateinit var titlePrefs:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs=applicationContext.getSharedPreferences("navDrawer", Context.MODE_PRIVATE)
        titlePrefs=applicationContext.getSharedPreferences("myPref",Context.MODE_PRIVATE)
        editor=prefs.edit()
        binding.color1.setText(titlePrefs.getString("color11", null))
        binding.color2.setText(titlePrefs.getString("color22", null))
        binding.color3.setText(titlePrefs.getString("color33", null))
        binding.color4.setText(titlePrefs.getString("color44", null))
        binding.color5.setText(titlePrefs.getString("color55", null))
        binding.color6.setText(titlePrefs.getString("color66", null))
        binding.color7.setText(titlePrefs.getString("color77", null))
        binding.saveButton.setOnClickListener {
            editor.putString("color1", binding.color1.text.toString())
            editor.putString("color2", binding.color2.text.toString())
            editor.putString("color3", binding.color3.text.toString())
            editor.putString("color4", binding.color4.text.toString())
            editor.putString("color5", binding.color5.text.toString())
            editor.putString("color6", binding.color6.text.toString())
            editor.putString("color7", binding.color7.text.toString())
            editor.apply()
            Toast.makeText(applicationContext,"Categories name successfully updated.",Toast.LENGTH_SHORT).show()
        }
    }

}