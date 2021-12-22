package com.inovationware.generalmodule;

import java.util.UUID;

public class FormatWindow {
    public static String newGUID(){
        return UUID.randomUUID().toString();
    }
}
