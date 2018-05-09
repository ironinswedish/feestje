package com.example.user.vibrapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.ExecutionException;


public class Main2Activity extends AppCompatActivity {
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    public static String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void meting(View view) throws ExecutionException, InterruptedException {
        Button b = findViewById(R.id.button);
        EditText l = (EditText) findViewById(R.id.editText);
        EditText p = (EditText) findViewById(R.id.editText2);

        String pass=Hashing.sha256().hashString(p.getText().toString()/*Password to hash*/, StandardCharsets.UTF_8).toString();
        Log.e("meting: ",pass );

        String result = new HttpClass(Main2Activity.this).execute("login", l.getText().toString(), pass, Main3Activity.mode).get();


        //if (!result.equals("fail")) {
        if (Main3Activity.mode.equals("toevoegenEigenProj")) {


            if (projectshow.projects != null) {
                projectshow.projects.clear();
            }
            new GetClass(Main2Activity.this).execute("projectsToAdd");

        } else if (Main3Activity.mode.equals("nieuwProjectLogin")) {

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            // Add a TextView here for the "Title" label, as noted in the comments
            final EditText titleBox = new EditText(this);
            titleBox.setHint("ProjectNaam");
            layout.addView(titleBox); // Notice this is an add method
            Log.e("meting: ", "making alert");
            TextView tv = new TextView(this);
            tv.setText("Selecteer Type");
            tv.setTextSize(20);
            tv.setTypeface(null, Typeface.BOLD);

            layout.addView(tv);
            final RadioButton[] rb = new RadioButton[2];
            final RadioGroup rg = new RadioGroup(this); //create the RadioGroup
            rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
            for (int i = 0; i < 2; i++) {
                rb[i] = new RadioButton(this);
                rg.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout
            }
            rb[0].setText("normaal");
            rb[0].setChecked(true);
            rb[1].setText("STEM");

            layout.addView(rg);//you add the whole RadioGroup to the layout
            TextView tv2 = new TextView(this);
            tv2.setText("Selecteer Openbaarheid");
            tv2.setTextSize(20);
            tv2.setTypeface(null, Typeface.BOLD);

            layout.addView(tv2);
            final RadioButton[] rb2 = new RadioButton[2];
            final RadioGroup rg2 = new RadioGroup(this); //create the RadioGroup
            rg2.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
            for (int i = 0; i < 2; i++) {
                rb2[i] = new RadioButton(this);
                rg2.addView(rb2[i]); //the RadioButtons are added to the radioGroup instead of the layout
            }
            rb2[0].setText("publiek");
            rb2[1].setText("privé");
            rb2[1].setChecked(true);

            layout.addView(rg2);//you add the whole RadioGroup to the layout


            final EditText descriptionBox = new EditText(this);
            descriptionBox.setHint("ExperimentNaam");
            layout.addView(descriptionBox); // Another add method
            final TextView textView = new TextView(this);
            textView.setText("Gelieve beide velden in te vullen.");
            textView.setTextColor(Color.RED);
            textView.setVisibility(TextView.INVISIBLE);
            layout.addView(textView);
            final AlertDialog alert = new AlertDialog.Builder(this)
                    .setPositiveButton(android.R.string.ok, null)
                    .setNegativeButton(android.R.string.cancel, null).setView(layout).setMessage("Geef de parameters in")
                    .show();

            Button buttonPositive = alert.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String type = null;
                    if (rg.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getApplicationContext(), "Please select type's", Toast.LENGTH_SHORT).show();
                    } else {
                        // get selected radio button from radioGroup
                        int selectedId = rg.getCheckedRadioButtonId();
                        // find the radiobutton by returned id
                        type = (String) ((rb[0].getId() == selectedId) ? rb[0].getText() : rb[1].getText());
                    }
                    if (titleBox.getText().toString().matches("") || descriptionBox.getText().toString().matches("")) {
                        textView.setVisibility(TextView.VISIBLE);
                    } else {
                        Editable YouEditTextValue = titleBox.getText();
                        alert.setTitle("");
                        new HttpClass(Main2Activity.this).execute("NProj", titleBox.getText().toString(), descriptionBox.getText().toString(), String.valueOf(MainActivity.l.getLatitude()), String.valueOf(MainActivity.l.getLongitude()), type);
                    }
                }
            });

            Button buttonNegative = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
            buttonNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alert.dismiss();

                }
            });
            alert.show();

          /*  alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String type = null;
                    if (rg.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getApplicationContext(), "Please select typ's", Toast.LENGTH_SHORT).show();
                    } else {
                        // get selected radio button from radioGroup
                        int selectedId = rg.getCheckedRadioButtonId();
                        // find the radiobutton by returned id
                        type = (String) ((rb[0].getId() == selectedId) ? rb[0].getText() : rb[1].getText());
                    }
                    if (titleBox.getText().toString().matches("") || descriptionBox.getText().toString().matches("")) {
                        textView.setVisibility(TextView.VISIBLE);
                    } else {
                        Editable YouEditTextValue = titleBox.getText();
                        alert.setTitle("");
                        new HttpClass(Main2Activity.this).execute("NProj", titleBox.getText().toString(), descriptionBox.getText().toString(), String.valueOf(MainActivity.l.getLatitude()), String.valueOf(MainActivity.l.getLongitude()), type);
                    }
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // what ever you want to do with No option.
                }
            });*/
            //  new HttpClass(Main2Activity.this).execute("NProj", l.getText().toString(), p.getText().toString(), Main3Activity.mode);

        } else if (Main3Activity.mode.equals("opvragen")) {
            if (projectshow.projects != null) {
                projectshow.projects.clear();
            }
            new GetClass(Main2Activity.this).execute("projects");


        }
        //  }
    }

    public void register(View view) {
        Intent intent = new Intent(this, registreren.class);
        startActivity(intent);
    }
}
