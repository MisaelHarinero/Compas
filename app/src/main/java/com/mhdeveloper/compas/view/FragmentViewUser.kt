package com.mhdeveloper.compas.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.mhdeveloper.compas.R
import com.mhdeveloper.compas.controller.dao.FirestoreController
import com.mhdeveloper.compas.controller.managements.MngRooms
import com.mhdeveloper.compas.model.Permission

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentViewUser.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentViewUser.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentViewUser : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {



    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    //Elements to do the action
    private var tagUser:String?= null
    //Interface Elements
    private var rubish:ImageButton? = null
    private var save:ImageButton? = null
    private var textTag:TextView? = null
    private var rolls:Spinner? = null
    private var permissionSelected:String? = null



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
        var view = inflater.inflate(R.layout.fragment_fragment_view_user, container, false)
        this.rubish = view.findViewById(R.id.delete)
        this.save = view.findViewById(R.id.saveChanges)
        this.textTag = view.findViewById(R.id.tag)
        this.rolls = view.findViewById(R.id.permissesSpin)
        rubish!!.setOnClickListener(this)
        save!!.setOnClickListener(this)
        this.textTag!!.text = tagUser
        var arrayAdapter = ArrayAdapter<Permission>(view.context,android.R.layout.simple_spinner_item,MngRooms.getRoomSelected().permissions)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rolls!!.adapter = arrayAdapter
        rolls!!.onItemSelectedListener = this

        return view
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.delete ->{
                var builder = AlertDialog.Builder(v!!.context)
                builder.setTitle(R.string.alert_dialog_delete_user_title)
                builder.setMessage(R.string.alert_dialog_delete_user_mssg)
                builder.setPositiveButton("Accept",DialogInterface.OnClickListener { dialog, which ->
                    deleteUser()
                    chargeUpFragment()

                })
                builder.setNegativeButton("Decline", DialogInterface.OnClickListener { dialog, which ->
                   Toast.makeText(v.context,"Action Cancelled",Toast.LENGTH_LONG)
                    chargeUpFragment()
                })
                builder.create().show()

            }
            R.id.saveChanges ->{
                if (permissionSelected != null){
                    MngRooms.getRoomSelected().permissesUser[tagUser] = permissionSelected
                    FirestoreController.saveRoom(MngRooms.getRoomSelected())
                }
            }
        }
    }
    fun chargeUpFragment(){
        var fragment = FragmentUsers()
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.stack,fragment).commit()
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
         * @return A new instance of fragment FragmentViewUser.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentViewUser().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun setUser(tag:String){
        this.tagUser = tag
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        permissionSelected = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        permissionSelected = MngRooms.getRoomSelected().permissions[position].name
    }
    fun deleteUser(){
        MngRooms.getRoomSelected().members.remove(tagUser)
        MngRooms.getRoomSelected().permissesUser.remove(tagUser)
        FirestoreController.cleanForUser(tagUser,MngRooms.getRoomSelected().uid)
        FirestoreController.saveRoom(MngRooms.getRoomSelected())
    }

}
