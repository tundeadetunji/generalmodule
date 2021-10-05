package com.inovationware.generalmodule;

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

}
