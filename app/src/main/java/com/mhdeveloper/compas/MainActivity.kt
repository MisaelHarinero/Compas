package com.mhdeveloper.compas

import android.media.Image
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mhdeveloper.compas.view.AdapterRecyclerArea
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    var recycler:RecyclerView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        // Create the Room list from
        recycler = findViewById(R.id.recycler)
        var adapter:AdapterRecyclerArea = AdapterRecyclerArea(this)
        recycler!!.layoutManager = LinearLayoutManager(this)
        recycler!!.adapter = adapter
        nav_view.setNavigationItemSelectedListener(this)
        // Elements for Interface Menu
        val name : TextView = findViewById(R.id.nameUserID)
        val email:TextView = findViewById(R.id.mailUserID)
        name.text="Misael"
        email.text="misaelharinero@gmail.com"
        val exit:ImageButton = findViewById(R.id.logOutButton)
        exit.setOnClickListener(this)
        // Buttons For Interface to change betwenn  fragmednts
        val buttonAdd : ImageButton = findViewById(R.id.buttonAdd)
        val buttonViewTicketsNotAttended : ImageButton = findViewById(R.id.TicketNotAttended)
        val buttonTicketAttended: ImageButton = findViewById(R.id.TicketAttended)
        val buttonShowUser:ImageButton = findViewById(R.id.ViewUser)
        buttonAdd.setOnClickListener(this)
        buttonViewTicketsNotAttended.setOnClickListener(this)
        buttonTicketAttended.setOnClickListener(this)
        buttonShowUser.setOnClickListener(this)


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.logOutButton -> {
                Toast.makeText(this,"Hi",Toast.LENGTH_LONG).show()
            }
            R.id.buttonAdd ->{

            }
            R.id.TicketNotAttended -> {

            }
            R.id.TicketAttended -> {

            }
            R.id.ViewUser ->{

            }
        }
    }
}
