package com.example.user.vibrapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.google.common.hash.Hashing;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

class HttpClass extends AsyncTask<String, Integer, String> {
    //     String ip="10.108.16.247";
    static String ip = "141.134.252.69"/* = "10.108.32.60"*/;
    //    static String ip = "10.108.16.230"/* = "10.108.32.60"*/;
    public async delegate = null;//Call back interface
    private ProgressDialog dialog;
    private Activity a;


    public HttpClass(Activity activity) {
        dialog = new ProgressDialog(activity);
        a = activity;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected String doInBackground(String... strings) {
        if (strings[0].equals("login")) {

            StringBuilder builder = new StringBuilder();

            try {


                URL url = new URL("http://".concat(ip).concat(":8080/VibreREST/rest/parameter_service/login?password=" + strings[2]) + "&email=" + strings[1]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                connection.connect();

                Log.e("a", String.valueOf(connection.getResponseCode()));


                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String s;

                while ((s = rd.readLine()) != null) {
                    builder.append(s);

                    Log.e("&", builder.toString());
                }
                Main2Activity.a = builder.toString();
                Log.e("doInBackground: ", Main2Activity.a);
                connection.disconnect();
                /*if (strings[3].equals("toevoegenEigenProj")) {
                    saveToken();
                }*/

            } catch (Exception e) {
                //   Log.e("a", MainActivity.xList.toString());
                Log.e("1", "ERROR");
                Log.e("1", e.toString());
                dialog.dismiss();
                a.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast toast = Toast.makeText(a, "Error occurred trying to connect the server, please try again!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
                return "fail";

            }

            return null;
        } else if (strings[0].equals("toevoegenEigenProj")) {

            return toevoegenEigenProj(strings);
        } else if (strings[0].equals("NProj")) {

            return nieuwProject(strings);
        } else if (strings[0].equals("toevoegenToken")) {
            return toevoegenToken(strings);
        } else {
            Log.e("aaaaaa", "bbbbbbb");
            return "123";
        }
    }

    private String toevoegenToken(String[] strings) {
        this.dialog.setMessage("Processing... in adding project");

        StringBuilder builder = new StringBuilder();
        try {
            byte[] byteArray = MainActivity.xB;
            ByteArrayInputStream bas = new ByteArrayInputStream(byteArray);
            DataInputStream ds = new DataInputStream(bas);
            URL url = new URL("http://".concat(ip).concat(":8080/VibreREST/rest/parameter_service/toevoegenToken"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("token", Main2Activity.a);
            connection.setRequestProperty("type", Main3Activity.mode);
            connection.setRequestProperty("projToken", strings[1]);
            connection.setRequestProperty("experimentNaam", strings[2]);
            connection.setRequestProperty("hertz", String.valueOf(MainActivity.hertz));
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
            Log.e("a", MainActivity.mainNode.toString());
            os.writeBytes(MainActivity.mainNode.toString());

            os.flush();
            os.close();

            Log.e("a", String.valueOf(connection.getResponseCode()));


            connection.disconnect();
            Intent intent = new Intent(a, startscherm.class);
            a.startActivity(intent);
        } catch (Exception e) {
            Log.e("2", "ERROR");
            dialog.dismiss();
            Log.e("2", e.toString());
            a.runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(a, "Error occurred trying to connect the server, please try again!", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
            ;
        }


        return null;

    }


    private String nieuwProject(String[] strings) {

        this.dialog.setMessage("Processing... in adding project");

        StringBuilder builder = new StringBuilder();
        try {
            byte[] byteArray = MainActivity.xB;
            ByteArrayInputStream bas = new ByteArrayInputStream(byteArray);
            DataInputStream ds = new DataInputStream(bas);
            URL url = new URL("http://".concat(ip).concat(":8080/VibreREST/rest/parameter_service/nieuwProject"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("token", Main2Activity.a);
            connection.setRequestProperty("type", strings[5]);
            connection.setRequestProperty("projectNaam", strings[1]);
            connection.setRequestProperty("experimentNaam", strings[2]);
            connection.setRequestProperty("long", strings[3]);
            connection.setRequestProperty("lat", strings[4]);
            connection.setRequestProperty("hertz", String.valueOf(MainActivity.hertz));
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
            Log.e("a", MainActivity.mainNode.toString());
            os.writeBytes(MainActivity.mainNode.toString());

            os.flush();
            os.close();

            Log.e("a", String.valueOf(connection.getResponseCode()));


            connection.disconnect();
            Intent intent = new Intent(a, startscherm.class);
            a.startActivity(intent);

        } catch (Exception e) {
            Log.e("2", "ERROR");
            Log.e("2", e.toString());
            dialog.dismiss();
            a.runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(a, "Error occurred trying to connect the server, please try again!", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }


        return null;

    }

    private String toevoegenEigenProj(String... strings) {
        this.dialog.setMessage("Processing... in adding project");

        StringBuilder builder = new StringBuilder();
        try {
            byte[] byteArray = MainActivity.xB;
            ByteArrayInputStream bas = new ByteArrayInputStream(byteArray);
            DataInputStream ds = new DataInputStream(bas);
            URL url = new URL("http://".concat(ip).concat(":8080/VibreREST/rest/parameter_service/meting"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("token", Main2Activity.a);
            connection.setRequestProperty("type", Main3Activity.mode);
            connection.setRequestProperty("projID", strings[1]);
            connection.setRequestProperty("experimentNaam", strings[2]);
            connection.setRequestProperty("hertz", String.valueOf(MainActivity.hertz));
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
            Log.e("a", MainActivity.mainNode.toString());
            os.writeBytes(MainActivity.mainNode.toString());

            os.flush();
            os.close();

            Log.e("a", String.valueOf(connection.getResponseCode()));


            connection.disconnect();
            Intent intent = new Intent(a, startscherm.class);
            a.startActivity(intent);
        } catch (Exception e) {
            Log.e("2", "ERROR");
            Log.e("2", e.toString());
            dialog.dismiss();
            a.runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(a, "Error occurred trying to connect the server, please try again!", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }


        return null;

    }

    @Override
    protected void onPostExecute(String s) {
        //  Activity a=dialog.getOwnerActivity();

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
//        new GetClass(a).execute("projects");

    }

    @Override
    protected void onPreExecute() {
        this.dialog.show();
        this.dialog.setCancelable(false);
        this.dialog.setMessage("Processing... in http");

    }
}