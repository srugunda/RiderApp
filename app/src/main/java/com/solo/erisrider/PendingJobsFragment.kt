package com.solo.erisrider

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_pending_jobs.*


class PendingJobsFragment : Fragment() {

    val db = Firebase.firestore
    val pendingJobsList = ArrayList<Delivery>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createSampleData() //create sample data and add it to firestore

        retrieveData()

    }

    private fun displayPendingJobsList(){
       //loop through the pendingJobsList extracting the elements
        for(item in pendingJobsList){
            if(ftv_invoice_number_value.text.isEmpty()){
                ftv_invoice_number_value.setText(item.invoiceNumber.toString())
            }
            else if(ftv_invoice_number_value2.text.isEmpty()){
                ftv_invoice_number_value.setText(item.invoiceNumber.toString())
            }
            else if(ftv_invoice_number_value3.text.isEmpty()){
                ftv_invoice_number_value.setText(item.invoiceNumber.toString())
            }
        }
    }

    private fun retrieveData() {
        db.collection("Deliveries")
            .get()
            .addOnSuccessListener { result ->
                for(document in result){
                    val deliveryObject = document.toObject<Delivery>()
                    pendingJobsList.add(deliveryObject)
                }
                displayPendingJobsList()
            }
            .addOnFailureListener { exception ->
                Log.d("ERROR TAG", "Error getting documents: ", exception)
            }
    }

    private fun createSampleData() {
        val delivery2 = hashMapOf(
            "invoiceNumber" to 5534,
            "recipientName" to "Abacus Pharmacy",
            "recipientAddress" to "Wandegeya Rd",
            "deliveryItems" to "Paracetamol",
            "deliveryQuantity" to 15
        )

        val delivery3 = hashMapOf(
            "invoiceNumber" to 8987,
            "recipientName" to "C&A Pharmacy",
            "recipientAddress" to "Kansanga",
            "deliveryItems" to "Asprin",
            "deliveryQuantity" to 10
        )

        //add this delivery data to the firestore db
        db.collection("Deliveries")
            .add(delivery2)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }

        db.collection("Deliveries")
            .add(delivery3)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }
}
