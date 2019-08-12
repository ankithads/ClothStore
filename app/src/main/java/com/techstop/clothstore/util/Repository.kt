package com.techstop.clothstore.util

import com.techstop.clothstore.data.CartResponse

object Repository {
    var shoppingCart: MutableMap<String,String> = mutableMapOf()

    fun addToCart(cartResponse: CartResponse) {
        shoppingCart[cartResponse.cartId] = cartResponse.productId

    }

    fun deletefromCart(cartId: String) {
        shoppingCart.remove(cartId)
    }

    fun getCartItems()
    : MutableMap<String, String> {
        return shoppingCart
    }
}