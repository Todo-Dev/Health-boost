package health.boost;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    String role = null;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        TextView student = findViewById(R.id.student);
        TextView coach = findViewById(R.id.coach);
        EditText input_email = findViewById(R.id.signup_email);
        EditText input_username = findViewById(R.id.signup_username);
        EditText input_password = findViewById(R.id.signup_password);
        EditText input_firstName = findViewById(R.id.signup_firstName);
        EditText input_lastName = findViewById(R.id.signup_lastname);
        EditText input_mobile_number = findViewById(R.id.signup_mobile_number);
        Button signupButton = findViewById(R.id.signup_submit_button);
        Button backToLogInButton = findViewById(R.id.login_back_button);


        student.setTextAppearance(R.style.underline);
        role = "student";

        student.setOnClickListener(v -> {
            student.setTextAppearance(R.style.underline);
            coach.setTextAppearance(R.style.underlineNone);
            role = "student";
        });

        coach.setOnClickListener(v -> {
            student.setTextAppearance(R.style.underlineNone);
            coach.setTextAppearance(R.style.underline);
            input_firstName.setVisibility(View.VISIBLE);
            input_lastName.setVisibility(View.VISIBLE);
            role = "coach";
        });


        signupButton.setOnClickListener(v -> {
                Log.i(TAG, "in event listener: coach "+ role);
                String email = input_email.getText().toString();
                String username = input_username.getText().toString();
                String mobile_number = input_mobile_number.getText().toString();
                String firstName = input_firstName.getText().toString();
                String lastName = input_lastName.getText().toString();
                String password = input_password.getText().toString();
                if (!email.isEmpty() && !username.isEmpty() && !password.isEmpty() && !mobile_number.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty()) {
                    Log.i(TAG, "onCreate: "+ username+ email+ password+ mobile_number+ firstName+ lastName);
                    signUp(username, email, password, mobile_number, firstName, lastName);
                } else {
                    Toast.makeText(SignUpActivity.this, "please fill the fields", Toast.LENGTH_SHORT).show();
                }
        });

        backToLogInButton.setOnClickListener(v -> {
            Intent goToLogin = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(goToLogin);
        });

    }


    public void signUp(String username, String email, String password, String mobileNumber, String firstName, String lastName) {
        Amplify.Auth.signUp(
                username,
                password,
                AuthSignUpOptions.builder()
                        .userAttribute(AuthUserAttributeKey.email(), email)
                        .build(),
                success -> {
                    Log.i(TAG, "signUp successful: " + success.toString());
                    Intent goToVerification = new Intent(SignUpActivity.this, VerificationActivity.class);
                    goToVerification.putExtra("username", username);
                    goToVerification.putExtra("password", password);
                    goToVerification.putExtra("email", email);
                    goToVerification.putExtra("role", role);
                    goToVerification.putExtra("phoneNumber", mobileNumber);
                    goToVerification.putExtra("firstName", firstName);
                    goToVerification.putExtra("lastName", lastName);
                    startActivity(goToVerification);
                },
                error -> Log.e(TAG, "signUp failed: " + error.toString()));
    }


}