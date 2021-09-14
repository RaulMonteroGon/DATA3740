package com.example.data3740.project.landing

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.data3740.R

class SelectImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_image)

        val btnsettingsimg : Button = findViewById(R.id.btn_settingsimg)


        btnsettingsimg.setOnClickListener {
            val settings = Intent(this, SettingsActivity::class.java)
            startActivity(settings)
        }
    }
}
