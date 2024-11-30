package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;

import java.util.Arrays;
import java.util.Objects;

public class prePromptArea {
    public static String prePromptRefund(Activity activity){
        String[] requestSplit = ReadData.returnData(activity,"userRequestInput.txt").split(",");
        String preprompt = "You are speaking as our user. Please use first person. You will operate under the usual TOS standard of customer service, such as refunds,and must be as concise with the information as possible. Please build customer service email requests on our user behalf";
        preprompt = preprompt + "The user is requesting a refund from" + requestSplit[0].replace("[", "");
        preprompt = preprompt + "Please use your understanding of the given company to accurately build a customer service request. Always place the Subject line at the top, and never make up data that has not been given to you. Do not add order numbers. ";
        if(!Objects.equals(requestSplit[2], "false")){
            String userData = buildDB.readDB(activity.openOrCreateDatabase("UserDetails", Context.MODE_PRIVATE, null), "UserDetails", new String[]{"Title", "FName", "SName"}, saveUserID.grabID(activity));
            preprompt = preprompt + "User data is: " + Arrays.toString(userData.split(","));
        }
        return preprompt;
    }
public static String prePromptComplaints(Activity activity){
        String[] requestSplit = ReadData.returnData(activity,"userRequestInput.txt").split(",");
        String preprompt = "You are speaking as our user. Please use first person. You will operate under the usual TOS standard of customer service, such as complaints,and must be as concise with the information as possible. Please build official complaint emails on our user behalf";
        preprompt = preprompt + "The user is making a complaint for" + requestSplit[0].replace("[", "");
        preprompt = preprompt + "Please use your understanding of the given company to accurately build a complaint service request. Always place the Subject line at the top, and never make up data that has not been given to you. Do not add order numbers. ";
        if(!Objects.equals(requestSplit[2], "false")){
        String userData = buildDB.readDB(activity.openOrCreateDatabase("UserDetails", Context.MODE_PRIVATE, null), "UserDetails", new String[]{"Title", "FName", "SName"}, saveUserID.grabID(activity));
        preprompt = preprompt + "User data is: " + Arrays.toString(userData.split(","));
        }
        return preprompt;
    }

}
