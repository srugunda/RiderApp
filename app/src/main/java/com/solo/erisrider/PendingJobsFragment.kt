package com.solo.erisrider

import android.content.Intent
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
import kotlinx.android.synthetic.main.fragment_pending_jobs.*
import kotlinx.android.synthetic.main.fragment_pending_jobs.fragment_cardview1
import kotlinx.android.synthetic.main.fragment_pending_jobs.fragment_cardview2
import kotlinx.android.synthetic.main.fragment_pending_jobs.fragment_cardview3
import kotlinx.android.synthetic.main.fragment_pending_jobs.ftv_invoice_number
import kotlinx.android.synthetic.main.fragment_pending_jobs.ftv_invoice_number_value
import kotlinx.android.synthetic.main.fragment_pending_jobs.ftv_invoice_number_value2
import kotlinx.android.synthetic.main.fragment_pending_jobs.ftv_invoice_number_value3
import kotlinx.android.synthetic.main.fragment_pending_jobs.ftv_recipient_name_value
import kotlinx.android.synthetic.main.fragment_pending_jobs.ftv_recipient_name_value2
import kotlinx.android.synthetic.main.fragment_pending_jobs.ftv_recipient_name_value3
import kotlinx.android.synthetic.main.fragment_pending_jobs.progress_spinner


class PendingJobsFragment : Fragment() {

    val db = Firebase.firestore
    val pendingJobsList = ArrayList<Delivery>()
    val REQUEST_CODE = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideViews() //this is done for a moment so that we dont have blanks displayed during DB call

        //createSampleData()

        retrieveData()

        initializeClickListeners()

    }

    fun initializeClickListeners(){

        fragment_cardview1.setOnClickListener {
            val detailIntent = Intent(getActivity(), DetailsActivity::class.java)
            detailIntent.putExtra("invoice_number",ftv_invoice_number_value.text.toString())
            startActivity(detailIntent)
        }

        fragment_cardview2.setOnClickListener {
            val detailIntent = Intent(getActivity(), DetailsActivity::class.java)
            detailIntent.putExtra("invoice_number",ftv_invoice_number_value2.toString())
            startActivity(detailIntent)
        }

        fragment_cardview3.setOnClickListener {
            val detailIntent = Intent(getActivity(), DetailsActivity::class.java)
            detailIntent.putExtra("invoice_number",ftv_invoice_number_value3.toString())
            startActivity(detailIntent)
        }

    }

    private fun hideViews(){
        fragment_cardview1.setVisibility(View.GONE)
        fragment_cardview2.setVisibility(View.GONE)
        fragment_cardview3.setVisibility(View.GONE)
    }

    private fun showViews(){
        fragment_cardview1.setVisibility(View.VISIBLE)
        fragment_cardview2.setVisibility(View.VISIBLE)
        fragment_cardview3.setVisibility(View.VISIBLE)
    }

    private fun displayPendingJobsList(){
       //loop through the pendingJobsList extracting the elements
        for(item in pendingJobsList){
            if(ftv_invoice_number_value.text.isEmpty()){
                ftv_invoice_number_value.setText(item.invoiceNumber.toString())
                ftv_recipient_name_value.setText(item.recipientName)
            }
            else if(ftv_invoice_number_value2.text.isEmpty()){
                ftv_invoice_number_value2.setText(item.invoiceNumber.toString())
                ftv_recipient_name_value2.setText(item.recipientName)
            }
            else if(ftv_invoice_number_value3.text.isEmpty()){
                ftv_invoice_number_value3.setText(item.invoiceNumber.toString())
                ftv_recipient_name_value3.setText(item.recipientName)
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
                showViews()
                progress_spinner.setVisibility(View.GONE)
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
