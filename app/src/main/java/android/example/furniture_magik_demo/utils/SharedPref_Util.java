package android.example.furniture_magik_demo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.example.furniture_magik_demo.Login.OtpVerification;
import android.example.furniture_magik_demo.R;

public class SharedPref_Util {

    public static void setString(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static void setBoolean(Context context, String user_present, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(user_present, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String user_present) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        return sharedPref.getBoolean(user_present,false);
    }
}
