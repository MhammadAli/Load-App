package com.udacity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        val fileName = intent.getStringExtra("fileName").toString()
        val status = intent.getStringExtra("status").toString()
        filename_tv.text = fileName
        status_tv.text = status
    }

}
