package com.inovationware.generalmodule;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Machine {

    static Context context;

    public Machine(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void ClipboardSetText(String text_to_copy) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            clipboard.clearPrimaryClip();
        }
        ClipData clip = ClipData.newPlainText("", text_to_copy);
        clipboard.setPrimaryClip(clip);
    }

    public static String ClipboardGetText() {
        String clipboardText;
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);

        clipboardText = clipboard.getPrimaryClip().getItemAt(0)
                .coerceToText(context).toString();

        return clipboardText;
    }


}
