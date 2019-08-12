package com.techstop.clothstore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.techstop.clothstore.data.CartRequest
import com.techstop.clothstore.data.CartResponse
import com.techstop.clothstore.data.ProductInfo
import com.techstop.clothstore.data.URL
import com.techstop.clothstore.util.HttpHandler
import com.techstop.clothstore.util.Repository
import kotlinx.android.synthetic.main.activity_catalogue.*
import kotlinx.android.synthetic.main.activity_home.*


class CatalogueActivity : AppCompatActivity() {
    lateinit var mainLayout: LinearLayout
    var productInfo: MutableMap<Int,ProductInfo> = mutableMapOf()
    val gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_catalogue)
        setSupportActionBar(toolbar)

        val homeButton = findViewById<Button>(R.id.homeButton)
        mainLayout = findViewById(R.id.mainLayout)

        //Populate the product List on create
        HttpHandler(getProductsHandler).execute(
                "",
                "GET",
                URL.BASE_URL + URL.PRODUCTS_URL,
                ""
        )

        homeButton.setOnClickListener{
            val i = Intent(this, HomeActivity::class.java)//Start Another Activity
            startActivity(i)
            finish()
        }
    }


    /*
        Callback function from HttpURLConnection to display the product List
     */
    var getProductsHandler: (List<String>?) -> Unit = {
        println(it);

        val arr = gson.fromJson(it!![1], Array<ProductInfo>::class.java)
        createCatalogue(arr)
    }

    /*This function does the following.
        -Create the layout for product listing
        -Send POST request to add the item to cart

    */
    fun createCatalogue(catalogue : Array<ProductInfo>) {
        var num_items = catalogue.size
        var rowLayout = arrayOfNulls<LinearLayout>(catalogue.size);
        var addTOCartButton = arrayOfNulls<Button>(catalogue.size);
        var addToWishlistButton = arrayOfNulls<Button>(catalogue.size);
        var index: Int = 0
        mainLayout.removeAllViews()
        for (item in catalogue) {
            rowLayout[index] = LinearLayout(this)
            rowLayout[index]!!.orientation = VERTICAL
            var properties = arrayOfNulls<TextView>(4)

            for(j in 0 until 4){
                properties[j] = TextView(this)
            }
            properties[0]?.text = item.name
            properties[1]?.text = item.category
            properties[2]?.text = item.price
            properties[3]?.text = item.stock
            addTOCartButton[index] = Button(this)
            addTOCartButton[index]?.text = "ADD TO CART"
            addTOCartButton[index]?.id = index
            productInfo[index] = item

            addTOCartButton[index]?.setOnClickListener {
                val cartRequest = CartRequest(item.productId)
                val cartJsonRequest = gson.toJson(cartRequest)
                println(item.productId + "  " + item.stock)
                if(item.stock == "0") {
                    Toast.makeText(this, "Sorry cannot add to cart, No stock left", Toast.LENGTH_LONG).show()

                } else {

                    HttpHandler(cartAddHandler).execute(
                            cartJsonRequest,
                            "POST",
                            URL.BASE_URL + URL.CART_URL,
                            ""
                    )
                }

            }

            addToWishlistButton[index] = Button(this)
            addToWishlistButton[index]?.text = "ADD TO WISHLIST"

            for(j in 0 until 4){
                rowLayout[index]?.addView(properties[j])
            }

            rowLayout[index]?.addView(addTOCartButton[index])
            rowLayout[index]?.addView(addToWishlistButton[index])

            mainLayout.addView(rowLayout[index])
            index++
        }

        fab.setOnClickListener { view ->

        }
    }

    //Add Cart item in repository for listing. Since the API returns same value every time { "cartId": 1, "productId": 123 }
    //There is only one item in the repository.
    var cartAddHandler: (List<String>?) -> Unit = {
        val cartResponse = gson.fromJson(it!![1], CartResponse::class.java)
        Repository.addToCart(cartResponse);
        Toast.makeText(this, "Successfully added to the cart", Toast.LENGTH_LONG).show()
    }

}
