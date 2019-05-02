package com.mhdeveloper.compas.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mhdeveloper.compas.R
import com.mhdeveloper.compas.controller.dao.AuthController
import com.mhdeveloper.compas.controller.notifications.NtErrorLoggin
import com.mhdeveloper.compas.controller.notifications.NtLogInUserTry
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_log_in.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LogInFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LogInFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LogInFragment : Fragment(), View.OnClickListener {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    /**
     * Interface Elements
     * */
    var email:EditText? = null
    var passwd:EditText? = null
    var buttonLogIn:Button? = null
    var buttonForgot:Button? = null
    var buttonCreateAccount:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onClick(v: View?) {
        when(v!!.id){
                R.id.logInButton ->{
                    if ((!email!!.text.toString().equals(""))&&(!passwd!!.text.toString().equals(""))){
                        var auth = AuthController()
                        auth.signInWithMailPasswd(email!!.text.toString(),passwd!!.text.toString())
                    }
                }
                R.id.forgotPasswd ->{

                }
                R.id.createAccount->{
                    var fragment = FragmentRegister()
                    fragmentManager!!.beginTransaction().replace(R.id.container,fragment).commit()
                }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_log_in, container, false)
        this.email = view.findViewById(R.id.email)
        this.passwd = view.findViewById(R.id.passwd)
        NtLogInUserTry.setEmail(email)
        NtLogInUserTry.setPasswd(passwd)
        this.buttonLogIn = view.findViewById(R.id.logInButton)
        this.buttonForgot = view.findViewById(R.id.forgotPasswd)
        this.buttonCreateAccount = view.findViewById(R.id.createAccount)
        this.buttonLogIn!!.setOnClickListener(this)
        this.buttonForgot!!.setOnClickListener(this)
        this.buttonCreateAccount!!.setOnClickListener(this)

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
         * @return A new instance of fragment LogInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LogInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
