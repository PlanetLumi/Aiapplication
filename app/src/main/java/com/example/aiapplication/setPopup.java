package com.example.aiapplication;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


public class setPopup {
    public static void showError(Context context, String errorMessage, String errorMessage2){
        Activity activity = (Activity) context;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = activity.getWindow().getDecorView().getRootView(); // Gets the root view of the activity.
        View popupView = inflater.inflate(R.layout.error_popup, null);
        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        View container = popupWindow.getContentView().getRootView();
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) container.getLayoutParams();
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.85f;
        wm.updateViewLayout(container, params);
        TextView errorText = popupView.findViewById(R.id.errorText);
        errorText.setText(errorMessage);
        TextView errorText2 = popupView.findViewById(R.id.errorText2);
        errorText2.setText(errorMessage2);
    }
    public static void showSuccess(Context context, String imageType, String successMessage, String sucessMessage2){
        int drawable = context.getResources().getIdentifier(imageType, "drawable", context.getPackageName());
        Activity activity = (Activity) context;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = activity.getWindow().getDecorView().getRootView(); // Gets the root view of the activity.
        View popupView = inflater.inflate(R.layout.sucess_popup, null);
        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        View container = popupWindow.getContentView().getRootView();
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) container.getLayoutParams();
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.85f;
        wm.updateViewLayout(container, params);
        ImageView successImage = popupView.findViewById(R.id.successImage);
        successImage.setImageResource(drawable);
        TextView successText = popupView.findViewById(R.id.successText);
        successText.setText(successMessage);
        TextView successText2 = popupView.findViewById(R.id.successText2);
        successText2.setText(sucessMessage2);
    }

    public static void setSuccessButton(Context context, String imageType, int buttonId, String successMessage, String successMessage2){
        Activity activity = (Activity) context;
        View saveBtn = activity.findViewById(R.id.saveButton);
        if (saveBtn != null) {
            saveBtn.setOnClickListener(v -> showSuccess(context, imageType, successMessage, successMessage2));
        }
    }
}