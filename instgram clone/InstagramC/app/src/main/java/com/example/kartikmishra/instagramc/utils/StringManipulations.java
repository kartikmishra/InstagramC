package com.example.kartikmishra.instagramc.utils;

public class StringManipulations {


    public static  String expandUsername(String username){

        return  username.replace(".","");
    }

    public static String condenseUsername(String username){

        return username.replace(" ",".");
    }
}
