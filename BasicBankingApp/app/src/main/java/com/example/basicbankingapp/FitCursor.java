package com.example.basicbankingapp;

import android.database.CursorWindow;

import java.lang.reflect.Field;

public class FitCursor {
    public static void fix() {
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 102400 * 1024); //the 102400 is the new size added
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
