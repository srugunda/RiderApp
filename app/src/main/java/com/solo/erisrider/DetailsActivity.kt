package com.solo.erisrider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //populate the view with data from firestore based on the invoice number.

        //set click listener for accept job button
        button_accept_job.setOnClickListener {
            //adds the job to a firestore collection of accepted jobs based on the invoice number



            //closes the view and goes to the accepted jobs fragment
            //removes that job from the pending jobs fragment
        }
    }
}
