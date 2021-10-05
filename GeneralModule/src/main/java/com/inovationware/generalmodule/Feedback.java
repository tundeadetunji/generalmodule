package com.inovationware.generalmodule;

import android.content.Context;
import android.widget.Toast;

public class Feedback {
    Context context;

    public Feedback(Context context) {
        this.context = context;
    }

    public void feedback(String string, int Toast_Length){
        Toast.makeText(context, string, Toast_Length).show();
    }
}
