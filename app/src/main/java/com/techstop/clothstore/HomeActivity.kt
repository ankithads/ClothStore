package com.techstop.clothstore

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.techstop.clothstore.data.ProductInfo
import com.techstop.clothstore.data.URL
import com.techstop.clothstore.util.HttpHandler
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {


/*
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_wishlist)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_shoppingCart)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val catalogueButton = findViewById<Button>(R.id.catalogue);
        val wishlistButton = findViewById<Button>(R.id.wishlist);
        val cartButton = findViewById<Button>(R.id.cart);

        catalogueButton.setOnClickListener {
            val i = Intent(this, CatalogueActivity::class.java)//Start Another Activity
            startActivity(i)
            finish()
        }

        wishlistButton.setOnClickListener {
            val i = Intent(this, WishlistActivity::class.java)//Start Another Activity
            startActivity(i)
            finish()
        }

        cartButton.setOnClickListener {
            val i = Intent(this, CartActivity::class.java)//Start Another Activity
            startActivity(i)
            finish()
        }

   //     navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }


}
