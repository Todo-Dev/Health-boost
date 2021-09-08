package health.boost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Coach;
import com.amplifyframework.datastore.generated.model.Student;
import com.amplifyframework.datastore.generated.model.Trainer;

import java.util.UUID;

public class VerificationActivity extends AppCompatActivity {

    private static final String TAG = "VerificationActivity";
    private Handler handler;
    private Handler saveHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ImageView verification_Image = findViewById(R.id.verification_image);
        EditText verification_code = findViewById(R.id.verification_code);
        Button verification_button = findViewById(R.id.verification_submitButton);

        Intent intent = getIntent();
        String username = intent.getExtras().getString("username", "");
        String password = intent.getExtras().getString("password", "");
        String email = intent.getExtras().getString("email", "");
        String role = intent.getExtras().getString("role", "");
        String phoneNumber = intent.getExtras().getString("phoneNumber", "");
        String firstName = intent.getExtras().getString("firstName", "");
        String lastName = intent.getExtras().getString("lastName", "");

        handler= new Handler(getMainLooper(), msg -> {
            verification_Image.setImageResource(R.drawable.ic_cancel);
            return false;
        });

        saveHandler = new Handler(getMainLooper(), msg -> {
            Log.i(TAG, "onCreate: role is ===> " + role);
           if (role.equals("student")){
               Trainer coach = Trainer.justId(UUID.randomUUID().toString());
               Student student = Student.builder()
                       .firstName(firstName)
                       .lastName(lastName)
                       .username(username)
                       .email(email)
                       .phoneNumber(Integer.parseInt(phoneNumber))
                       .role(role)
                       .trainer(coach)
                       .build();
               saveStudentToAPI(student);
           }else{
               Log.i(TAG, "onCreate: IN COACH ===> " + role);
               Trainer coach = Trainer.builder()
                       .firstName(firstName)
                       .lastName(lastName)
                       .username(username)
                       .email(email)
                       .phoneNumber(Integer.parseInt(phoneNumber))
                       .role(role)
                       .build();
               saveTrainerToAPI(coach);
           }
            return false;
        });



        Log.i(TAG, "onCreate: extras"+ intent);
        verification_button.setOnClickListener(v -> {
            String verification_number = verification_code.getText().toString();
            verification(username,verification_number,password);
        });

    }

    public void verification(String username, String confirmationNumber,String password) {
        Amplify.Auth.confirmSignUp(
                username,
                confirmationNumber,
                success -> {
                    Log.i(TAG, "verification: succeeded" + success.toString());
                    Intent goToSignIn = new Intent(VerificationActivity.this, LoginActivity.class);
                    saveHandler.sendEmptyMessage(2);
                    startActivity(goToSignIn);
//                    silentSignIn(username, password);
                },
                error -> {
                    Log.e(TAG, "verification: failed" + error.toString());
                    handler.sendEmptyMessage(1);
                });
    }

    public void silentSignIn(String username, String password) {
        Amplify.Auth.signIn(
                username,
                password,
                success -> Log.i(TAG, "signIn: worked " + success.toString()),
                error -> Log.e(TAG, "signIn: failed" + error.toString()));
    }

    public static void saveStudentToAPI(Student student) {
        Log.i(TAG, "saveStudentToAPI: in save student " + student);

        Amplify.API.mutate(
                ModelMutation.create(student),
                response -> Log.i("save student", "Added Todo with id: " + response.getData().getId()),
                error -> Log.e("save student", "Create failed", error)
        );
    }
    public static void saveCoachToAPI(Coach coach) {
        Log.i(TAG, "saveCoachToAPI: in save coach "+ coach);
        Amplify.API.mutate(ModelMutation.create(coach),
                response  -> Log.i(TAG, "Saved coach to api : " + response.getData()),
                error -> Log.e(TAG, "Could not save coach to API/dynamodb", error));
    }
    public static void saveTrainerToAPI(Trainer trainer) {
        Log.i(TAG, "saveCoachToAPI: in save trainer "+ trainer);
        Amplify.API.mutate(ModelMutation.create(trainer),
                response  -> Log.i(TAG, "Saved trainer to api : " + response.getData()),
                error -> Log.e(TAG, "Could not save trainer to API/dynamodb", error));
    }
}