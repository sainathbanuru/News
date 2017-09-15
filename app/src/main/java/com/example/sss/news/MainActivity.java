package com.example.sss.news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    ArrayList<newsData> dataArrayList;
    ProgressBar progressBar;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        ActionBar bar = getSupportActionBar();
        bar.hide();
        dataArrayList = new ArrayList<>();
        queue = Volley.newRequestQueue(getApplicationContext());

        if (networkInfo != null && networkInfo.isConnected())
            getData();
        else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, noInternetActivity.class);
            startActivity(intent);
        }
        //initSwipePager();
    }

    private void getData(){

        String url ="https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=top&apiKey=d40a9cfd65f248678a9baa790e387fdc";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        parseData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void parseData(String response) {
        Log.d("data",response);
        try {
            JSONObject obj = new JSONObject(response);
            String publisher = obj.getString("source");
            JSONArray dataArray = obj.getJSONArray("articles");
            for (int i = 0; i < dataArray.length();i++){
                JSONObject newsObj = dataArray.getJSONObject(i);
                dataArrayList.add(new newsData(newsObj.getString("author"), newsObj.getString("title"), newsObj.getString("description"),newsObj.getString("url"),newsObj.getString("urlToImage"), newsObj.getString("publishedAt"), publisher));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initSwipePager(dataArrayList);

    }


    private void initSwipePager(ArrayList<newsData> data){
        VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.vPager);
        progressBar.setVisibility(View.GONE);
        verticalViewPager.setAdapter(new VerticlePagerAdapter(this, data));
    }


}