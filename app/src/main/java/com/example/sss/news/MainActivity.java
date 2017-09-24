package com.example.sss.news;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.sss.news.data.NewsContract;
import com.example.sss.news.data.NewsSQLiteDBHelper;

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
    NewsSQLiteDBHelper newsDB;
    SQLiteDatabase db;

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

        newsDB = new NewsSQLiteDBHelper(this);

        db = newsDB.getReadableDatabase();



        if (networkInfo != null && networkInfo.isConnected()){
            getData();

        }

        else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, noInternetActivity.class);
            startActivity(intent);
        }
        //initSwipePager();
    }

    private void getDataFromStorage() {

        Cursor cursor = db.rawQuery("SELECT * FROM  " + NewsContract.newsData.TABLE_NAME,null);

        Log.d("count", String.valueOf(cursor.getCount()));
    }

    private void getData(){

        String url ="https://newsapi.org/v1/articles?source=techcrunch&apiKey=d40a9cfd65f248678a9baa790e387fdc";

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
            String author = null;
            String title = null;
            String description = null;
            String url = null;
            String  url2image = null;
            String publishedAt = null;
            for (int i = 0; i < dataArray.length();i++){
                JSONObject newsObj = dataArray.getJSONObject(i);
                author = newsObj.getString("author");
                title = newsObj.getString("title");
                description = newsObj.getString("description");
                url = newsObj.getString("url");
                url2image = newsObj.getString("urlToImage");
                publishedAt = newsObj.getString("publishedAt");

                insertDataToDatabase(title, description, publisher, url, url2image);
                dataArrayList.add(new newsData(author, title, description, url, url2image, publishedAt , publisher));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initSwipePager(dataArrayList);
        getDataFromStorage();


    }

    private void insertDataToDatabase(String title, String description, String publisher, String url, String url2image) {

        ContentValues values = new ContentValues();
        values.put(NewsContract.newsData.COLUMN_TITLE, title);
        values.put(NewsContract.newsData.COLUMN_DESCRIPTION, description);
        values.put(NewsContract.newsData.COLUMN_PUBLISHER, publisher);
        values.put(NewsContract.newsData.COLUMN_URL, url);
        values.put(NewsContract.newsData.COLUMN_URL2IMAGE, url2image);
        db.insert(NewsContract.newsData.TABLE_NAME,null,values);

    }

    private void deleteFromDatabase(){
        db.delete(NewsContract.newsData.TABLE_NAME,null,null);

    }


    private void initSwipePager(ArrayList<newsData> data){
        VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.vPager);
        progressBar.setVisibility(View.GONE);
        verticalViewPager.setAdapter(new VerticlePagerAdapter(this, data));
    }


}