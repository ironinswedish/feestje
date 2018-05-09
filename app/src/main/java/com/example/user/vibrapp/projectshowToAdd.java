package com.example.user.vibrapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class projectshowToAdd extends AppCompatActivity {
    protected static ArrayList<Project> projects;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_projectshow_to_add);
        if (projects == null) {
            projects = new ArrayList<Project>();
        } else {

        }
        Log.e("createADD", String.valueOf(projects.size()));
        TableLayout ll = (TableLayout) findViewById(R.id.table2);
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView tv = new TextView(this);
        tv.setText("TitelTest");
        tv.setTypeface(null, Typeface.BOLD_ITALIC);

        row.addView(tv);
        TextView tv2 = new TextView(this);
        tv2.setText("ProjectToken");
        tv2.setTypeface(null, Typeface.BOLD_ITALIC);

        row.addView(tv2);

        ll.addView(row, 0);

        for (int i = 0; i < projects.size(); i++) {

            row = new TableRow(this);
            lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            tv = new TextView(this);
            if (projects.get(i).getTitel().length() > 20) {
                tv.setText(projects.get(i).getTitel().substring(0, 20).concat("...  "));

            } else {

                tv.setText(projects.get(i).getTitel().concat("     "));
            }
            row.addView(tv);
            tv2 = new TextView(this);
            tv2.setText(projects.get(i).getProjectToken());

            row.addView(tv2);
            Button btn = new Button(this);
            btn.setText("Add");
            btn.setId(projects.get(i).getId());
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);

            final EditText edittext = new EditText(this);
            alert.setMessage("Geef de Experimentnaam in");
            alert.setTitle("Experimentnaam:");
            alert.setView(edittext);

            alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Editable YouEditTextValue = edittext.getText();
                    alert.setTitle("");
                    new HttpClass(projectshowToAdd.this).execute("toevoegenEigenProj", id,edittext.getText().toString());

                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // what ever you want to do with No option.
                }
            });


            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    id=String.valueOf(v.getId());
                    alert.show();

                }
            });
            row.addView(btn);
            ll.addView(row, i + 2);
        }


    }

    public static void addProject(Project p) {
        if (projects == null) {
            projects = new ArrayList<Project>();
        }
        projects.add(p);
    }


}
