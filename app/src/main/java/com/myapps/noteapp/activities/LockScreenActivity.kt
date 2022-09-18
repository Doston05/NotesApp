package com.myapps.noteapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hanks.passcodeview.PasscodeView.PasscodeViewListener
import com.myapps.noteapp.databinding.ActivityLockScreenBinding


class LockScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLockScreenBinding
    private  var password: String?=null
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var editor:SharedPreferences.Editor

    private var isPasswordCreated=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLockScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = applicationContext.getSharedPreferences("MyPref", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        password=sharedPreferences.getString("password",null)
        isPasswordCreated=sharedPreferences.getBoolean("flag",false)

        if (!isPasswordCreated){
            createPassword()
        }
        else {
            checkPassword()
        }

    }
    private fun createPassword(){
        binding.passcodeview.listener=object :PasscodeViewListener{
            override fun onFail() {
                TODO("Not yet implemented")
            }

            override fun onSuccess(number: String?) {
                password=number
                editor.putString("password",password)
                isPasswordCreated=true
                editor.putBoolean("flag",isPasswordCreated)
                editor.apply()
                val intentPasscode = Intent(this@LockScreenActivity, MainActivity::class.java)
                startActivity(intentPasscode)
            }

        }
    }

    private fun checkPassword(){
        binding.passcodeview.setLocalPasscode(password).listener = object : PasscodeViewListener {
            override fun onFail() {
                // to show message when Password is incorrect
                Toast.makeText(this@LockScreenActivity, "Password is wrong!", Toast.LENGTH_SHORT)
                    .show()
                binding.passcodeview.clearFocus()
            }

            override fun onSuccess(number: String) {

                val intentPasscode = Intent(this@LockScreenActivity, MainActivity::class.java)
                startActivity(intentPasscode)
            }
        }

    }


}