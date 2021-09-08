package health.boost.studentUi.hireCoach;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Coach;
import com.amplifyframework.datastore.generated.model.Student;
import com.amplifyframework.datastore.generated.model.Trainer;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import health.boost.R;
import health.boost.databinding.FragmentHireCoachBinding;
import health.boost.studentUi.hireCoach.adapter.CoachAdapter;

public class HireCoachFragment extends Fragment {

    private static final String TAG = "hireCoach";
    private List<Trainer> coachList = new ArrayList<>();
    private CoachAdapter adapter;
    private HireCoachViewModel hireCoachViewModel;
    private FragmentHireCoachBinding binding;
    Handler handler;
    private static final int CALL_PERMISSION_REQUEST_CODE = 1234;
    Student currentStudent;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hireCoachViewModel =
                new ViewModelProvider(this).get(HireCoachViewModel.class);

        binding = FragmentHireCoachBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        coachList= new ArrayList<>();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_coach);

        getTrainerFromApi();
        getCurrentStudent();


        handler = new Handler(Looper.getMainLooper(),msg -> {
            listItemChanged();
            Log.i(TAG, "onCreateView: list" + coachList.size() + coachList);
            return false;
        });

        adapter= new CoachAdapter(coachList, new CoachAdapter.onCoachClickedListener() {
            @Override
            public void onCall(int position) {
               String phone_Number =  coachList.get(position).getPhoneNumber().toString();
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setData(Uri.parse("tel:" + 123456789));
//                startActivity(intent);
                call(phone_Number);
            }

            @Override
            public void onHire(int position) {
                Log.i(TAG, "hireCoach: in hire");
                Trainer trainer = coachList.get(position);
                Log.i(TAG, "onHire: old student" + currentStudent);
                Log.i(TAG, "onHire: the coach" + trainer);
                Student student = Student.builder()
                        .trainer(trainer)
                        .id(currentStudent.getId())
                        .email(currentStudent.getEmail())
                        .username(currentStudent.getUsername())
                        .firstName(currentStudent.getFirstName())
                        .lastName(currentStudent.getLastName())
                        .phoneNumber(currentStudent.getPhoneNumber())
                        .role(currentStudent.getRole())
                        .build();
                Log.i(TAG, "onHire: new student" + student);
                hireCoach(student);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);



        hireCoachViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void listItemChanged() {
        adapter.notifyDataSetChanged();
    }

    public void getTrainerFromApi(){
        Log.i(TAG, "getCoachFromApi: in");
        Amplify.API.query(
                ModelQuery.list(Trainer.class),
                responseCoach -> {
                    for (Trainer trainer : responseCoach.getData()){
                        coachList.add(trainer);
                        Log.i(TAG, "getTrainerFromApi: trainer" + trainer);
                    }
                    Log.i(TAG, "get the coach list: response =====>" + responseCoach);
                    handler.sendEmptyMessage(1);
                } ,
                error2 -> Log.i(TAG, "get the coach list: QueryFailure")
        );

    }

    @Override
    public void onResume() {
        super.onResume();
    }

   public void call(String number) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:0" + number));
            getActivity().startActivity(callIntent);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }
    
    public void getCurrentStudent(){
        Amplify.API.query(ModelQuery.list(Student.class,Student.EMAIL.contains(Amplify.Auth.getCurrentUser().getUsername())),
                res-> {
                    Log.i(TAG, "getCurrentStudent: res "+ res);
                    currentStudent=res.getData().getItems().iterator().next();
                    Log.i(TAG, "currentStudent is : " + currentStudent);
                },
                err->{
                    Log.i(TAG, "getCurrentStudent: err " +err.toString());
                });
    }
    public void hireCoach(Student student){
        Log.i(TAG, "hireCoach: in update");
        Amplify.API.mutate(ModelMutation.update(student),
                res-> {
            Log.i(TAG, "hireCoach: is updated "+ res);
                    Toast.makeText(getContext(), "Coach has been hired", Toast.LENGTH_SHORT).show();
                },
                err-> {
            Log.i(TAG, "hireCoach: failed to updated" + err);
                    Toast.makeText(getContext(), "something wrong happened", Toast.LENGTH_SHORT).show();
                }
                );
    }
}