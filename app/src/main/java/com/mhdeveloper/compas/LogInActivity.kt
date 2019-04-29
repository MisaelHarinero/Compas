package com.mhdeveloper.compas

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mhdeveloper.compas.controller.dao.AuthController
import com.mhdeveloper.compas.controller.notifications.NtCharge
import com.mhdeveloper.compas.view.FragmentWait
import com.mhdeveloper.compas.view.LogInFragment

class LogInActivity : AppCompatActivity(), FragmentWait.OnFragmentInteractionListener,LogInFragment.OnFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        NtCharge.loginActivity = this
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
