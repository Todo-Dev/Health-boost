package health.boost;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.GraphQLRequest;
import com.amplifyframework.api.graphql.PaginatedResult;
import com.amplifyframework.api.graphql.model.ModelPagination;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Coach;
import com.amplifyframework.datastore.generated.model.Student;
import com.amplifyframework.datastore.generated.model.Trainer;

import java.util.NoSuchElementException;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    String currentLoggedId;


 ;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText login_email_input = findViewById(R.id.login_email);
        EditText login_password_input = findViewById(R.id.login_password);
        Button login_submit_button = findViewById(R.id.login_button);
        Button login_to_signup_button = findViewById(R.id.signup_button);

//        try {
//            getApi();
//            Thread.sleep(2500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        login_submit_button.setOnClickListener(v -> {
            String email = login_email_input.getText().toString();
            String password = login_password_input.getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                signIn(email, password);


            } else {
                login_email_input.setOutlineAmbientShadowColor(R.color.red);
                login_password_input.setOutlineAmbientShadowColor(R.color.red);
                Toast.makeText(LoginActivity.this, "please fill the fields", Toast.LENGTH_SHORT).show();
            }
        });

        login_to_signup_button.setOnClickListener(v -> {
            Intent goToSignUp = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(goToSignUp);
        });

    }

    public void signIn(String username, String password) {
        Amplify.Auth.signIn(
                username,
                password,
                success -> {
                    Log.i(TAG, "signIn: worked " + success.toString());
                    Amplify.API.query(
                            ModelQuery.list(Student.class, Student.EMAIL.contains(username)),
                            response -> {
                                try {



                                    if (response.getData().getItems().iterator().next().getRole().equals("student")) {

                                        Intent studentIntent = new Intent(getApplicationContext(), StudentActivity.class);


                                        startActivity(studentIntent);




                                    }
                                } catch (NoSuchElementException e) {
                                    Amplify.API.query(
                                            ModelQuery.list(Trainer.class, Trainer.EMAIL.contains(username)),
                                            responseCoach -> {
                                                Log.i(TAG, "signIn: response" + responseCoach.getData());
                                            },
                                            error2 -> Log.i(TAG, "signIn: QueryFailure")
                                    );
                                    Intent anotherIntent = new Intent(getApplicationContext(), CoachActivity.class);
                                    startActivity(anotherIntent);
                                }

                                Log.i(TAG, "signIn: ");
                                Log.i(TAG, "signIn: response" + response.getData());
                            },
                            error -> Log.e("MyAmplifyApp", "Query failure", error)

                    );
                },
                error -> Log.e(TAG, "signIn: failed" + error.toString()));


    }

    public void getApi() {
        Amplify.API.query(
                ModelQuery.list(Student.class),
                responseCoach -> {
                    Log.i(TAG, "get the STUDENT list: response =====>" + responseCoach);
                },
                error2 -> Log.i(TAG, "get the STUDENT list: QueryFailure")
        );


        Amplify.API.query(
                ModelQuery.list(Trainer.class),
                responseCoach -> {
                    Log.i(TAG, "get the Trainer list: response =====>" + responseCoach);
                },
                error2 -> Log.i(TAG, "get the Trainer list: QueryFailure")
        );
//        Amplify.API.query(
//                ModelQuery.list(Coach.class, Coach.ROLE.contains("coach")),
//                response -> {
//                    for (Coach todo : response.getData()) {
//                    Log.i("MyAmplifyApp", String.valueOf(todo));
//                    }
//                },
//                error -> Log.e("MyAmplifyApp", "Query failure", error)
//        );
        Amplify.API.query(
                ModelQuery.get(Coach.class,"d3a25b03-b888-432a-866a-289548b77553"),
                response -> Log.i("get the single  coach " , String.valueOf(response)),
                error -> Log.e("get the single  coach", error.toString(), error)
        );
//        queryFirstPage();
    }
    public void queryFirstPage() {
        query(ModelQuery.list(Coach.class, ModelPagination.limit(2)));
    }

    private static void query(GraphQLRequest<PaginatedResult<Coach>> request) {
        Log.i(TAG, "query: in");
        Amplify.API.query(
                request,
                response -> {
                    Log.i(TAG, "query: "+response);
                    if (response.hasData()) {
                        for (Coach todo : response.getData()) {
                            Log.i("MyAmplifyApp", todo.getEmail());
                        }
                        if (response.getData().hasNextResult()) {
                            query(response.getData().getRequestForNextResult());
                        }
                    }
                },
                failure -> Log.e("MyAmplifyApp", "Query failed.", failure)
        );
    }
}
