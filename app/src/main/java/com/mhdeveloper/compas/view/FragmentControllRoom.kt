package com.mhdeveloper.compas.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

import com.mhdeveloper.compas.R
import com.mhdeveloper.compas.controller.managements.MngRooms

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentControllRoom.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentControllRoom.newInstance] factory method to
 * create an instance of this fragment.
 * @author Misael Harinero
 *
 */
class FragmentControllRoom : Fragment(), View.OnClickListener {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    /**
     * variables
     * */
    private var nameRoom: TextView? = null
    private var tagRoom: TextView? = null
    private var buttonGestRoom: ImageButton? = null
    private var buttonGestUser: ImageButton? = null


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

        var view = inflater.inflate(R.layout.fragment_fragment_controll_room, container, false)
        nameRoom = view.findViewById(R.id.nameRoom)
        tagRoom = view.findViewById(R.id.tagRoom)
        //Set Data
        nameRoom!!.text = MngRooms.getRoomSelected().name
        tagRoom!!.text = MngRooms.getRoomSelected().uid
        //Set Button Data
        buttonGestRoom = view.findViewById(R.id.buttonEditRoom)
        buttonGestUser = view.findViewById(R.id.buttonUsers)
        //Listener to Events
        buttonGestUser!!.setOnClickListener(this)
        buttonGestRoom!!.setOnClickListener(this)
        chargeDataRoom()
        return view
    }
    override fun onClick(v: View?) {

        when(v!!.id){
            R.id.buttonEditRoom ->{
                chargeDataRoom()
            }
            R.id.buttonUsers ->{
                var fragment = FragmentUsers()
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.stack,fragment).commit()
                changeColor(2)

            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }
     fun renewData(){
        if (nameRoom != null){
            nameRoom!!.text = MngRooms.getRoomSelected().name
        }
         if (tagRoom != null) {
             tagRoom!!.text = MngRooms.getRoomSelected().uid
         }
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
         * @returned A new instance of fragment FragmentControllRoom.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentControllRoom().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun changeColor(num:Int){
        when(num){
            1->{
                buttonGestUser!!.setBackgroundResource(R.color.clear)
                buttonGestRoom!!.setBackgroundResource(R.color.ColorSelected)
            }
            2->{
                buttonGestUser!!.setBackgroundResource(R.color.ColorSelected)
                buttonGestRoom!!.setBackgroundResource(R.color.clear)
            }
        }
    }
    fun chargeDataRoom(){
        var fragment = FragmentRoomData()
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.stack,fragment).commit()
        changeColor(1)
    }
}
