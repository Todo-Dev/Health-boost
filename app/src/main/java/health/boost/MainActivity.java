package health.boost;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button navToNutrientPage = MainActivity.this.findViewById(R.id.button_nutrientPage);
        navToNutrientPage.setOnClickListener(view -> {

            Intent newIntent = new Intent(MainActivity.this, NutrientActivity.class);
            startActivity(newIntent);
        });


        Button navToIngredientPage = MainActivity.this.findViewById(R.id.button_ingredientPage);
        navToIngredientPage.setOnClickListener(view -> {

            Intent newIntent = new Intent(MainActivity.this, IngredientActivity.class);
            startActivity(newIntent);
        });

    }


}