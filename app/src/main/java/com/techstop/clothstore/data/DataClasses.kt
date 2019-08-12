package com.techstop.clothstore.data

data class ProductInfo(

    var productId:   String,
    var name:        String,
    var category:    String,
    var price:       String,
    var oldPrice:    String,
    var stock:       String
)

data class CartRequest (
        var productId: String
)

data class CartResponse (
        var cartId:    String,
        var productId: String
)