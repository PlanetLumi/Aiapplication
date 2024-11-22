package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;

import java.util.Arrays;

public class prePromptArea {
    public static String prePromptRefund(Activity activity){
        String[] requestSplit = ReadData.returnData(activity,"userRequestInput.txt").split(",");
        String[] userData = ReadData.returnData(activity, "userData.txt").split(",");
        String preprompt = "You are speaking as our user. Please use first person. You will operate under the usual TOS standard of customer service, such as refunds,and must be as concise with the information as possible. Please build customer service email requests on our user behalf";
        ReadData readData = new ReadData();
        preprompt = preprompt + "The user is requesting a refund from" + requestSplit[0].replace("[", "");
        preprompt = preprompt + "Please use your understanding of the given company to accurately build a customer service request. Always place the Subject line at the top, and never make up data that has not been given to you. Do not add order numbers. ";
        preprompt = preprompt + "The users name is" + userData[0] + " " + userData[1];
        return preprompt;
    }
}
