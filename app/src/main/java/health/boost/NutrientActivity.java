package health.boost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

import health.boost.adapter.NutrientAdapter;
import health.boost.data.Nutrient;

public class NutrientActivity extends AppCompatActivity {
    List<Nutrient> nutrientsList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient);
        Button button = findViewById(R.id.getData);
        recyclerView = findViewById(R.id.recyclerView_nutrient);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(NutrientActivity.this);
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
                        recyclerView.setAdapter(myAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(NutrientActivity.this, "title" + nutrientsList.get(4), Toast.LENGTH_LONG).show();

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(NutrientActivity.this, "Error", Toast.LENGTH_LONG).show();

                    }
                });
                requestQueue.add(request);


            }
        });

    }
}