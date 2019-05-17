package com.mhdeveloper.compas.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.mhdeveloper.compas.MainActivity

import com.mhdeveloper.compas.R
import com.mhdeveloper.compas.controller.dao.FirestoreController
import com.mhdeveloper.compas.controller.managements.MngRooms
import com.mhdeveloper.compas.model.Ticket
import com.mhdeveloper.compas.view.adapters.AdapterRecyclerTicketAll
import com.mhdeveloper.compas.view.adapters.AdapterRecyclerTicketAttendendedByMe

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentVeiwTicketsAttended.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentVeiwTicketsAttended.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentVeiwTicketsAttended : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    //Data Elements
    private var tickets:ArrayList<Ticket> = ArrayList()
    private var eventData: ListenerRegistration?  = null

    //Interface ELements
    private var recycler: RecyclerView? = null

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
        var view =  inflater.inflate(R.layout.fragment_fragment_veiw_tickets_attended, container, false)
        recycler= view.findViewById(R.id.recycler)
        var adapter = AdapterRecyclerTicketAttendendedByMe(this.tickets,view.context,activity as MainActivity)
        eventData = FirestoreController.getTicketsAttendedByMe(MngRooms.getRoomSelected().uid,MngRooms.getUser().tag).addSnapshotListener(
            EventListener { t: QuerySnapshot?, firebaseFirestoreException ->
            if (t != null){
                tickets.clear()
                for (doc in t.documents){
                    var ticket = doc.toObject(Ticket::class.java)
                    if (ticket != null && ticket.tagUserEmmiter != MngRooms.getUser().tag){
                        this.tickets.add(ticket)
                    }
                }
                adapter.notifyDataSetChanged()
            }
        })
        recycler!!.layoutManager = LinearLayoutManager(view.context)
        recycler!!.adapter = adapter
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
         * @return A new instance of fragment FragmentVeiwTicketsAttended.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentVeiwTicketsAttended().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
