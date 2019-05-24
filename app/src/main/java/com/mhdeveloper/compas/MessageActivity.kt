package com.mhdeveloper.compas

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Timestamp
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ListenerRegistration
import com.mhdeveloper.compas.controller.dao.CloudController
import com.mhdeveloper.compas.controller.dao.DatabaseStrings
import com.mhdeveloper.compas.controller.dao.FirestoreController
import com.mhdeveloper.compas.controller.managements.MngRooms
import com.mhdeveloper.compas.model.Message
import com.mhdeveloper.compas.model.Ticket
import com.mhdeveloper.compas.view.adapters.AdapterMessages
import java.text.SimpleDateFormat

/**
 * @author Misael Harinero
 * Actividad que nos muestra el chat Interno de un ticket
 *
 * */

class MessageActivity : AppCompatActivity(), View.OnClickListener {

    //Funcional Variables
    var fragmentPhotoCharged = false
    var ticketTag:String? = null
    var charged = false
    var mssgs:ArrayList<Message> = ArrayList()
    var event:ListenerRegistration? = null
    private var uri: Uri? = null
    // Final Variable for result Code
    val CODE_PICK_IMG = 12562
    // Interface Elements
    var buttonReturn: Toolbar? = null
    var buttonSendMessage:ImageButton? = null
    var buttonSendImage:ImageButton? = null
    var recycler:RecyclerView? = null
    var text:EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        ticketTag = intent.getStringExtra("ticketTag")
        recycler = findViewById(R.id.recycler)
        var adapter = AdapterMessages(this.mssgs,this)
        event = FirestoreController.getMessagesByTicketTag(ticketTag).addSnapshotListener(EventListener { t, exception ->
            if (t != null && !t.isEmpty){
                for (doc in t.documentChanges){
                    var msg = doc.document.toObject(Message::class.java)
                    if (msg != null){
                        mssgs.add(msg)
                    }
                }
                adapter.notifyDataSetChanged()
                if (mssgs.size>0){
                    recycler!!.layoutManager?.scrollToPosition(mssgs.lastIndex)
                }
            }
        })
        recycler!!.layoutManager = LinearLayoutManager(this)
        recycler!!.adapter = adapter
        buttonReturn = findViewById(R.id.toolbarMssg)
        FirestoreController.db.collection(DatabaseStrings.COLLECTION_TICKETS).document(ticketTag!!).get().addOnCompleteListener {
            if (it.isSuccessful){
                buttonReturn!!.title = it.result!!.toObject(Ticket::class.java)?.title
            }

        }
        buttonReturn!!.setNavigationOnClickListener(View.OnClickListener {
            if (event != null){
                event!!.remove()
            }
            finish()
        })
        text = findViewById(R.id.mmsg)
        buttonSendMessage = findViewById(R.id.send)
        buttonSendMessage!!.setOnClickListener(this)
        buttonSendImage = findViewById(R.id.addImage)
        buttonSendImage!!.setOnClickListener(this)


    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.send ->{
                if (!text!!.text.isEmpty()){
                    FirestoreController.saveMessagesByTicketTag(Message(MngRooms.getUser().tag,ticketTag,Message.TYPE_TEXT,text!!.text.toString(),null,Timestamp.now()))
                    text!!.setText("")
                }
            }
            R.id.addImage->{
                chargePhoto()
            }
        }
    }
    /**
     * Metodo que se encarga de coger una foto de la galeria
     * */
    fun chargePhoto (){
        val getIntent: Intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        getIntent.setType("image/*")
        getIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(getIntent,CODE_PICK_IMG)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //Si el codigo es el de obtencion de Imagen y el resultado es correcto, nos obtiene la Uri de la foto , guarda la foto en la Cloud y  manda el mensaje
        if (requestCode == CODE_PICK_IMG && resultCode == Activity.RESULT_OK){
            uri = data!!.data
            var message=Message(MngRooms.getUser().tag,ticketTag,Message.TYPE_PHOTO,text!!.text.toString(),null,Timestamp.now())
            text!!.setText("")
            var formatterPhoto = SimpleDateFormat("dd_MM_yyyy_H_M_s")
            message.uriPhoto = "${message.tagUser}_${message.tagUser}_${formatterPhoto.format(message.date.toDate())}.jpg"
            //Realizamos la carga de la foto en la vista para poder llamar a evento On COmplete
            val reference =CloudController.getStorage().reference
            reference.child(DatabaseStrings.COLLECTION_PHOTOS + "${DatabaseStrings.COLLECTION_PHOTOS_TICKETS}${message.tagTicket}/${DatabaseStrings.COLLECTION_PHOTOS_MESSAGES}${message.uriPhoto}").putFile(uri!!).addOnCompleteListener( OnCompleteListener {
                if (it.isSuccessful){
                    FirestoreController.saveMessagesByTicketTag(message)
                }

            })
        }
    }

}
