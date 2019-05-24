package com.mhdeveloper.compas.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.google.firebase.Timestamp
import com.mhdeveloper.compas.R
import com.mhdeveloper.compas.controller.dao.CloudController
import com.mhdeveloper.compas.controller.dao.DatabaseStrings
import com.mhdeveloper.compas.controller.dao.UtilitiesClass
import com.mhdeveloper.compas.controller.notifications.NtRegister
import com.mhdeveloper.compas.model.User
import kotlinx.android.synthetic.main.fragment_fragment_register.*
import java.sql.Date


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentRegister.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentRegister.newInstance] factory method to
 * create an instance of this fragment.
 *  @author Misael Harinero
 */
class FragmentRegister : Fragment(), View.OnKeyListener, View.OnClickListener {



    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null


    /**
     * Interface Elements
     *
     * */
    private var name: EditText? = null
    private var surname: EditText? = null
    private var birthdate: EditText? = null
    private var mail: EditText? = null
    private var password: EditText? = null
    private var rePassword: EditText? = null
    private var checkPolitics: CheckBox? = null
    private var buttonRegister: Button? = null
    private var error: TextView? = null
    // Image Selection
    private var imageSelected: ImageButton? = null
    private var uri:Uri? = null
    val CODE_PICK_IMG = 1232




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
       when(v!!.id){
            R.id.etRepeatPass ->{

                if (password!!.getText().toString().equals(rePassword!!.getText().toString()) && UtilitiesClass.checkPassword(password!!.text.toString())) {
                    password!!.setBackgroundColor(getResources().getColor(R.color.colorCorrect, null))
                    rePassword!!.setBackgroundColor(getResources().getColor(R.color.colorCorrect, null))
                } else {
                    password!!.setBackgroundColor(getResources().getColor(R.color.colorIncorrect, null))
                    rePassword!!.setBackgroundColor(getResources().getColor(R.color.colorIncorrect, null))
                }
            }
           R.id.etPass ->{
               if (password!!.getText().toString().equals(rePassword!!.getText().toString()) && UtilitiesClass.checkPassword(password!!.text.toString())) {
                   password!!.setBackgroundColor(getResources().getColor(R.color.colorCorrect, null))
                   rePassword!!.setBackgroundColor(getResources().getColor(R.color.colorCorrect, null))
               } else {
                   password!!.setBackgroundColor(getResources().getColor(R.color.colorIncorrect, null))
                   rePassword!!.setBackgroundColor(getResources().getColor(R.color.colorIncorrect, null))
               }
           }
        }
        return false
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.buttonContinue -> {

                register()

            }
            R.id.selectPhoto ->{
                chargePhoto()
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_fragment_register, container, false)
        this.name = view.findViewById(R.id.etName)
        this.surname = view.findViewById(R.id.etSurname)
        this.birthdate = view.findViewById(R.id.etDate)
        this.mail = view.findViewById(R.id.etMail)
        this.password = view.findViewById(R.id.etPass)
        this.rePassword = view.findViewById(R.id.etRepeatPass)
        this.checkPolitics = view.findViewById(R.id.checkBox)
        this.buttonRegister = view.findViewById(R.id.buttonContinue)
        this.error = view.findViewById(R.id.errorMssg)
        this.imageSelected = view.findViewById(R.id.selectPhoto)
        this.imageSelected!!.setOnClickListener(this)
        this.password!!.setOnKeyListener(this)
        this.rePassword!!.setOnKeyListener(this)
        this.buttonRegister!!.setOnClickListener(this)


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

    fun register() {
        val name: String
        val surname: String
        val date: String
        val email: String
        val pass: String
        val rePass: String
        name = this.name!!.getText().toString()
        surname = this.surname!!.getText().toString()
        date = this.birthdate!!.getText().toString()
        email = this.mail!!.getText().toString()
        pass = this.password!!.getText().toString()
        rePass = this.rePassword!!.getText().toString()
        var mmsg = ""
        var register = true
        if (name != "" && surname != "" &&
            date != "" &&
            email != "" &&
            pass != "" &&
            rePass != ""
        ) {
            if (!UtilitiesClass.checkMail(email)) {
                register = false
                mmsg += "El email esta mal formado\n"
            }
            if (!UtilitiesClass.checkPassword(pass)) {
                register = false
                mmsg += "La contraseña esta mal formada\n"
            }
            if (pass != rePass) {
                register = false
                mmsg += "Las contraseñas tienen que ser las mismas"


            }
            if (!checkPolitics!!.isChecked()) {
                register = false
                mmsg += "Tienes que aceptar nuestra Politica de privacidad"
            }


        } else {
            register = false
            mmsg += "Tienes que introducir todos los datos\n"
        }
        if (register) {

            try {

                val user = User("${email.split("@")[0]}${UtilitiesClass.generateTag()}","", name, surname, email, Timestamp(Date.valueOf(date)),false,null)
                if (uri != null){
                    user.imageRoute = "${user.tag}/avatar.png"
                }
                NtRegister.setFragment(this)
                NtRegister.register(email,pass,user,uri)
            } catch (e: IllegalArgumentException) {
                mmsg += "Formato de la fecha debe ser yyyy-MM-dd"

            }

        }

        this.error!!.setText(mmsg)
    }
    /**
     * Metodo qe  nos carga el Intent de selecion de imagen de la galeria
     * */
    fun chargePhoto (){
        val getIntent: Intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        getIntent.setType("image/*")
        getIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(getIntent,CODE_PICK_IMG)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentRegister.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentRegister().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
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
