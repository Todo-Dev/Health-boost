package health.boost.coachUi.setting;

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
import android.widget.EditText;
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
import com.amplifyframework.datastore.generated.model.Coach;
import com.amplifyframework.datastore.generated.model.Student;
import com.amplifyframework.datastore.generated.model.Trainer;

import health.boost.LoginActivity;
import health.boost.R;
import health.boost.databinding.FragmentSettingCoachBinding;

public class SettingFragment extends Fragment {
    private static final String TAG = "setting";
    private SettingViewModel settingViewModel;
    private FragmentSettingCoachBinding binding;
    Trainer currentTrainer;
    Handler handler;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                new ViewModelProvider(this).get(SettingViewModel.class);

        binding = FragmentSettingCoachBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getCurrentTrainer();

        TextView email=root.findViewById(R.id.studentEmailEditing_coach);
        TextView username=root.findViewById(R.id.studentUsernameEditing_coach);
        TextView phoneNumber=root.findViewById(R.id.studentPhonenumberEditing_coach);

        handler=new Handler(Looper.getMainLooper(), msg -> {
            email.setText(currentTrainer.getEmail());
            username.setText(currentTrainer.getUsername());
            phoneNumber.setText(0+currentTrainer.getPhoneNumber().toString());

            return true;
        });

        Button saveChanges= root.findViewById(R.id.change_phone_number);
        saveChanges.setOnClickListener(v -> {
            String newPhoneNumber= phoneNumber.getText().toString();
            if (!newPhoneNumber.isEmpty()&& !newPhoneNumber.equals(currentTrainer.getPhoneNumber().toString())){
                Trainer trainer = Trainer.builder()
                        .id(currentTrainer.getId())
                        .email(currentTrainer.getEmail())
                        .username(currentTrainer.getUsername())
                        .firstName(currentTrainer.getFirstName())
                        .lastName(currentTrainer.getLastName())
                        .phoneNumber(Integer.parseInt(newPhoneNumber))
                        .role(currentTrainer.getRole())
                        .build();
                updateTrainer(trainer);
                Toast.makeText(getContext(), "data has been updated", Toast.LENGTH_SHORT).show();

            }
        });


        Button button = root.findViewById(R.id.buttonLogout_coach);
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

    public void getCurrentTrainer(){
        Amplify.API.query(ModelQuery.list(Trainer.class,Trainer.EMAIL.contains(Amplify.Auth.getCurrentUser().getUsername())),
                res-> {
                    Log.i(TAG, "getCurrentTrainer: res "+ res);
                    currentTrainer=res.getData().getItems().iterator().next();
                    Log.i(TAG, "getCurrentTrainer is : " + currentTrainer);
                    handler.sendEmptyMessage(1);
                },
                err->{
                    Log.i(TAG, "getCurrentTrainer: err " +err.toString());
                });
    }

    public void updateTrainer(Trainer trainer){
        Log.i(TAG, "update Trainer: in update");
        Amplify.API.mutate(ModelMutation.update(trainer),
                res-> {
                    Log.i(TAG, "update trainer: is updated "+ res);
                },
                err-> {
                    Log.i(TAG, "update trainer: failed to updated" + err);
                }
        );
    }

}

