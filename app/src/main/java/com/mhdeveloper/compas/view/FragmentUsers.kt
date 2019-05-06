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
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mhdeveloper.compas.R
import com.mhdeveloper.compas.controller.dao.FirestoreController
import com.mhdeveloper.compas.controller.managements.MngRooms
import com.mhdeveloper.compas.controller.notifications.NtRechargeAdapterUser
import com.mhdeveloper.compas.model.Permission
import com.mhdeveloper.compas.view.adapters.AdapterRecyclerUser
import kotlinx.android.synthetic.main.fragment_creation_ticket.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentUsers.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentUsers.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentUsers : Fragment(), View.OnClickListener, OnItemSelectedListener {



    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    /**
     * Interface Elements
     * */
    private var recycler:RecyclerView? = null
    private var buttonAdd:ImageButton? = null
    /**
     * Elements for funcionality
     * */
    private var permissionSelected:String?= null

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
        var view = inflater.inflate(R.layout.fragment_fragment__users, container, false)
        //Add elements
        recycler = view.findViewById(R.id.users_recycler)
        recycler!!.layoutManager = LinearLayoutManager(view.context)
        buttonAdd = view.findViewById(R.id.buttonAdd)
        buttonAdd!!.setOnClickListener(this)
        //Generate Adapter
        var adapter = AdapterRecyclerUser()
        recycler!!.adapter = adapter
        //Añadimos el adapter al notificador del evento
        NtRechargeAdapterUser.setAdapter(adapter)
        return view
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.buttonAdd ->{
                if (MngRooms.getPermissions().isAdminUser){
                    inviteNewUser()
                }
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
         * @return A new instance of fragment FragmentUsers.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentUsers().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun inviteNewUser(){
        var builder = AlertDialog.Builder(activity)
        var inflater = activity!!.layoutInflater
        var view = inflater.inflate(R.layout.dialog_new_user,null)
        builder.setView(view)
        var spinner:Spinner= view.findViewById(R.id.spinner)
        var buttonAccpet = view.findViewById<ImageButton>(R.id.send)
        var tag = view.findViewById<EditText>(R.id.tag)
        var arrayAdapter = ArrayAdapter<Permission>(view.context,android.R.layout.simple_spinner_item,MngRooms.getRoomSelected().permissions)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = this
        var dialog = builder.create()
        buttonAccpet.setOnClickListener(View.OnClickListener {
            if (permissionSelected != null && !tag.text.toString().equals("")){
                    if (!MngRooms.getRoomSelected().permissesUser.containsKey(tag.text.toString())){
                        FirestoreController.createNotification(tag.text.toString(),permissionSelected,MngRooms.getRoomSelected().uid)
                    }else{

                    }
            }
            dialog.cancel()
        })
        dialog.show()




    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        permissionSelected = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            permissionSelected = MngRooms.getRoomSelected().permissions[position].name
    }
}
