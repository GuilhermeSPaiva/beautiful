package com.example.guilhermepaiva.myapplication;

import android.provider.BaseColumns;

/**
 * Created by guilhermepaiva on 12/09/17.
 */

public final class Login {

    private Login() {}

    public static class LoginEntry implements BaseColumns {
        public static final String TABLE_NAME = "login";
        public static final String COLUMN_NAME_TITLE = "user";
        public static final String COLUMN_NAME_SUBTITLE = "password";
    }

}
