package com.mhdeveloper.compas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ListenerRegistration
import com.mhdeveloper.compas.controller.dao.FirestoreController
import com.mhdeveloper.compas.controller.managements.MngRooms
import com.mhdeveloper.compas.model.Message
import com.mhdeveloper.compas.view.adapters.AdapterMessages

class MessageActivity : AppCompatActivity(), View.OnClickListener {

    var fragmentPhotoCharged = false
    var ticketTag:String? = null
    var charged = false
    var buttonReturn: Toolbar? = null
    var buttonSendMessage:ImageButton? = null
    var buttonSendImage:ImageButton? = null
    var mssgs:ArrayList<Message> = ArrayList()
    var recycler:RecyclerView? = null
    var event:ListenerRegistration? = null
    var text:EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        ticketTag = intent.getStringExtra("ticketTag")
        recycler = findViewById(R.id.recycler)
        var adapter = AdapterMessages(this.mssgs)
        event = FirestoreController.getMessagesByTicketTag(ticketTag).addSnapshotListener(EventListener { t, firebaseFirestoreException ->
            if (t != null && !t.isEmpty){
                for (doc in t.documentChanges){
                    var msg = doc.document.toObject(Message::class.java)
                    if (msg != null){
                        mssgs.add(msg)
                    }
                }
                adapter.notifyDataSetChanged()
            }
        })
        recycler!!.layoutManager = LinearLayoutManager(this)
        recycler!!.adapter = adapter
        buttonReturn = findViewById(R.id.toolbarMssg)
        buttonReturn!!.setNavigationOnClickListener(View.OnClickListener {
            if (event != null){
                event!!.remove()
            }
            finish()
        })
        text = findViewById(R.id.mmsg)
        buttonSendMessage = findViewById(R.id.send)
        buttonSendMessage!!.setOnClickListener(this)


    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.send ->{
                if (!text!!.text.isEmpty()){
                    FirestoreController.saveMessagesByTicketTag(Message(MngRooms.getUser().tag,ticketTag,Message.TYPE_TEXT,text!!.text.toString(),null,Timestamp.now()))
                    text!!.setText("")
                }
            }
        }
    }

}
