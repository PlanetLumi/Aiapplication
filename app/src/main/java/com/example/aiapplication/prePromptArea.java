package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;

public class prePromptArea {
    public static String prePromptRefund(Activity activity){
        String[] requestSplit = ReadData.returnData(activity,"userRequestInput.txt").split(",");
        String preprompt = "You are speaking as our user. Please use first person. You will operate under the usual TOS standard of customer service, such as refunds,and must be as concise with the information as possible. Please build customer service email requests on our user behalf";
        ReadData readData = new ReadData();
        preprompt = preprompt + "The users data is" + readData.returnData(activity, "userData.txt") + "please only give this information if deemed nessecary.";
        preprompt = preprompt + "The user is requesting a refund from" + requestSplit[0].replace("[", "");
        preprompt = preprompt + "Please use your understanding of the given company to accurately build a customer service request.";
        return preprompt;
    }
}
