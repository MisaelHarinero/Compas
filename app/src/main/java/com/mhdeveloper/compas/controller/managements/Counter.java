package com.mhdeveloper.compas.controller.managements;

public class Counter {
    private int count;
    public Counter(){
        count = 0;
    }
    public void plusCount(){
        count++;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
