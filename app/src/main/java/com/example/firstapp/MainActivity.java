package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "jiatai";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadHtml();
            }
        }).start();
    }

    private void loadHtml() {
        try {
            URL url = new URL("https://www.baidu.com/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Map<String, List<String>> headerFields = connection.getHeaderFields();
                Set<Map.Entry<String, List<String>>> entries = headerFields.entrySet();
                for (Map.Entry<String, List<String>> entry : entries) {
                    Log.d(TAG, entry.getKey() + " == " + entry.getValue());
                }
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = bufferedReader.readLine()) != null){
                    Log.d(TAG, "line is " + line);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
