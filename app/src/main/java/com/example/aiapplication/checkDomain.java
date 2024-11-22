package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;

public class checkDomain {
    public static boolean checkSuffix(Context context, String domain){
        domain = domain.toLowerCase();
        String[] suffix = ReadData.returnData(context, "com.example.aiapplication/domainsuffix.txt").split("\\n");
        int x = 0;
        while(!domain.contains(suffix[x].toLowerCase())){
            x++;
            if(x == suffix.length){
                return false;
            }
        }
        return true;
    }
}
