package health.boost.coachUi.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Coach;
import com.amplifyframework.datastore.generated.model.Student;
import com.amplifyframework.datastore.generated.model.Trainer;

import java.util.ArrayList;
import java.util.List;

import health.boost.LoginActivity;
import health.boost.R;
import health.boost.coachUi.home.Adapter.StudentAdapter;
import health.boost.databinding.FragmentHireCoachBinding;
import health.boost.databinding.FragmentRecipeCoachBinding;
import health.boost.studentUi.hireCoach.adapter.CoachAdapter;


public class CoachHomeFragment extends Fragment {

    private static final String TAG = "CoachHomeFragment";
    private static final int CALL_PERMISSION_REQUEST_CODE = 1234;
    private CoachHomeViewModel coachHomeViewModel;
    private FragmentHireCoachBinding binding;
    private Trainer currentCoach;
    private Handler handler;
    private StudentAdapter adapter;
    private List<Student> studentList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        coachHomeViewModel =
                new ViewModelProvider(this).get(CoachHomeViewModel.class);
        Log.i(TAG, "onCreateView: CoachHome" + currentCoach + "         " + studentList);
        binding = FragmentHireCoachBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
         studentList= new ArrayList<>();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_student1);

        try {
            getStudentFromApi();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        handler = new Handler(Looper.getMainLooper(), msg -> {
            Log.i(TAG, "onCreateView: list" + studentList.size() + studentList);
            listItemChanged();
            return false;
        });
        adapter= new StudentAdapter(studentList, new StudentAdapter.onStudentClickedListener() {
            @Override
            public void onCall(int position) {
                String phone_Number =  studentList.get(position).getPhoneNumber().toString();
                call(phone_Number);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
//        VideoView video = (VideoView) root.findViewById(R.id.videoView);
//        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                video.requestFocus();
//                video.start();
//            }
//        });
//        video.setVideoURI(Uri.parse("https://cdn.videvo.net/videvo_files/video/free/2014-06/large_watermarked/Oranges_3Videvo_preview.mp4"));



//        final TextView textView = binding.textHireCoach;
        coachHomeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }

        });


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding = null;
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
    public void getStudentFromApi() throws InterruptedException {
//            Amplify.API.query(ModelQuery.list(Trainer.class,Trainer.EMAIL.contains(Amplify.Auth.getCurrentUser().getUsername())),
//                    res-> {
//                        currentCoach=res.getData().getItems().iterator().next();
//                    },
//                    err->{
//                        Log.i(TAG, "getCurrentStudent: err " +err.toString());
//                    });
//
//        Thread.sleep(5000);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Amplify.API.query(
                ModelQuery.list(Trainer.class , Trainer.ID.contains(preferences.getString("trainerId" , ""))),
                responseCoach -> {
                    currentCoach = responseCoach.getData().getItems().iterator().next();
                    studentList = currentCoach.getStudents();
                    Log.i(TAG, "getStudentFromApi: " + currentCoach);
                    handler.sendEmptyMessage(1);
                } ,
                error2 -> Log.i(TAG, "get the coach list: QueryFailure")
        );

    }
    @SuppressLint("NotifyDataSetChanged")
    private void listItemChanged() {
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}