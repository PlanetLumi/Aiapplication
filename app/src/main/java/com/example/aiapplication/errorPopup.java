package com.example.aiapplication;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class errorPopup {
    public static void showError(Context context, String errorMessage, String errorMessage2){
        Activity activity = (Activity) context;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.error_popup, null);
        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.showAtLocation(activity.getCurrentFocus(), Gravity.CENTER, 0, 0);
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
}
