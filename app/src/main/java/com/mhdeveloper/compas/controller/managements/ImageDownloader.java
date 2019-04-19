package com.mhdeveloper.compas.controller.managements;

public class ImageDownloader {
    private  static String image;
    public final static int CODE_PICK_IMG  = 103;

    public static String getImage() {
        return image;
    }

    public static void setImage(String image) {
        ImageDownloader.image = image;
    }
}
