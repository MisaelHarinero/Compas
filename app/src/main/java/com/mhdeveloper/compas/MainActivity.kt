package com.mhdeveloper.compas

import android.app.Activity
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
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mhdeveloper.compas.controller.dao.AuthController
import com.mhdeveloper.compas.controller.dao.CloudController
import com.mhdeveloper.compas.controller.dao.DatabaseStrings
import com.mhdeveloper.compas.controller.dao.FirestoreController
import com.mhdeveloper.compas.controller.managements.MngRooms
import com.mhdeveloper.compas.controller.notifications.NtNotificationNewTickets
import com.mhdeveloper.compas.controller.notifications.NtOneSelected
import com.mhdeveloper.compas.controller.notifications.NtRechargeAdapterData
import com.mhdeveloper.compas.model.Ticket
import com.mhdeveloper.compas.view.*
import com.mhdeveloper.compas.view.adapters.AdapterRecyclerArea
import com.mhdeveloper.compas.view.adapters.AdapterRecyclerTicketAttendendedByMe
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

/**
 * Clase que se encarga del control de la pantalla principal . La cual se encarga de la carga de datos, de el control de las Rooms, es la Actividad Principal de Nuestra Aplicación.
 * @author Misael Harinero
 * */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
    NotRoomFragment.OnFragmentInteractionListener, CreateRoomFragment.OnFragmentInteractionListener,
    NotificationsFragment.OnFragmentInteractionListener, CreationTicket.OnFragmentInteractionListener,
    FragmentControllRoom.OnFragmentInteractionListener,
    FragmentUsers.OnFragmentInteractionListener, FragmentViewUser.OnFragmentInteractionListener,
    FragmentViewTickets.OnFragmentInteractionListener, ViewAllTickets.OnFragmentInteractionListener,
    FragmentMyTikects.OnFragmentInteractionListener, FragmentVeiwTicketsAttended.OnFragmentInteractionListener,
    FragmentDataUserChange.OnFragmentInteractionListener, FragmentDataTicket.OnFragmentInteractionListener ,
    FragmentRoomData.OnFragmentInteractionListener{
    // Not used
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    var recycler: RecyclerView? = null
    var chargedFirst: Boolean = false
    private var CHANNEL_ID: String? = null
    var imageId: ImageView? = null
    val CODE_PICK_IMG = 1234
    //Interface Elements
    var buttonAdd: ImageButton? = null
    var buttonViewTicketsNotAttended: ImageButton? = null
    var buttonShowUser: ImageButton? = null


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
        MngRooms.chargeRooms()
        NtOneSelected.setActivity(this)
        NtNotificationNewTickets.setActivity(this)
        // Create the Room list from
        recycler = findViewById(R.id.recycler)
        var adapter: AdapterRecyclerArea =
            AdapterRecyclerArea(this, this)
        recycler!!.layoutManager = LinearLayoutManager(this)
        recycler!!.adapter = adapter
        nav_view.setNavigationItemSelectedListener(this)
        // Elements for Interface Menu
        val name: TextView = findViewById(R.id.nameUserID)
        val email: TextView = findViewById(R.id.mailUserID)
        val tag: TextView = findViewById(R.id.tagUser)
        this.imageId = findViewById(R.id.imageId)
        chargePhotoUser()
        name.text = "${MngRooms.getUser().name} ${MngRooms.getUser().surname}"
        email.text = MngRooms.getUser().email
        tag.text = MngRooms.getUser().tag
        val exit: ImageButton = findViewById(R.id.logOutButton)
        exit.setOnClickListener(this)
        val buttonAddRoom: ImageButton = findViewById(R.id.addRoom)
        val notificationMenu: ImageButton = findViewById(R.id.notificationMenu)
        buttonAddRoom.setOnClickListener(this)
        notificationMenu.setOnClickListener(this)
        // Buttons For Interface to change betwenn  fragmednts
        this.buttonAdd = findViewById(R.id.buttonAdd)
        this.buttonViewTicketsNotAttended = findViewById(R.id.TicketNotAttended)
        this.buttonShowUser = findViewById(R.id.ViewUser)

        buttonAdd!!.setOnClickListener(this)
        buttonViewTicketsNotAttended!!.setOnClickListener(this)
        buttonShowUser!!.setOnClickListener(this)
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

            R.id.changeAvatar -> {
                var fragment = FragmentDataUserChange()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * Method that charge A Fragment First Time when we select a Room
     * */
    fun chargeRoom() {
        if (MngRooms.getPermissions().isWriteTk) {
            var fragment = CreationTicket()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
            selectButton(1)
        } else {
            var fragment = FragmentViewTickets()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
            selectButton(2)
        }
        drawer_layout.closeDrawer(GravityCompat.START)

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
        when (v!!.id) {
            R.id.logOutButton -> {
                var intent = Intent(this, LogInActivity::class.java)
                AuthController().logOut()
                startActivity(intent)
                finish()
                MngRooms.clear()
            }
            R.id.buttonAdd -> {
                if (MngRooms.getRoomSelected() != null && MngRooms.getPermissions() != null) {
                    if (MngRooms.getPermissions().isWriteTk) {
                        var fragment = CreationTicket()
                        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                        selectButton(1)
                    } else {
                        Snackbar.make(v, R.string.not_permissions, Snackbar.LENGTH_LONG)
                    }
                }

            }
            R.id.TicketNotAttended -> {
                if (MngRooms.getRoomSelected() != null && MngRooms.getPermissions() != null) {
                    var fragment = FragmentViewTickets()
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                    selectButton(2)
                }
            }

            R.id.ViewUser -> {
                if (MngRooms.getRoomSelected() != null && MngRooms.getPermissions() != null) {
                    var fragment = FragmentControllRoom()
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                    selectButton(3)
                }
            }
            R.id.addRoom -> {
                var fragment = CreateRoomFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            R.id.notificationMenu -> {
                var fragment = NotificationsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                drawer_layout.closeDrawer(GravityCompat.START)
            }
        }
    }

    /**
     * Method to chargea room in case that the user have got any one, but if he havent got any charge the not Room Fragment
     * */
    fun firstTimeCharge() {
        if (!chargedFirst || MngRooms.getRoomSelected() == null) {
            if (MngRooms.getRoomSelected() == null) {
                var fragment = NotRoomFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
            } else {
                chargeRoom()
                chargedFirst = true
            }
        }


    }

    /**
     * Method to post a Notification
     * */
    fun notificationNewTicket(tk: Ticket) {
        var formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
        var notification = NotificationCompat.Builder(this, CHANNEL_ID!!)
            .setContentTitle(
                "${tk.title} - ${if (tk.date != null) {
                    formatter.format(tk.date.toDate())
                } else {
                }}"
            )
            .setSubText(tk.description)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(tk.importance)
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify((Math.floor(Math.random() * 19999).toInt()), notification.build())
        }
    }

    fun createChanellNotification() {
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

    /**
     * Method to do an Intent to Message Activity
     * */
    fun chargeMssgActivity(ticketTag: String) {
        if (ticketTag != null) {
            var intent = Intent(this, MessageActivity::class.java)
            intent.putExtra("ticketTag", ticketTag)
            startActivity(intent)
        }
    }

    /**
     * Show PopUp Menu for My Tickets Layout if you do a long click
     * */
    fun showPopUp(ticket: Ticket, view: View) {
        var menu = PopupMenu(this, view)
        menu.menuInflater.inflate(R.menu.menu_ticket, menu.menu)
        menu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
            when (it!!.itemId) {
                R.id.viewData -> {
                    var fragment = FragmentDataTicket()
                    fragment.setTicket(ticket)
                    supportFragmentManager.beginTransaction().replace(R.id.stack, fragment).commit()
                    true
                }
                R.id.delete -> {
                    Toast.makeText(this, "Delete ${ticket.tag} completed", Toast.LENGTH_LONG)
                    if (ticket.uriPhoto != null) {
                        CloudController.deleteMediaTicket("${DatabaseStrings.COLLECTION_PHOTOS_TICKETS}${ticket.uriPhoto}")
                    }
                    FirestoreController.deleteATicket(ticket.tag)
                    NtRechargeAdapterData()
                    true
                }
                else -> {
                    false
                }
            }
        })
        menu.show()

    }

    /**
     * Method to show Menu for Ticket Layout Attended if you do a long click
     * */
    fun showPopUpReader(ticket: Ticket, view: View, adapter: AdapterRecyclerTicketAttendendedByMe) {
        var menu = PopupMenu(this, view)
        menu.menuInflater.inflate(R.menu.menu_ticket_reader, menu.menu)
        menu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
            when (it!!.itemId) {
                R.id.viewData -> {
                    var fragment = FragmentDataTicket()
                    fragment.setTicket(ticket)
                    supportFragmentManager.beginTransaction().replace(R.id.stack, fragment).commit()
                    true
                }
                R.id.finish -> {
                    Toast.makeText(this, "Finish ${ticket.tag}", Toast.LENGTH_LONG)
                    ticket.isFinished = true
                    FirestoreController.saveTicket(ticket)
                    adapter.notifyDataSetChanged()
                    true
                }
                else -> {
                    false
                }
            }
        })
        menu.show()

    }
    /**
     * Method to show Menu for Ticket Layout Attended if you do a long click
     * */
    fun showPopUpReaderClosed(ticket: Ticket, view: View, adapter: AdapterRecyclerTicketAttendendedByMe) {
        var menu = PopupMenu(this, view)
        menu.menuInflater.inflate(R.menu.menu_ticket_closed, menu.menu)
        menu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
            when (it!!.itemId) {
                R.id.viewData -> {
                    var fragment = FragmentDataTicket()
                    fragment.setTicket(ticket)
                    supportFragmentManager.beginTransaction().replace(R.id.stack, fragment).commit()
                    true
                }
                R.id.finish -> {
                    Toast.makeText(this, "Open ${ticket.tag}", Toast.LENGTH_LONG)
                    ticket.isFinished = false
                    adapter.notifyDataSetChanged()
                    FirestoreController.saveTicket(ticket)
                    true
                }
                else -> {
                    false
                }
            }
        })
        menu.show()

    }


    fun chargePhotoUser() {
        if (MngRooms.getUser().imageRoute != null) {
            CloudController.chargePhoto(
                imageId,
                "${DatabaseStrings.COLLECTION_PHOTOS_USERS}${MngRooms.getUser().imageRoute}",
                this
            )
        }
    }

    fun selectButton(num: Int) {
        when (num) {
            1 -> {
                this.buttonAdd!!.setBackgroundResource(R.color.ColorSelected)
                this.buttonViewTicketsNotAttended!!.setBackgroundResource(R.color.clear)
                this.buttonShowUser!!.setBackgroundResource(R.color.clear)
            }
            2 -> {
                this.buttonAdd!!.setBackgroundResource(R.color.clear)
                this.buttonViewTicketsNotAttended!!.setBackgroundResource(R.color.ColorSelected)
                this.buttonShowUser!!.setBackgroundResource(R.color.clear)
            }
            3 -> {
                this.buttonAdd!!.setBackgroundResource(R.color.clear)
                this.buttonViewTicketsNotAttended!!.setBackgroundResource(R.color.clear)
                this.buttonShowUser!!.setBackgroundResource(R.color.ColorSelected)
            }
        }
    }
    fun changePhotoUser(uri:Uri){
        this.imageId!!.setImageURI(uri)
    }


}
