package com.example.user.vibrapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class registreren extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registreren);
        EditText gebruikersnaam = (EditText) findViewById(R.id.editText);

        gebruikersnaam.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                new register(registreren.this).execute("gebruikersnaam", s.toString());
                Log.e("onTextChanged: ", s.toString());

            }
        });
        EditText email = (EditText) findViewById(R.id.editText4);

        email.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                new register(registreren.this).execute("email", s.toString());
                Log.e("onTextChanged: ", s.toString());

            }
        });

    }

    public void register(View v) {
        EditText voornaam = (EditText) findViewById(R.id.editText2);
        EditText naam = (EditText) findViewById(R.id.editText3);
        EditText email = (EditText) findViewById(R.id.editText4);
        EditText wachtwoord = (EditText) findViewById(R.id.editText5);
        EditText school = (EditText) findViewById(R.id.editText6);
        EditText opleiding = (EditText) findViewById(R.id.editText7);
        EditText gebruikersnaam = (EditText) findViewById(R.id.editText);
        Log.e("register: ",email.getText().toString() );
        new register(registreren.this).execute("registreren", voornaam.getText().toString(),
                naam.getText().toString(), email.getText().toString(),
                wachtwoord.getText().toString(), school.getText().toString(),
                opleiding.getText().toString(),gebruikersnaam.getText().toString());
    }

}
