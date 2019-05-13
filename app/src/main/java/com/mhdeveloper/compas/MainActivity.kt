package com.mhdeveloper.compas

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mhdeveloper.compas.controller.dao.AuthController
import com.mhdeveloper.compas.controller.dao.FirestoreController
import com.mhdeveloper.compas.controller.managements.MngRooms
import com.mhdeveloper.compas.controller.notifications.NtNotificationNewTickets
import com.mhdeveloper.compas.controller.notifications.NtOneSelected
import com.mhdeveloper.compas.model.Ticket
import com.mhdeveloper.compas.view.*
import com.mhdeveloper.compas.view.adapters.AdapterRecyclerArea
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener ,NotRoomFragment.OnFragmentInteractionListener,CreateRoomFragment.OnFragmentInteractionListener,NotificationsFragment.OnFragmentInteractionListener,CreationTicket.OnFragmentInteractionListener,FragmentControllRoom.OnFragmentInteractionListener,
FragmentUsers.OnFragmentInteractionListener, FragmentViewUser.OnFragmentInteractionListener,FragmentViewTickets.OnFragmentInteractionListener,ViewAllTickets.OnFragmentInteractionListener,FragmentMyTikects.OnFragmentInteractionListener{
    // Not used
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    var recycler:RecyclerView?= null
    var chargedFirst:Boolean = false
    private var CHANNEL_ID:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        //Instance Elements Data
        createChanellNotification()
        FirestoreController.instanceFirestore()
        FirestoreController.getSnapshotForTickets(MngRooms.getUser().tag)
        MngRooms.chargeRooms()
        NtOneSelected.setActivity(this)
        NtNotificationNewTickets.setActivity(this)
        // Create the Room list from
        recycler = findViewById(R.id.recycler)
        var adapter: AdapterRecyclerArea =
            AdapterRecyclerArea(this,this)
        recycler!!.layoutManager = LinearLayoutManager(this)
        recycler!!.adapter = adapter
        nav_view.setNavigationItemSelectedListener(this)
        // Elements for Interface Menu
        val name : TextView = findViewById(R.id.nameUserID)
        val email:TextView = findViewById(R.id.mailUserID)
        val tag :TextView = findViewById(R.id.tagUser)
        name.text="${MngRooms.getUser().name} ${MngRooms.getUser().surname}"
        email.text=MngRooms.getUser().email
        tag.text = MngRooms.getUser().tag
        val exit:ImageButton = findViewById(R.id.logOutButton)
        exit.setOnClickListener(this)
        val buttonAddRoom :ImageButton = findViewById(R.id.addRoom)
        val notificationMenu:ImageButton  = findViewById(R.id.notificationMenu)
        buttonAddRoom.setOnClickListener(this)
        notificationMenu.setOnClickListener(this)
        // Buttons For Interface to change betwenn  fragmednts
        val buttonAdd : ImageButton = findViewById(R.id.buttonAdd)
        val buttonViewTicketsNotAttended : ImageButton = findViewById(R.id.TicketNotAttended)
        val buttonTicketAttended: ImageButton = findViewById(R.id.TicketAttended)
        val buttonShowUser:ImageButton = findViewById(R.id.ViewUser)

        buttonAdd.setOnClickListener(this)
        buttonViewTicketsNotAttended.setOnClickListener(this)
        buttonTicketAttended.setOnClickListener(this)
        buttonShowUser.setOnClickListener(this)
        //In case not Room for that user we charge fragment to create one


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
    fun chargeRoom(){
        if (MngRooms.getPermissions().isWriteTk){
            var fragment = CreationTicket()
            supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
        }else{
            var fragment = FragmentViewTickets()
            supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
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
                var intent = Intent(this,LogInActivity::class.java)
                AuthController().logOut()
                startActivity(intent)
                finish()
                MngRooms.clear()
            }
            R.id.buttonAdd ->{
                if (MngRooms.getPermissions().isWriteTk){
                    var fragment = CreationTicket()
                    supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
                }else{
                    Snackbar.make(v,R.string.not_permissions,Snackbar.LENGTH_LONG)
                }

            }
            R.id.TicketNotAttended -> {
                var fragment = FragmentViewTickets()
                supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()

            }
            R.id.TicketAttended -> {

            }
            R.id.ViewUser ->{
                var fragment = FragmentControllRoom()
                supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()

            }
            R.id.addRoom -> {
                var fragment = CreateRoomFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
            }
            R.id.notificationMenu ->{
                var fragment = NotificationsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
            }
        }
    }
    fun firstTimeCharge(){
       if (!chargedFirst){
           if (MngRooms.getRoomSelected() == null){
               var fragment = NotRoomFragment()
               supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
           }else{
               chargeRoom()
               chargedFirst = true
           }
       }


    }
    fun notificationNewTicket(tk:Ticket){
        var formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
        var notification = NotificationCompat.Builder(this,CHANNEL_ID!!)
            .setContentTitle("${tk.title} - ${if(tk.date != null){formatter.format(tk.date.toDate())}else{}}")
            .setSubText(tk.description)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(tk.importance)
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify((Math.floor(Math.random()*19999).toInt()), notification.build())
        }
    }
    fun createChanellNotification(){
        CHANNEL_ID = getString(R.string.channel_id)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = getString(R.string.channel_notification_tickets)
                val descriptionText = getString(R.string.channel_description_notification_tickets)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

    }
    fun chargeMssgActivity(ticketTag:String){
        if (ticketTag !=null){
                var intent = Intent(this,MessageActivity::class.java)
                intent.putExtra("ticketTag",ticketTag)
                startActivity(intent)
        }
    }





}
