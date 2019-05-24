package com.mhdeveloper.compas.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.mhdeveloper.compas.MainActivity

import com.mhdeveloper.compas.R
import com.mhdeveloper.compas.controller.dao.CloudController
import com.mhdeveloper.compas.controller.dao.DatabaseStrings
import com.mhdeveloper.compas.controller.dao.FirestoreController
import com.mhdeveloper.compas.controller.managements.MngRooms
import com.mhdeveloper.compas.model.User
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentDataUserChange.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentDataUserChange.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentDataUserChange : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.image ->{
                chargePhoto()
            }
        }
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var name:TextView? = null
    private var surname:TextView? = null
    private var date:TextView? = null
    private var mail:TextView? = null
    private var tag:TextView? = null
    private var imageAvatar:ImageButton? = null
    private var uri:Uri? = null
    val CODE_PICK_IMG = 1234

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
        var view = inflater.inflate(R.layout.fragment_fragment_data_user_change, container, false)
        name = view.findViewById(R.id.name)
        surname = view.findViewById(R.id.surname)
        mail = view.findViewById(R.id.email)
        tag = view.findViewById(R.id.tag)
        date = view.findViewById(R.id.date)
        imageAvatar = view.findViewById(R.id.image)
        imageAvatar!!.setOnClickListener(this)
        name!!.text = MngRooms.getUser().name
        surname!!.text = MngRooms.getUser().surname
        tag!!.text = MngRooms.getUser().tag
        var formatter = SimpleDateFormat("dd/MM/yyyy")
        date!!.text = formatter.format(MngRooms.getUser().dateBorn.toDate())
        mail!!.text = MngRooms.getUser().email
        if (MngRooms.getUser().imageRoute != null){
            CloudController.chargePhoto(imageAvatar,"${DatabaseStrings.COLLECTION_PHOTOS_USERS}${MngRooms.getUser().imageRoute}",view.context)
        }
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
         * @return A new instance of fragment FragmentDataUserChange.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentDataUserChange().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    /**
     * Method to charge a Photo from the Galery
     * */
    fun chargePhoto (){
        val getIntent: Intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        getIntent.setType("image/*")
        getIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(getIntent,CODE_PICK_IMG)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CODE_PICK_IMG && resultCode == Activity.RESULT_OK){
            uri = data!!.data
            imageAvatar!!.setImageURI(uri)
            if (MngRooms.getUser().imageRoute == null){
                MngRooms.getUser().imageRoute =  "${MngRooms.getUser().tag}/avatar.png"
                FirestoreController.saveUser(MngRooms.getUser())
            }
            CloudController.savePhoto(uri,"${DatabaseStrings.COLLECTION_PHOTOS_USERS}${MngRooms.getUser().imageRoute}")
            var activity = activity as MainActivity
            activity.chargePhotoUser()
        }

    }
}
