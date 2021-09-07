package health.boost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import health.boost.adapter.IngredientAdapter;
import health.boost.data.Nutrient;

public class IngredientActivity extends AppCompatActivity {
    List<Nutrient> ingredientsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EditText searchedCalorie;
    private JSONArray JSONArr;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        searchedCalorie = findViewById(R.id.editText_searchedCalories);
        progressBar = findViewById(R.id.progressbar_search);

        getResults();


        recyclerView = findViewById(R.id.recycleView_caloriesSearch);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        Button btn = findViewById(R.id.myButton);
        btn.setOnClickListener(v -> {
            // Do something in response to button click
            getSearchedResults(searchedCalorie.getText().toString());
        });

    }

    private void getResults() {
        String url = "https://api.spoonacular.com/recipes/findByNutrients?minCarbs=10&maxCarbs=50&number=20&apiKey=0af706a2f4d74bfc8f4d0c9aaf2625d4";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        @SuppressLint("NotifyDataSetChanged") JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArr = response;
                        ingredientsList.clear();
                        for (int i = 0; i < JSONArr.length(); i++) {
                            JSONObject jsonObject = JSONArr.getJSONObject(i);
                            ingredientsList.add(new Nutrient(jsonObject.optString("title"), jsonObject.optString("image"), jsonObject.optInt("calories"),
                                    jsonObject.getString("protein"), jsonObject.getString("fat"), jsonObject.getString("carbs")));
                        }
                        progressBar.setVisibility(View.GONE);
                        IngredientAdapter myAdapter = new IngredientAdapter(getApplicationContext(), ingredientsList);
                        recyclerView.getRecycledViewPool().clear();
                        recyclerView.setAdapter(myAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.i("the res is error:", error.toString())
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void getSearchedResults(String calorieNum) {
//&number=10
//        apiKey=c957b6816ba048139fbc25a67d2cff33
        String url = "https://api.spoonacular.com/recipes/findByNutrients?maxCalories=" + calorieNum + "&apiKey=0af706a2f4d74bfc8f4d0c9aaf2625d4";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        @SuppressLint("NotifyDataSetChanged") JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArr = response;
                        ingredientsList.clear();
                        for (int i = 0; i < JSONArr.length(); i++) {
                            JSONObject jsonObject = JSONArr.getJSONObject(i);
                            ingredientsList.add(new Nutrient(jsonObject.optString("title"), jsonObject.optString("image"), jsonObject.optInt("calories"),
                                    jsonObject.getString("protein"), jsonObject.getString("fat"), jsonObject.getString("carbs")));
                        }
                        progressBar.setVisibility(View.GONE);
                        IngredientAdapter myAdapter = new IngredientAdapter(getApplicationContext(), ingredientsList);
                        recyclerView.getRecycledViewPool().clear();
                        recyclerView.setAdapter(myAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.i("the res is error:", error.toString())
        );
        requestQueue.add(jsonObjectRequest);
    }


}