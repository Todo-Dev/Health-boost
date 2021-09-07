package health.boost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_main_coach extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_coach);

        Button studentActivity = findViewById(R.id.gotostudentactivity);
        studentActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StudentActivity.class);
                startActivity(intent);
            }
        });

        Button coachActivity = findViewById(R.id.gotocoachactivity);
        coachActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CoachActivity.class);
                startActivity(intent);
            }
        });
    }
}