package com.mhdeveloper.compas.controller.notifications;
class NtCharge implements INt{
    public static Activity loginActivity;
    public static chargeDataUser(String uid){
        FragmentWait fragment = new FragmentWait();
        loginActivity.getSupportFragmentManager().begginTransaction().replace(R.id.container,fragment).commit();
        FirestoreController.dataUSer(uid,new NtCharge());
    }
    @Override
    public void action(){
    Intent intent = new Intent(MainActivity.class,loginActivity.getContext());        
    loginActivity.startActivity(intent);
    }


}