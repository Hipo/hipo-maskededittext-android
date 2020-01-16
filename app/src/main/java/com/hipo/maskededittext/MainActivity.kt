package com.hipo.maskededittext

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    private fun initUi() {
        parsedTextButton.setOnClickListener {
            Toast.makeText(this, phoneEditText.getParsedText(), Toast.LENGTH_SHORT).show()
        }

        rawTextButton.setOnClickListener {
            Toast.makeText(this, phoneEditText.text.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
