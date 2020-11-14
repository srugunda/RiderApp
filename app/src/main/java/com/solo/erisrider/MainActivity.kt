package com.solo.erisrider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import kotlinx.android.synthetic.main.activity_main_tablayout.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tablayout)

        /*
        cardview_1.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("Invoice_Number", invoice_number.text.toString())
            startActivity(intent)
        }*/

        setSupportActionBar(toolbar)

        tab_layout.addTab(tab_layout.newTab().setText(R.string.pending_jobs_label))
        tab_layout.addTab(tab_layout.newTab().setText(R.string.accepted_jobs_label))

        // Set the tabs to fill the entire layout.
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Use PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        val adapter = PagerAdapter(this, supportFragmentManager, tab_layout.tabCount)
        view_pager.adapter = adapter

        // Setting a listener for clicks.
        view_pager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                view_pager.setCurrentItem(tab.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}
