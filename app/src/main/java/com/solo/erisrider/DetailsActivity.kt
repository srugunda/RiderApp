package com.solo.erisrider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intent = intent
        val invoiceNumber = intent.getStringExtra("Invoice_Number")

        textView.text = invoiceNumber

        //set click listener for accept job button
        button_accept_job.setOnClickListener {
            //adds the job to a DB node of accepted jobs based on the invoice number
        }
    }
}
