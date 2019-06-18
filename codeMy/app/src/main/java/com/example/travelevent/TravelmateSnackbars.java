package com.example.travelevent;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.view.View;

public interface TravelmateSnackbars {

    @RequiresApi(api = Build.VERSION_CODES.N)
    static Snackbar createSnackBar(View view, int message, int duration) {
        return Snackbar.make(view, message, duration);
    }
    static Snackbar createSnackBar(View view, String message, int duration) {
        return Snackbar.make(view, message, duration);
    }
}
