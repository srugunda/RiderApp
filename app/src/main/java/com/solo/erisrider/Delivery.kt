package com.solo.erisrider

class Delivery (val invoiceNumber : Int, val recipientName : String, val recipientAddress : String,
                val deliveryItems : String, val deliveryQuantity : Int) {

    //secondary constructor
    constructor() : this(0,"","","",0) {}

    //class body


}