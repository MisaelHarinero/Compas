package com.mhdeveloper.compas.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.Timestamp

import com.mhdeveloper.compas.R
import com.mhdeveloper.compas.controller.dao.CloudController
import com.mhdeveloper.compas.controller.dao.DatabaseStrings
import com.mhdeveloper.compas.controller.dao.FirestoreController
import com.mhdeveloper.compas.controller.managements.MngRooms
import com.mhdeveloper.compas.model.Ticket
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CreationTicket.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CreationTicket.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CreationTicket : Fragment(), View.OnClickListener {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    /**
    * Interface elements
    */
    private var imp1:Button?= null
    private var imp2:Button?= null
    private var imp3:Button?= null
    private var imp4:Button?= null
    private var imp5:Button?= null
    private var setImg:Button?= null
    private var create:Button?= null
    private var importanceSelected = 3
    private var title:EditText? = null
    private var description:EditText? = null
    private var imageSelected:ImageView? = null
    private var uri:Uri? = null
    private val CODE_PICK_IMG = 2000





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.im1->{
                changeImportance(1)
            }
            R.id.im2->{
                changeImportance(2)
            }
            R.id.im3->{
                changeImportance(3)
            }
            R.id.im4->{
                changeImportance(4)
            }
            R.id.im5->{
                changeImportance(5)
            }
            R.id.addImage ->{
                chargePhoto()
            }
            R.id.createTicket->{
                if (!title!!.text.toString().equals("")&&!description!!.text.toString().equals("")){
                    var date = Timestamp.now()
                    var formatter = SimpleDateFormat("dd-MM-yyyy_H_m")
                    var formatterPhoto = SimpleDateFormat("dd_MM_yyyy")
                    var ticket = Ticket("${MngRooms.getRoomSelected().uid}${MngRooms.getUser().tag}${formatter.format(date.toDate())}",MngRooms.getRoomSelected().uid,importanceSelected,title!!.text.toString(),description!!.text.toString(),MngRooms.getUser().tag,date)
                    if (uri != null){
                        ticket.uriPhoto = "${MngRooms.getRoomSelected().uid}${MngRooms.getUser().tag}${formatterPhoto.format(ticket.date.toDate())}/ticketPhoto.jpg"
                        CloudController.savePhoto(uri,"${DatabaseStrings.COLLECTION_PHOTOS_TICKETS}${ticket.uriPhoto}")
                    }
                    FirestoreController.saveTicket(ticket)
                    this.title!!.setText("")
                    this.description!!.setText("")
                    this.imageSelected!!.setImageURI(null)
                    this.importanceSelected = 3
                    Toast.makeText(v.context,"SAVE CORRETLY",Toast.LENGTH_LONG)
                }else{
                    Toast.makeText(v.context,"YOU NEED WRITE TITLE AND DESCRIPTION",Toast.LENGTH_LONG)
                }
            }
        }
    }

    fun chargePhoto (){
        val getIntent:Intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        getIntent.setType("image/*")
        getIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(getIntent,CODE_PICK_IMG)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_creation_ticket, container, false)
        imp1 = view.findViewById(R.id.im1)
        imp2 = view.findViewById(R.id.im2)
        imp3 = view.findViewById(R.id.im3)
        imp4 = view.findViewById(R.id.im4)
        imp5 = view.findViewById(R.id.im5)
        imp1!!.setOnClickListener(this)
        imp2!!.setOnClickListener(this)
        imp3!!.setOnClickListener(this)
        imp4!!.setOnClickListener(this)
        imp5!!.setOnClickListener(this)
        this.title = view.findViewById(R.id.titleTicket)
        this.description = view.findViewById(R.id.description)
        this.imageSelected = view.findViewById(R.id.image)
        this.setImg = view.findViewById(R.id.addImage)
        this.setImg!!.setOnClickListener(this)
        this.create = view.findViewById(R.id.createTicket)
        this.create!!.setOnClickListener(this)
        changeImportance(importanceSelected)


        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreationTicket.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreationTicket().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun changeImportance(num:Int){
        this.importanceSelected = num
        when(num){
            1 ->{
             imp1!!.setBackgroundResource(R.color.ColorSelected)
             imp2!!.setBackgroundResource(R.color.ColorNotSelected)
             imp3!!.setBackgroundResource(R.color.ColorNotSelected)
             imp4!!.setBackgroundResource(R.color.ColorNotSelected)
             imp5!!.setBackgroundResource(R.color.ColorNotSelected)

            }
            2 ->{
                imp1!!.setBackgroundResource(R.color.ColorNotSelected)
                imp2!!.setBackgroundResource(R.color.ColorSelected)
                imp3!!.setBackgroundResource(R.color.ColorNotSelected)
                imp4!!.setBackgroundResource(R.color.ColorNotSelected)
                imp5!!.setBackgroundResource(R.color.ColorNotSelected)

            }
            3 ->{
                imp1!!.setBackgroundResource(R.color.ColorNotSelected)
                imp2!!.setBackgroundResource(R.color.ColorNotSelected)
                imp3!!.setBackgroundResource(R.color.ColorSelected)
                imp4!!.setBackgroundResource(R.color.ColorNotSelected)
                imp5!!.setBackgroundResource(R.color.ColorNotSelected)

            }
            4    ->{
                imp1!!.setBackgroundResource(R.color.ColorNotSelected)
                imp2!!.setBackgroundResource(R.color.ColorNotSelected)
                imp3!!.setBackgroundResource(R.color.ColorNotSelected)
                imp4!!.setBackgroundResource(R.color.ColorSelected)
                imp5!!.setBackgroundResource(R.color.ColorNotSelected)

            }
            5 ->{
                imp1!!.setBackgroundResource(R.color.ColorNotSelected)
                imp2!!.setBackgroundResource(R.color.ColorNotSelected)
                imp3!!.setBackgroundResource(R.color.ColorNotSelected)
                imp4!!.setBackgroundResource(R.color.ColorNotSelected)
                imp5!!.setBackgroundResource(R.color.ColorSelected)

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CODE_PICK_IMG && resultCode == Activity.RESULT_OK){
            uri = data!!.data
            imageSelected!!.setImageURI(uri)
        }
    }

}
