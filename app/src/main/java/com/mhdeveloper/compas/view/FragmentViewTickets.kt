package com.mhdeveloper.compas.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.mhdeveloper.compas.R
import com.mhdeveloper.compas.controller.managements.MngRooms
import kotlinx.android.synthetic.main.fragment_create_room.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentViewTickets.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentViewTickets.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentViewTickets : Fragment(), View.OnClickListener {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null


    //Interface Elements
    private var buttonAll: Button? = null
    private var buttonMy: Button? = null
    private var buttonAttended: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_fragment_view_tickets, container, false)
        buttonAll  = view.findViewById(R.id.viewAll)
        buttonMy = view.findViewById(R.id.myTickets)
        buttonAttended = view.findViewById(R.id.AttendByMe)
        if (MngRooms.getPermissions().isWriteTk){
            buttonMy!!.setOnClickListener(this)
            chargeMyTickets()
            changeColors(2)
            if (!MngRooms.getPermissions().isReadTk){
                buttonAll!!.visibility = Button.GONE
                buttonAttended!!.visibility = Button.GONE
            }else{
                buttonAll!!.setOnClickListener(this)
                buttonAttended!!.setOnClickListener(this)
            }
        }else if(MngRooms.getPermissions().isReadTk){
            buttonAll!!.setOnClickListener(this)
            buttonAttended!!.setOnClickListener(this)
            buttonMy!!.visibility = Button.GONE
            chargeAllTicket()
            changeColors(1)
        }

        return view
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.viewAll ->{
                chargeAllTicket()
                changeColors(1)
            }
            R.id.myTickets ->{
                chargeMyTickets()
                changeColors(2)

            }
            R.id.AttendByMe ->{
                var fragment = FragmentVeiwTicketsAttended()
                fragmentManager!!.beginTransaction().replace(R.id.stack,fragment).commit()
                changeColors(3)
            }
        }
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
         * @returned A new instance of fragment FragmentViewTickets.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentViewTickets().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun chargeAllTicket(){
        var fragment = ViewAllTickets()
        fragmentManager!!.beginTransaction().replace(R.id.stack,fragment).commit()
    }
    fun chargeMyTickets(){
        var fragment = FragmentMyTikects()
        fragmentManager!!.beginTransaction().replace(R.id.stack,fragment).commit()
    }
    fun changeColors( id:Int){
        when(id){
            1 ->{
               buttonAll!!.setBackgroundResource(R.color.ColorSelected)
               buttonAttended!!.setBackgroundResource(R.color.ColorNotSelected)
               buttonMy!!.setBackgroundResource(R.color.ColorNotSelected)

            }
            2 ->{

                buttonAll!!.setBackgroundResource(R.color.ColorNotSelected)
                buttonMy!!.setBackgroundResource(R.color.ColorSelected)
                buttonAttended!!.setBackgroundResource(R.color.ColorNotSelected)

            }
            3 ->{

                buttonAll!!.setBackgroundResource(R.color.ColorNotSelected)
                buttonMy!!.setBackgroundResource(R.color.ColorNotSelected)
                buttonAttended!!.setBackgroundResource(R.color.ColorSelected)
            }
        }
    }
}
