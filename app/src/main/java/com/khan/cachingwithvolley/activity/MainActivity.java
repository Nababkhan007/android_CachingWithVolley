package com.khan.cachingwithvolley.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.HttpHeaderParser;
import com.khan.cachingwithvolley.R;
import com.khan.cachingwithvolley.other.AppController;
import com.khan.cachingwithvolley.other.BaseUrl;
import com.khan.cachingwithvolley.other.CacheRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        fetchData();
    }

    private void fetchData() {
        CacheRequest cacheRequest = new CacheRequest(0, BaseUrl.baseUrl + BaseUrl.postsUrlPath, response -> {
            try {
                final String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                JSONObject jsonObject = new JSONObject(jsonString);

                textView.setText(jsonObject.toString(5));

                Toast.makeText(MainActivity.this, "onResponse:\n\n" + jsonObject.toString(5), Toast.LENGTH_LONG).show();

            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
            }

        }, error ->
                Toast.makeText(MainActivity.this, "onErrorResponse:\n\n" + error.toString(), Toast.LENGTH_LONG).show());

//        Add the request to the RequestQueue.
        AppController.getInstance().addToRequestQueue(cacheRequest);
    }

    private void initialization() {
        textView = findViewById(R.id.textViewId);
    }
}