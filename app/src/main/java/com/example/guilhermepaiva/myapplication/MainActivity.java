package com.example.guilhermepaiva.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    // Database
    PostDBHelper mDbHelper = new PostDBHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactIntent();
            }
        });

        Button login = (Button) findViewById(R.id.button);
        final EditText user = (EditText) findViewById(R.id.editText);
        final EditText password = (EditText) findViewById(R.id.editText2);

        Button create = (Button) findViewById(R.id.button2);

        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String u = user.getText().toString();
                        String p = password.getText().toString();

                        if (checkDatabase(u, p)) {
                            Log.d("Opa", u + " -> " + p);
                        }

                        else {
                            Log.d("Tem nao", u + " -> " + p);
                        }
                    }
                }
        );

        create.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String u = user.getText().toString();
                        String p = password.getText().toString();

                        if (insertLogin(u, p)) {
                            Log.d("Cadastrado", u + " -> " + p);
                        }

                        else {
                            Log.d("Falha no cadastro", u + " -> " + p);
                        }
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkDatabase(String user, String password) {
        if (user.isEmpty() || password.isEmpty()) {
            return false;
        }

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                Login.LoginEntry._ID,
                Login.LoginEntry.COLUMN_NAME_TITLE,
                Login.LoginEntry.COLUMN_NAME_SUBTITLE
        };

        String selection = Login.LoginEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { user };

        String sortOrder = Login.LoginEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor c = db.query(
                Login.LoginEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        c.moveToFirst();

        for(int i = 0; i < c.getCount(); i++) {
            String u = c.getString(1);
            String p = c.getString(2);

            if (u.equals(user) && p.equals(password)) {
                return true;
            }

            c.moveToNext();
        }

        return false;
    }

    private boolean insertLogin(String user, String password) {
        if (user.isEmpty() || password.isEmpty()) {
            return false;
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Login.LoginEntry.COLUMN_NAME_TITLE, user);
        values.put(Login.LoginEntry.COLUMN_NAME_SUBTITLE, password);

        long newRowId = db.insert(Login.LoginEntry.TABLE_NAME, null, values);
        db.close();

        return true;
    }

    private void shareIntent() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        startActivity(Intent.createChooser(sharingIntent, "Sharear"));
    }

    private void contactIntent() {
        Intent secondActivity = new Intent(this, ContactListActivity.class);
        startActivity(secondActivity);
    }
}
