package com.mhdeveloper.compas.controller.notifications;

import android.content.Intent;
import com.mhdeveloper.compas.LogInActivity;
import com.mhdeveloper.compas.MainActivity;
import com.mhdeveloper.compas.R;
import com.mhdeveloper.compas.controller.dao.FirestoreController;
import com.mhdeveloper.compas.view.FragmentWait;

/**
 * @author  Misael Harinero
 * Clase que se encarga de realizar la accion cuando se haya completado un evento, en este caso el evento de Sign in, el usuario
 * se identifica con sus credenciales y cuando firebase haga saltar el evento que se completo la accion y obtengamos los datos saltara el evento y nos hara el intent a la actividad siguiente
 */
public class NtCharge implements INt{
    public static LogInActivity loginActivity;
    public static void  chargeDataUser(String uid){
        FragmentWait fragment = new FragmentWait();
        loginActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        FirestoreController.dataUser(uid,new NtCharge());

 }
    @Override
    public void action(){
     Intent intent =new Intent(loginActivity,MainActivity.class);
     loginActivity.startActivity(intent);
     loginActivity.finish();
    }


}