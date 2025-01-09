package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;


public class setPopup {
    //Error popup function
    public static void showError(Context context, String errorMessage, String errorMessage2){
        //Finds pallette
        int layoutId = setPalette.setLayout(context, "error_popup");
        Activity activity = (Activity) context;
        //Finds activity stage
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = activity.getWindow().getDecorView().getRootView(); // Gets the root view of the activity.
        View popupView = inflater.inflate(layoutId, null);
        //Defines popup parameters as size of screen
        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);
        //Defines middle
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        View container = popupWindow.getContentView().getRootView();
        //Sets dim amount
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) container.getLayoutParams();
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        //Sets dim amount by factor of num / 100 == percent
        params.dimAmount = 0.85f;
        wm.updateViewLayout(container, params);
        //If error message defined display
        TextView errorText = popupView.findViewById(R.id.errorText);
        errorText.setText(errorMessage);
        //Spare room for more error messages
        TextView errorText2 = popupView.findViewById(R.id.errorText2);
        errorText2.setText(errorMessage2);
    }
    //Success popup function
    public static void showSuccess(Context context, String imageType, String successMessage, String sucessMessage2){
        //Finds pallette
        int layoutId = setPalette.setLayout(context, "sucess_popup");
        Log.d("showSuccess", "Layout ID: " + layoutId);
        String popUp = setPalette.findPopUp(context);
        //Finds what image to display
        imageType = imageType + popUp;
        //Finds this resource
        int drawable = context.getResources().getIdentifier(imageType, "drawable", context.getPackageName());
        Activity activity = (Activity) context;
        //Finds activity stage
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = activity.getWindow().getDecorView().getRootView(); // Gets the root view of the activity.
        View popupView = inflater.inflate(layoutId, null);
        //Defines popup parameters as size of screen
        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);

        //Defines middle
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        View container = popupWindow.getContentView().getRootView();
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) container.getLayoutParams();
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        //Sets dim amount by factor of num / 100 == percent
        params.dimAmount = 0.85f;
        wm.updateViewLayout(container, params);
        //Sets image and text
        ImageView successImage = popupView.findViewById(R.id.successImage);
        successImage.setImageResource(drawable);
        TextView successText = popupView.findViewById(R.id.successText);
        successText.setText(successMessage);
        TextView successText2 = popupView.findViewById(R.id.successText2);
        successText2.setText(sucessMessage2);
    }
    //Dani popup function - shows lead artist
    public static void showDani(Context context){
        //Finds pallette
        int layoutId = setPalette.setLayout(context, "dani_reference");
        Activity activity = (Activity) context;
        //Finds activity stage
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = activity.getWindow().getDecorView().getRootView(); // Gets the root view of the activity.
        View popupView = inflater.inflate(layoutId, null);
        //Defines popup parameters as size of screen
        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);
        //Defines middle
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        View container = popupWindow.getContentView().getRootView();
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) container.getLayoutParams();
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        //Sets dim amount by factor of num / 100 == percent
        params.dimAmount = 0.85f;
        wm.updateViewLayout(container, params);
    }

}
