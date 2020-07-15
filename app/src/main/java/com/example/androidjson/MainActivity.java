package com.example.androidjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.t1ID);

        jsonTask jTask = new jsonTask();
        jTask.execute();
    }

    public  class jsonTask extends AsyncTask<String,String,String>{


        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            String name;
            Integer age;
            String description;
            try {
                URL url = new URL("https://api.npoint.io/e8d941599640b2ffb969");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";

                while ((line =bufferedReader.readLine()) !=null){

                    stringBuffer.append(line);

                }
                String file = stringBuffer.toString();

                JSONObject fileobj =  new JSONObject(file);
                JSONArray jsonArray =  fileobj.getJSONArray("studentinfo");
                JSONObject arrayobject = jsonArray.getJSONObject(0);


                name = arrayobject.getString("name");
                age = arrayobject.getInt("age");
                description= arrayobject.getString("description");



                return name+"\n\n"+
                        age+"\n\n"
                        +description;






            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            textView.setText(s);
        }
    }
}