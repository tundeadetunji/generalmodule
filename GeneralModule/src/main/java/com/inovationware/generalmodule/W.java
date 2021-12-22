package com.inovationware.generalmodule;

import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

public class W {
    public static String content(TextView textView){
        return textView.getText().toString();
    }

    public static void clear(TextView textView){
        textView.setText("");
    }

    public static void clear(TextView textView, String newText){
        textView.setText(newText);
    }

    public static boolean isEmpty(TextView textView){
        return textView.getText().toString().trim().length() < 1;
    }

    public static void toUpperCase(TextView textView){
        String s = textView.getText().toString();
        String t = s.toUpperCase();
        textView.setText(t);
    }

    public static void toLowerCase(TextView textView){
        String s = textView.getText().toString();
        String t = s.toLowerCase();
        textView.setText(t);
    }

    public static void enableControls(View[] control_){
        if (control_.length < 1)return;
        for(int i = 0; i < control_.length; i++){
            try{
                control_[i].setEnabled(true);
            }
            catch (Exception e){

            }
        }
    }

    public static void enableControls(View[] control_, boolean state_){
        if (control_.length < 1)return;
        for(int i = 0; i < control_.length; i++){
            try{
                control_[i].setEnabled(state_);
            }
            catch (Exception e){

            }
        }
    }

    public static void enableControl(View control_){
        try{
            control_.setEnabled(true);
        }
        catch (Exception e){

        }
    }

    public static void enableControl(View control_, boolean state_){
        try{
            control_.setEnabled(state_);
        }
        catch (Exception e){

        }
    }

    public static void disableControls(View[] control_){
        if (control_.length < 1)return;
        for(int i = 0; i < control_.length; i++){
            try{
                control_[i].setEnabled(false);
            }
            catch (Exception e){

            }
        }
    }

    public static void disableControl(View control_){
        try{
            control_.setEnabled(false);
        }
        catch (Exception e){

        }
    }

    public static boolean isEmail(String text){
        return !(text.trim().length() < 1) && Patterns.EMAIL_ADDRESS.matcher(text).matches();
    }

    public static boolean isEmail(TextView textView){
        return isEmail(textView.getText().toString());
    }





}
