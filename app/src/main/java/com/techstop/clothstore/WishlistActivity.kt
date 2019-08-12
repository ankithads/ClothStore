package com.techstop.clothstore

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_wishlist.*
import kotlinx.android.synthetic.main.content_wishlist.*

class WishlistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)
        setSupportActionBar(toolbar)
        homeButton.setOnClickListener{
            val i = Intent(this, HomeActivity::class.java)//Start Another Activity
            startActivity(i)
            finish()
        }
    }
}
