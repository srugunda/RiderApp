package com.solo.erisrider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    val db = Firebase.firestore
    var delivery = Delivery()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar_detail_screen)

        //populate the view with data from firestore based on the invoice number.
        val intent = intent
        val selectedInvoiceNumber = intent.getStringExtra("invoice_number")
        Log.d("DETAIL ACTIVITY TAG", "The invoice number is $selectedInvoiceNumber")

        db.collection("Deliveries")
            .whereEqualTo("invoiceNumber", selectedInvoiceNumber.toInt())
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    //val deliveryObj = document.toObject<Delivery>()
                    delivery = document.toObject()
                    //Log.d("DETAIL ACTIVITY TAG", "${deliveryObject.invoiceNumber}, ${deliveryObject.recipientName}, " +
                    //        "${deliveryObject.recipientAddress}, ${deliveryObject.deliveryItems}, ${deliveryObject.deliveryQuantity}")
                    updateDetailScreen(delivery)
                    detail_activity_relative_layout.visibility = View.VISIBLE
                    progress_bar_detail_activity.visibility = View.GONE
                    break
                }
            }
            .addOnFailureListener { exception ->
                Log.d("DETAIL ACTIVITY TAG", "Error getting documents: ", exception)
            }


        button_accept_job.setOnClickListener {
            // Add the job to a firestore collection of accepted jobs based on the invoice number
            db.collection("AcceptedJobs")
                .add(delivery)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }


            // Go to the accepted jobs fragment
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("fragment_position", "1")
            startActivity(intent)

            //TODO (#3) Removes that job from the pending jobs fragment
        }
    }

    private fun updateDetailScreen(deliveryObj : Delivery){
        Log.d("DETAIL ACTIVITY TAG", "Now inside the updateDetailScreen method")
        details_invoice_number_value.text = deliveryObj.invoiceNumber.toString()
        details_recipient_name_value.text = deliveryObj.recipientName
        details_recipient_address_value.text = deliveryObj.recipientAddress
        details_delivery_items_value.text = deliveryObj.deliveryItems
        details_delivery_quantity_value.text = deliveryObj.deliveryQuantity.toString()
    }
}
