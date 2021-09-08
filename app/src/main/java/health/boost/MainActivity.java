package health.boost;


import static java.lang.String.format;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import health.boost.data.Nutrient;


public class MainActivity extends AppCompatActivity {
    List<Nutrient> nutrientsList = new ArrayList<>();
    private RecyclerView myrv;


    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText weight = findViewById(R.id.weight);
        EditText height = findViewById(R.id.height);
        TextView bmires = findViewById(R.id.result);



//        String weight = ((EditText) findViewById(R.id.weight)).getText().toString();
//        String height = ((EditText) findViewById(R.id.height)).getText().toString();


//

        Button nutrientIngredientButton = MainActivity.this.findViewById(R.id.button_ingredientPage);
        nutrientIngredientButton.setOnClickListener(view -> {
            Intent newIntent2 = new Intent(getApplicationContext(), IngredientActivity.class);
            startActivity(newIntent2);
        });
    }




}