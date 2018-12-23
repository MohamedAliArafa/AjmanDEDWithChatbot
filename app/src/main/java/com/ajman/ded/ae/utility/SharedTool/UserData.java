package com.ajman.ded.ae.utility.SharedTool;

import android.content.Context;

import com.ajman.ded.ae.models.UserModel;


/**
 * Created by halima.reda on 8/28/2016.
 */
public class UserData {

    public static String TAG_SAVE_LOGIN_DATA = "login_data";
    public static String TAG_LANGUAGE_SCREEN = "language";
    public static String TAG_SHOPPING_CART = "shopping_carts";
    public static String TAG_LOCALIZATION = "localization";
    public static String TAG_NOTIFICATION_STATE = "notification_state";

    public static String TAG_CURRENCY = "Currency";
    public static String TAG_USER_CODE = "user_code";
    public static String TAG_USER_PASSWORD = "user_password";
    public static String TAG_REMEMBER_ME = "remmeber_me";
    public static String TAG_SAVE_PROFILE_INFO = "profile_info";

    public static String TAG_INSERT_TOKEN = "insert_token";
    public static Boolean TAG_TOKEN_INSERT_STATE = false;


    public static void saveUserObject(Context context, Object myObject, boolean rememberMe) {
        SharedPreferencesTool.saveObject(context, TAG_SAVE_LOGIN_DATA, myObject);
        SharedPreferencesTool.setBoolean(context, rememberMe, TAG_REMEMBER_ME);
    }

    public static UserModel getUserObject(Context context) {
        return SharedPreferencesTool.getObject(context, TAG_SAVE_LOGIN_DATA, UserModel.class);
    }

    public static boolean getRemmemberMe(Context context) {
        return SharedPreferencesTool.getBoolean(context, TAG_REMEMBER_ME);
    }

    public static void saveRemmemberMe(Context context, boolean rememberMe) {
        SharedPreferencesTool.setBoolean(context, rememberMe, TAG_REMEMBER_ME);
    }

    public static void saveLocalization(Context context, String value) {
        SharedPreferencesTool.setString(context, TAG_LOCALIZATION, value);
    }

    public static int getLocalization(Context context) {
        return SharedPreferencesTool.getInt(context, TAG_LOCALIZATION);
    }

    public static void saveNotificationState(Context context, int value) {
        SharedPreferencesTool.setInt(context, TAG_NOTIFICATION_STATE, value);
    }

    public static int getNotificationState(Context context) {
        return SharedPreferencesTool.getInt(context, TAG_NOTIFICATION_STATE);
    }

    public static void saveUserCurrencyID(Context context, int value) {
        SharedPreferencesTool.setInt(context, TAG_CURRENCY, value);
    }

    public static int getUserCurrencyID(Context context) {
        return SharedPreferencesTool.getInt(context, TAG_CURRENCY);
    }

    public static void saveUserStateOfInsertToken(Context context, boolean state, String value) {
        SharedPreferencesTool.setBoolean(context, state, value);
    }

    public static Boolean getUserStateOfInsertToken(Context context) {
        return SharedPreferencesTool.getBoolean(context, TAG_INSERT_TOKEN);
    }

    public static void saveUserCode(Context context, String value) {
        SharedPreferencesTool.setString(context, TAG_USER_CODE, value);
    }

    public static String getUserCode(Context context) {
        return SharedPreferencesTool.getString(context, TAG_USER_CODE);
    }

    public static void saveUserPassword(Context context, String value) {
        SharedPreferencesTool.setString(context, TAG_USER_PASSWORD, value);
    }

    public static String getUserPassword(Context context) {
        return SharedPreferencesTool.getString(context, TAG_USER_PASSWORD);
    }

    public static void clearShared(Context context) {
        SharedPreferencesTool.clearObject(context);
    }

    public static void clearUser(Context context) {
        SharedPreferencesTool.clearObject(context, TAG_SAVE_LOGIN_DATA);
    }

    public static void setLanguageScreen(Context context, boolean isShown) {
        SharedPreferencesTool.setBoolean(context, isShown, TAG_LANGUAGE_SCREEN);
    }

    public static boolean isLanguageShown(Context context) {
        return SharedPreferencesTool.getBooleanlang(context, TAG_LANGUAGE_SCREEN);
    }

    public static void saveReadyShoppingCart(Context context, boolean isShown) {
        SharedPreferencesTool.setBoolean(context, isShown, TAG_SHOPPING_CART);
    }

    public static boolean getReadyShoppingCart(Context context) {
        return SharedPreferencesTool.getBoolean(context, TAG_SHOPPING_CART);
    }
}
