package com.inovationware.generalmodule;

import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

public class W {
    public static String Content(TextView textView){
        return textView.getText().toString();
    }

    public static void Clear(TextView textView){
        textView.setText("");
    }

    public static void Clear(TextView textView, String newText){
        textView.setText(newText);
    }

    public static boolean IsEmpty(TextView textView){
        return textView.getText().toString().trim().length() < 1;
    }

    public static void ToUpperCase(TextView textView){
        String s = textView.getText().toString();
        String t = s.toUpperCase();
        textView.setText(t);
    }

    public static void ToLowerCase(TextView textView){
        String s = textView.getText().toString();
        String t = s.toLowerCase();
        textView.setText(t);
    }

    public static void EnableControls(View[] control_){
        if (control_.length < 1)return;
        for(int i = 0; i < control_.length; i++){
            try{
                control_[i].setEnabled(true);
            }
            catch (Exception e){

            }
        }
    }

    public static void EnableControls(View[] control_, boolean state_){
        if (control_.length < 1)return;
        for(int i = 0; i < control_.length; i++){
            try{
                control_[i].setEnabled(state_);
            }
            catch (Exception e){

            }
        }
    }

    public static void EnableControl(View control_){
        try{
            control_.setEnabled(true);
        }
        catch (Exception e){

        }
    }

    public static void EnableControl(View control_, boolean state_){
        try{
            control_.setEnabled(state_);
        }
        catch (Exception e){

        }
    }

    public static void DisableControls(View[] control_){
        if (control_.length < 1)return;
        for(int i = 0; i < control_.length; i++){
            try{
                control_[i].setEnabled(false);
            }
            catch (Exception e){

            }
        }
    }

    public static void DisableControl(View control_){
        try{
            control_.setEnabled(false);
        }
        catch (Exception e){

        }
    }

    public static boolean IsEmail(String text){
        return !(text.trim().length() < 1) && Patterns.EMAIL_ADDRESS.matcher(text).matches();
    }

    public static boolean IsEmail(TextView textView){
        return IsEmail(textView.getText().toString());
    }





}
