package com.example.cloudhire;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "NexHireSession";

    private static final String KEY_TOKEN = "token";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLE = "role";

    private final SharedPreferences preferences;

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
        );
    }

    public void saveSession(
            String token,
            Long id,
            String name,
            String email,
            String role
    ) {
        preferences.edit()
                .putString(KEY_TOKEN, token)
                .putLong(KEY_ID, id != null ? id : -1)
                .putString(KEY_NAME, name)
                .putString(KEY_EMAIL, email)
                .putString(KEY_ROLE, role)
                .apply();
    }

    public String getToken() {
        return preferences.getString(KEY_TOKEN, null);
    }

    public Long getId() {
        long id = preferences.getLong(KEY_ID, -1);
        return id == -1 ? null : id;
    }

    public String getName() {
        return preferences.getString(KEY_NAME, null);
    }

    public String getEmail() {
        return preferences.getString(KEY_EMAIL, null);
    }

    public String getRole() {
        return preferences.getString(KEY_ROLE, null);
    }

    public boolean isLoggedIn() {
        String token = getToken();
        return token != null && !token.isEmpty();
    }

    public void logout() {
        preferences.edit().clear().apply();
    }
}