package health.boost.studentUi.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Student;

import health.boost.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import health.boost.R;
import health.boost.data.Nutrient;
import health.boost.databinding.FragmentHomeStudentBinding;

public class StudentHomeFragment extends Fragment {

    private static final String TAG = "StudentHomeFragment";
    private HomeViewModel homeViewModel;
    private FragmentHomeStudentBinding binding;
    Student currentStudent;
    Handler handler;
    private static final int CALL_PERMISSION_REQUEST_CODE = 1235;
    private ImageView imageViewNutrient;
    private TextView textViewNutrientTitle;

    private JSONArray JSONArr;



    @SuppressLint({"SetTextI18n", "WrongConstant"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getCurrentStudent();

        TextView coachNameLabel = root.findViewById(R.id.coach_name_title_home);
        TextView coachName = root.findViewById(R.id.coach_name_home);
        TextView coachPhoneLabel = root.findViewById(R.id.coach_phone_title_home);
        TextView coachPhone = root.findViewById(R.id.coach_phone_home);
        ImageButton call = root.findViewById(R.id.call_home);

        handler= new Handler(Looper.getMainLooper(),msg -> {
            if (currentStudent.getTrainer()!=null){
                coachName.setText(currentStudent.getTrainer().getFirstName()+currentStudent.getTrainer().getLastName());
                coachPhone.setText(currentStudent.getTrainer().getPhoneNumber().toString());
                coachName.setVisibility(View.VISIBLE);
                coachPhoneLabel.setVisibility(View.VISIBLE);
                coachPhone.setVisibility(View.VISIBLE);
                call.setVisibility(View.VISIBLE);
            }else {
                coachNameLabel.setText("Please hire a Coach");
                coachNameLabel.setTextSize(28);

                coachName.setVisibility(View.GONE);
                coachPhoneLabel.setVisibility(View.GONE);
                coachPhone.setVisibility(View.GONE);
                call.setVisibility(View.GONE);
            }
            return true;
        });
        call.setOnClickListener(v -> {
            String phoneNumber = coachPhone.getText().toString();
            call(phoneNumber);
        });
        imageViewNutrient = binding.imageViewNutrient;
        textViewNutrientTitle = binding.textViewNutrientTitle;


        getResults();

        VideoView videoView =binding.videoView;
        videoView.setVideoPath("https://cdn.videvo.net/videvo_files/video/free/2014-06/large_watermarked/Oranges_3Videvo_preview.mp4");

        MediaController mediaController = new MediaController(getContext());
        //link mediaController to videoView
        mediaController.setAnchorView(videoView);
        //allow mediaController to control our videoView
        videoView.setMediaController(mediaController);
        videoView.start();


//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private void getResults() {
        String url = "https://api.spoonacular.com/recipes/random?number=1&instructionsRequired=true&apiKey=c957b6816ba048139fbc25a67d2cff33";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArr = (JSONArray) response.get("recipes");
                        JSONObject jsonObject = JSONArr.getJSONObject(0);
                        Nutrient nutrient = new Nutrient(jsonObject.optString("title"), jsonObject.optString("image"));
                        textViewNutrientTitle.setText(nutrient.getTitle());
                        Picasso.get().load(nutrient.getImage()).into(imageViewNutrient);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.i("the res is error:", error.toString());
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        binding = null;
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

    @Override
    public void onResume() {
        super.onResume();
    }
}