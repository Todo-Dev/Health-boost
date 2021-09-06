package health.boost;


import static java.lang.String.format;

import  androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import health.boost.adapter.NutrientAdapter;
import health.boost.data.Nutrient;



public class MainActivity extends AppCompatActivity {



    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText weight = findViewById(R.id.weight);
        EditText height = findViewById(R.id.height);
        TextView bmires = findViewById(R.id.result);
        Button button = findViewById(R.id.submit);


//        String weight = ((EditText) findViewById(R.id.weight)).getText().toString();
//        String height = ((EditText) findViewById(R.id.height)).getText().toString();


//
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmi(weight,height,bmires);
            }
        });
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void bmi(EditText weight, EditText height,TextView bmires) {
        if (height.getText().toString().isEmpty() && weight.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Both Height and Weight are Mandatory.", Toast.LENGTH_SHORT).show();
        } else {
            float height1 = Float.parseFloat(height.getText().toString())/100;
            float weight1 = Float.parseFloat(weight.getText().toString());
            Log.i(TAG, "bmi: "+ height1 + " /" + weight1);
            float res = weight1 / (height1 * height1);
            Log.i(TAG, "res: "+ res);

            if (res < 18.5) {
                bmires.setText("Underweight " + format("%.2f", res));
                bmires.setBackgroundColor(Color.parseColor("#00658F"));
//                sharedbmi = "Underweight";
            }
            if (res > 18.5 && res < 24.9) {
                bmires.setText("Healthy " + format("%.2f", res));
                bmires.setBackgroundColor(Color.parseColor("#63C1C6"));
//                sharedbmi = "Healthy";
            }
            if (res > 24.9 && res < 29.9) {
                bmires.setText("Overweight " + format("%.2f", res));
                bmires.setBackgroundColor(Color.parseColor("#F5AD84"));
//                sharedbmi = "Overweight";
            }
            if (res > 30 && res < 34.9) {
                bmires.setText("obesity " + format("%.2f", res));
                bmires.setBackgroundColor(Color.parseColor("#ED7632"));
//                sharedbmi = "obesity";
            }
            if (res > 35) {
                bmires.setText("severe obesity " + format("%.2f", res));
                bmires.setBackgroundColor(Color.parseColor("#CB414C"));
//                sharedbmi = "severe obesity";
            }
        }

        Button button = findViewById(R.id.getData);
        myrv = findViewById(R.id.recyclerView_nutrient);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://api.spoonacular.com/recipes/findByNutrients?minCarbs=10&maxCarbs=50&number=50&apiKey=0af706a2f4d74bfc8f4d0c9aaf2625d4";
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
                    String title = "";
                    try {
                        JSONArray vb = response;
                        for (int i = 0; i < vb.length(); i++) {
//                            title = vb.getString(i);
                            JSONObject jsonObject1;
                            jsonObject1 = vb.getJSONObject(i);
                            nutrientsList.add(new Nutrient(jsonObject1.optString("title"), jsonObject1.optString("image"), jsonObject1.optInt("calories"),
                                    jsonObject1.getString("protein"), jsonObject1.getString("fat"), jsonObject1.getString("carbs")));

                        }
                        NutrientAdapter myAdapter = new NutrientAdapter(getApplicationContext(), nutrientsList);
                        myrv.setAdapter(myAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(MainActivity.this, "title" + nutrientsList.get(4), Toast.LENGTH_LONG).show();

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();

                    }
                });
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                });
                requestQueue.add(request);


            }
        });


    }


}