package com.techstop.clothstore

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.techstop.clothstore.data.CartRequest
import com.techstop.clothstore.data.CartResponse
import com.techstop.clothstore.data.ProductInfo
import com.techstop.clothstore.data.URL
import com.techstop.clothstore.util.HttpHandler
import com.techstop.clothstore.util.Repository
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.content_cart.*

class CartActivity : AppCompatActivity() {

    lateinit var cartLayout: LinearLayout
    lateinit var total: TextView
    val gson: Gson = Gson()

    var totalPrice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setSupportActionBar(toolbar)
        cartLayout = findViewById(R.id.cartLayout)
        total = findViewById(R.id.textView)

        displayList()

        homeButton.setOnClickListener{
            val i = Intent(this, HomeActivity::class.java)//Start Another Activity
            startActivity(i)
            finish()
        }
    }

    /*
        This function does below functionality
            - Get the cart item from the repository
            - send GET Request to get the product Info
            - Send delete API request
            - Removes the item from the cart
     */
    fun displayList() {
        val cartItems = Repository.getCartItems()

        for(product in cartItems) {
            val rowLayout                 = LinearLayout(this)
            rowLayout!!.orientation       = LinearLayout.VERTICAL
            var properties = arrayOfNulls<TextView>(4)
            for(j in 0 until 4){
                properties[j] = TextView(this)
            }

            //Get the product Info by sending GET request to /ProductId "
            HttpHandler({
                val item = gson.fromJson(it!![1], ProductInfo::class.java)
                properties[0]?.text = item.name
                properties[1]?.text = item.category
                properties[2]?.text = item.price
                properties[3]?.text = item.stock
                val removeFromCartButton = Button(this)
                removeFromCartButton?.text = "REMOVE"


                totalPrice += item.price.toInt()
                total.text = "Total : " + totalPrice

                //Button to remove from the cart
                removeFromCartButton.setOnClickListener({
                    HttpHandler({if(it[0] == "1") {
                        Repository.deletefromCart(product.key);
                        rowLayout.removeAllViews()
                        totalPrice -= item.price.toInt()
                        total.text = "Total : " + totalPrice
                        Toast.makeText(this, "Suceessfully deleted" + item.name, Snackbar.LENGTH_LONG).show()
                    }
                    }).execute(
                            "",
                            "DELETE",
                            URL.BASE_URL + URL.CART_URL + "/" + product.key ,
                            ""
                    )
                })
                for(j in 0 until 4){
                    rowLayout?.addView(properties[j])
                }
                rowLayout?.addView(removeFromCartButton)
                cartLayout.addView(rowLayout)
            }).execute(
                    "",
                    "GET",
                    URL.BASE_URL + URL.PRODUCTS_URL+"/" + product.value,
                    ""
            )
        }
    }

}
