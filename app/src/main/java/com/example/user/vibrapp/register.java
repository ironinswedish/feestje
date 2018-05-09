package com.example.user.vibrapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.hash.Hashing;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

class register extends AsyncTask<String, Integer, String> {
    // String ip="10.108.16.247";
    // String ip = "10.108.32.60";
    private ProgressDialog dialog;
    public async delegate = null;//Call back interface
    Activity a;


    public register(Activity activity) {
        dialog = new ProgressDialog(activity);
        a = activity;

    }

    @Override
    protected String doInBackground(String... strings) {
        if (strings[0].equals("gebruikersnaam")) {
            return gebruikersnaam(strings[1]);
        } else if (strings[0].equals("email")) {
            return email(strings[1]);
        } else if (strings[0].equals("registreren")) {
            return nieuwAccountRegistreren(strings);
        }


        return null;

    }

    private String nieuwAccountRegistreren(String[] strings) {
        StringBuilder builder = new StringBuilder();
        // ArrayList<String> meting = new ArrayList<String>();
        try {


            URL url = new URL("http://".concat(HttpClass.ip)
                    .concat(":8080/VibreREST/rest/parameter_service/register?pass=" +
                            Hashing.sha256().hashString(strings[4],
                                    StandardCharsets.UTF_8).toString() + "&email=" + strings[3]));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            connection.setRequestProperty("voornaam", strings[1]);
            connection.setRequestProperty("naam", strings[2]);
            connection.setRequestProperty("school", strings[5]);
            connection.setRequestProperty("opleiding", strings[6]);
            connection.setRequestProperty("gebruikersnaam", strings[7]);


            connection.connect();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s;

            while ((s = rd.readLine()) != null) {
                builder.append(s);
                Log.e("&", builder.toString());
            }

            connection.disconnect();
            Intent intent = new Intent(a, Main2Activity.class);
            a.startActivity(intent);
        } catch (Exception e) {

            Log.e("get", "ERROR");
            Log.e("get", e.toString());
            a.runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(a, "Error occurred trying to connect the server, please try again!", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }

        return null;
    }


    private String email(String string) {
        StringBuilder builder = new StringBuilder();
        // ArrayList<String> meting = new ArrayList<String>();
        try {


            URL url = new URL("http://".concat(HttpClass.ip).concat(":8080/VibreREST/rest/parameter_service/email"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("email", string);

            connection.connect();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s;

            while ((s = rd.readLine()) != null) {
                builder.append(s);
                Log.e("&", builder.toString());
            }

            connection.disconnect();
            if (builder.toString().equals("check")) {
                TextView t = (TextView) a.findViewById(R.id.textView4);
                t.setTextColor(Color.GREEN);
            } else {
                TextView t = (TextView) a.findViewById(R.id.textView4);
                t.setTextColor(Color.RED);

            }
           /* Intent intent = new Intent(a, metingshow.class);
            a.startActivity(intent);*/
        } catch (Exception e) {

            Log.e("get", "ERROR");
            Log.e("get", e.toString());
            a.runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(a, "Error occurred trying to connect the server, please try again!", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
            TextView t = (TextView) a.findViewById(R.id.textView4);
            t.setTextColor(Color.RED);
        }

        return null;
    }

    private String gebruikersnaam(String string) {
        StringBuilder builder = new StringBuilder();
        // ArrayList<String> meting = new ArrayList<String>();
        try {


            URL url = new URL("http://".concat(HttpClass.ip).concat(":8080/VibreREST/rest/parameter_service/gebruikersnaam"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("gebruikersnaam", string);

            connection.connect();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s;

            while ((s = rd.readLine()) != null) {
                builder.append(s);
                Log.e("&", builder.toString());
            }

            connection.disconnect();
            if (builder.toString().equals("check")) {
                TextView t = (TextView) a.findViewById(R.id.textView2);
                t.setTextColor(Color.GREEN);
            } else {
                TextView t = (TextView) a.findViewById(R.id.textView2);
                t.setTextColor(Color.RED);

            }
           /* Intent intent = new Intent(a, metingshow.class);
            a.startActivity(intent);*/
        } catch (Exception e) {

            Log.e("get", "ERROR");
            Log.e("get", e.toString());
            a.runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(a, "Error occurred trying to connect the server, please try again!", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
            TextView t = (TextView) a.findViewById(R.id.textView2);
            t.setTextColor(Color.RED);
        }

        return null;


    }


    @Override
    protected void onPostExecute(String s) {
    }


    @Override
    protected void onPreExecute() {
    }

}