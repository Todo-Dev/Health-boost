package health.boost.studentUi.setting;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Student;

import health.boost.LoginActivity;
import health.boost.R;
import health.boost.databinding.FragmentSettingStudentBinding;

public class SettingFragment extends Fragment {
    private static final String TAG = "setting";
    private SettingViewModel settingViewModel;
    private FragmentSettingStudentBinding binding;
    Handler handler;
    Student currentStudent;
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                new ViewModelProvider(this).get(SettingViewModel.class);


        binding = FragmentSettingStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getCurrentStudent();
        TextView email=root.findViewById(R.id.studentEmailEditing);
        TextView username=root.findViewById(R.id.studentUsernameEditing);
        TextView phoneNumber=root.findViewById(R.id.studentPhonenumberEditing);

        handler=new Handler(Looper.getMainLooper(),msg -> {
            email.setText(currentStudent.getEmail());
            username.setText(currentStudent.getUsername());
            phoneNumber.setText(0+currentStudent.getPhoneNumber().toString());

            return true;
        });

        Button saveChanges= root.findViewById(R.id.change_phone_number);

        saveChanges.setOnClickListener(v -> {
            String newPhoneNumber= phoneNumber.getText().toString();
            if (!newPhoneNumber.isEmpty()&& !newPhoneNumber.equals(currentStudent.getPhoneNumber().toString())){
                Student student = Student.builder()
                        .trainer(currentStudent.getTrainer())
                        .id(currentStudent.getId())
                        .email(currentStudent.getEmail())
                        .username(currentStudent.getUsername())
                        .firstName(currentStudent.getFirstName())
                        .lastName(currentStudent.getLastName())
                        .phoneNumber(Integer.parseInt(newPhoneNumber))
                        .role(currentStudent.getRole())
                        .build();
                updateStudent(student);
                Toast.makeText(getContext(), "Coach has been hired", Toast.LENGTH_SHORT).show();
            }
        });


        Button button = root.findViewById(R.id.buttonLogout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirmation PopUp!").
                        setMessage("You sure, that you want to logout?");

                builder.setPositiveButton("Yes",
                        (dialog, id) -> { logout(); });

                builder.setNegativeButton("No",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });





        settingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void logout(){
        Amplify.Auth.signOut(
                () ->{
                    Log.i("AuthQuickstart", "Signed out successfully");
                    Intent goToLogin = new Intent(getContext(), LoginActivity.class);
                    startActivity(goToLogin);
                } ,
                error -> Log.e("AuthQuickstart", error.toString())
        );
    }

    public void getCurrentStudent(){
        Amplify.API.query(ModelQuery.list(Student.class,Student.EMAIL.contains(Amplify.Auth.getCurrentUser().getUsername())),
                res-> {
                    Log.i(TAG, "getCurrentStudent: res "+ res);
                    currentStudent=res.getData().getItems().iterator().next();
                    Log.i(TAG, "currentStudent is : " + currentStudent);
                    handler.sendEmptyMessage(1);
                },
                err->{
                    Log.i(TAG, "getCurrentStudent: err " +err.toString());
                });
    }

    public void updateStudent(Student student){
        Log.i(TAG, "update student: in update");
        Amplify.API.mutate(ModelMutation.update(student),
                res-> {
                    Log.i(TAG, "update student: is updated "+ res);
                },
                err-> {
                    Log.i(TAG, "update student: failed to updated" + err);
                }
        );
    }


}

