package com.mhdeveloper.compas.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.mhdeveloper.compas.R
import com.mhdeveloper.compas.controller.dao.CloudController
import com.mhdeveloper.compas.controller.dao.DatabaseStrings
import com.mhdeveloper.compas.model.Ticket

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentDataTicket.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentDataTicket.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentDataTicket : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var dataTicket:Ticket? = null
    private var title:TextView? = null
    private var description:TextView? = null
    private var importance:TextView? = null
    private var userEmmiter:TextView? = null
    private var responsable:TextView? = null
    private var image:ImageView? = null

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
        var view =  inflater.inflate(R.layout.fragment_fragment_data_ticket, container, false)
        this.title = view.findViewById(R.id.title)
        this.description = view.findViewById(R.id.description)
        this.importance = view.findViewById(R.id.importance)
        this.userEmmiter = view.findViewById(R.id.emmiter)
        this.responsable = view.findViewById(R.id.responsable)
        this.image = view.findViewById(R.id.image)
        title!!.text = dataTicket!!.title
        this.description!!.text = dataTicket!!.description?:""
        importance!!.text = Integer.toString(dataTicket!!.importance)
        userEmmiter!!.text = dataTicket!!.tagUserEmmiter
        responsable!!.text = dataTicket!!.tagUserAttended?:""
        CloudController.chargePhoto(image,"${DatabaseStrings.COLLECTION_PHOTOS_TICKETS}${dataTicket!!.uriPhoto}",view.context)


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
    fun setTicket(ticket: Ticket){
        this.dataTicket = ticket
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentDataTicket.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentDataTicket().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
