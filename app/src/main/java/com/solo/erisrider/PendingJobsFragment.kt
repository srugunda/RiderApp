package com.solo.erisrider

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class PendingJobsFragment : Fragment() {

    val db = Firebase.firestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //put some sample data in firestore that you will retrieve as page is loading up

        val delivery1 = hashMapOf(
            "invoiceNumber" to 8934,
            "recipientName" to "Buffalo Pharmacy",
            "recipientAddress" to "Kabalagala Rd",
            "deliveryItems" to "Ventolin",
            "deliveryQuantity" to "5"
        )

        //add this delivery to the firestore db
        db.collection("Deliveries")
            .add(delivery1)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                        Log.w("TAG", "Error adding document", e)
            }
    }
}
