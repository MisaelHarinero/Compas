package com.mhdeveloper.compas

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.mhdeveloper.compas.controller.dao.AuthController
import com.mhdeveloper.compas.controller.dao.FirestoreController
import com.mhdeveloper.compas.controller.notifications.NtCharge
import com.mhdeveloper.compas.controller.notifications.NtErrorLoggin
import com.mhdeveloper.compas.view.FragmentRegister
import com.mhdeveloper.compas.view.FragmentWait
import com.mhdeveloper.compas.view.LogInFragment

class LogInActivity : AppCompatActivity(), FragmentWait.OnFragmentInteractionListener,LogInFragment.OnFragmentInteractionListener,FragmentRegister.OnFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.init_layout)
        NtCharge.loginActivity = this
        NtErrorLoggin.setActivity(this)
        FirestoreController.instanceFirestore()
        var aut = AuthController()
        if (aut.isUserSignIn){
            NtCharge.chargeDataUser(aut.user.uid)
        }else{
            var fragment = LogInFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
        }


    }


    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
