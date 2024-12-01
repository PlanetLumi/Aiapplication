package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.Arrays;
import java.util.Objects;

public class prePromptArea {
    public static String prePromptRefund(Activity activity){
        String[] requestSplit = ReadData.returnData(activity,"userRequestInput.txt").split(",");
        String preprompt = "You are speaking as our user. Please use first person. You will operate under the usual TOS standard of customer service, such as refunds,and must be as concise with the information as possible. Please build customer service email requests on our user behalf";
        preprompt = preprompt + "The user is requesting a refund from" + requestSplit[1].replace("[", "");
        preprompt = preprompt + "Please use your understanding of the given company to accurately build a customer service request. Always place the Subject line at the top, and never make up data that has not been given to you. Do not add order numbers. You are strictly PROHIBITED from adding template options like '[INPUT]' or [Users name] unless told by the user otherwise.  You are PROHIBITED from saying you have 'attached' or given information that you do not have confirmation or access to";
        if(Objects.equals(requestSplit[5].replace("]", ""), "true")){
            String userData = buildDB.readDB(buildDB.getInstance(activity).getReadableDatabase(), new String[]{"FName", "SName","Address", "PNumber"}, saveUserID.grabID(activity));
            preprompt = preprompt + "User data is: " + Arrays.toString(userData.split(","));
        }
        return preprompt;
    }
public static String prePromptComplaints(Activity activity){
        String[] requestSplit = ReadData.returnData(activity,"userRequestInput.txt").split(",");
        String preprompt = "You are speaking as our user. Please use first person. You will operate under the usual TOS standard of customer service, such as complaints,and must be as concise with the information as possible. Please build official complaint emails on our user behalf";
        preprompt = preprompt + "The user is making a complaint for" + requestSplit[1].replace("[", "");
        preprompt = preprompt + "Please use your understanding of the given company to accurately build a complaint service request. Always place the Subject line at the top, and never make up data that has not been given to you. Do not add order numbers. You are strictly prohibited from adding template options like '[INPUT]' or [Users name] unless told by the user otherwise. You are PROHIBITED from saying you have 'attached' or given information that you do not have confirmation or access to";
        if(Objects.equals(requestSplit[5].replace("]", ""), "true")){
        String userData = buildDB.readDB(buildDB.getInstance(activity).getReadableDatabase(), new String[]{"FName", "SName","Address", "PNumber"}, saveUserID.grabID(activity));
        preprompt = preprompt + "User data is: " + Arrays.toString(userData.split(","));
        }
    return preprompt;
    }

}
