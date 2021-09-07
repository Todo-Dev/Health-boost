package health.boost;


import static java.lang.String.format;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;


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
                bmi(weight, height, bmires);
            }
        });



        Button nutrientIngredientButton = MainActivity.this.findViewById(R.id.button_ingredientPage);
        nutrientIngredientButton.setOnClickListener(view -> {
            Intent newIntent2 = new Intent(getApplicationContext(), IngredientActivity.class);
            startActivity(newIntent2);
        });
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void bmi(EditText weight, EditText height, TextView bmires) {
        if (height.getText().toString().isEmpty() && weight.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Both Height and Weight are Mandatory.", Toast.LENGTH_SHORT).show();
        } else {
            float height1 = Float.parseFloat(height.getText().toString()) / 100;
            float weight1 = Float.parseFloat(weight.getText().toString());
            Log.i(TAG, "bmi: " + height1 + " /" + weight1);
            float res = weight1 / (height1 * height1);
            Log.i(TAG, "res: " + res);

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


    }


}