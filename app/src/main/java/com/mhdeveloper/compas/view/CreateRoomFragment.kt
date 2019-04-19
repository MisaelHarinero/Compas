package com.mhdeveloper.compas.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton

import com.mhdeveloper.compas.R
import com.mhdeveloper.compas.controller.managements.ImageDownloader

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CreateRoomFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CreateRoomFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CreateRoomFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.imageChargeRoom -> {
                chargePhoto()



            }
            R.id.button -> {


            }
        }
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    //Elements Interface
    private var imageSelected: ImageButton? = null
    private var buttonCreate: Button? = null
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
        var view = inflater.inflate(R.layout.fragment_create_room, container, false)
        this.imageSelected = view.findViewById(R.id.imageChargeRoom)
        this.imageSelected!!.setOnClickListener(this)
        this.buttonCreate = view.findViewById(R.id.button)
        this.buttonCreate!!.setOnClickListener(this)
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }
    fun chargePhoto (){
        val getIntent:Intent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.setType("image/*")
        val pickIntent = Intent(Intent.ACTION_PICK)
        pickIntent.setType("image/*")
        val chooserIntent = Intent.createChooser(getIntent,"Select Image")

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, Array<Intent>(1,{i -> pickIntent} ))
        activity!!.startActivityForResult(chooserIntent, ImageDownloader.CODE_PICK_IMG)


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
         * @return A new instance of fragment CreateRoomFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateRoomFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}