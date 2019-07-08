package com.example.aksha.vollytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "http://api.jsonbin.io/b/5b61f4762b23fb1f2b6b56bf";
    RecyclerView category_recycler;
    private List<CategoryModel> categoryModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        categoryModels = new ArrayList<>();
        category_recycler = findViewById(R.id.category_recycler);
        ShowData();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        category_recycler.setLayoutManager(manager);


    }

    private void ShowData() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                for (int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        CategoryModel model = new CategoryModel();
                        model.setCategory_name(jsonObject.getString("category_name"));
                        model.setCategory_image(jsonObject.getString("category_image"));
                        categoryModels.add(model);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                CategoryAdapter categoryAdapter = new CategoryAdapter(getApplicationContext(),categoryModels);
                category_recycler.setAdapter(categoryAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonArrayRequest);
    }
}
