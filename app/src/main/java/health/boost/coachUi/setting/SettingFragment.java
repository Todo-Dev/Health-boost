//package health.boost.coachUi.setting;
//
//import android.app.AlertDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.amplifyframework.core.Amplify;
//
//import health.boost.LoginActivity;
//import health.boost.R;
//import health.boost.databinding.FragmentSettingCoachBinding;
//
//public class SettingFragment extends Fragment {
//    private static final String TAG = "setting";
//    private SettingViewModel settingViewModel;
//    private FragmentSettingCoachBinding binding;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        settingViewModel =
//                new ViewModelProvider(this).get(SettingViewModel.class);
//
//        binding = FragmentSettingCoachBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        Button button = root.findViewById(R.id.buttonLogout_coach);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Confirmation PopUp!").
//                        setMessage("You sure, that you want to logout?");
//
//                builder.setPositiveButton("Yes",
//                        (dialog, id) -> { logout(); });
//
//                builder.setNegativeButton("No",
//                        (dialog, id) -> dialog.cancel());
//                AlertDialog alert11 = builder.create();
//                alert11.show();
//            }
//        });
//
//
//
//
//        final TextView textView = binding.textSettingCoach;
//        settingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//
//    public void logout(){
//        Amplify.Auth.signOut(
//                () ->{
//                    Log.i("AuthQuickstart", "Signed out successfully");
//                    Intent goToLogin = new Intent(getContext(), LoginActivity.class);
//                    startActivity(goToLogin);
//                } ,
//                error -> Log.e("AuthQuickstart", error.toString())
//        );
//    }
//
//}
//
