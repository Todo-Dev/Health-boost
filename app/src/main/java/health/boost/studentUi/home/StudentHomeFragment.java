package health.boost.studentUi.home;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

    private HomeViewModel homeViewModel;
    private FragmentHomeStudentBinding binding;
    private ImageView imageViewNutrient;
    private TextView textViewNutrientTitle;

    private JSONArray JSONArr;
//    List<Nutrient> ingredientsList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
        binding = null;
    }
}