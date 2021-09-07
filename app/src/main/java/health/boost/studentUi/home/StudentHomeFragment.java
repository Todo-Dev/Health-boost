package health.boost.studentUi.home;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import health.boost.R;
import health.boost.data.Nutrient;
import health.boost.databinding.FragmentHomeStudentBinding;

public class StudentHomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeStudentBinding binding;
    private ImageView imageViewNutrient;
    private TextView textViewNutrientTitle;
    private TextView textViewNutrientCalories;
    private TextView textViewNutrientProtein;
    private TextView textViewNutrientFat;
    private TextView textViewNutrientCarbs;
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

        textViewNutrientCalories = root.findViewById(R.id.textView_nutrientCalories);
        textViewNutrientProtein = root.findViewById(R.id.textView_nutrientProtein);
        textViewNutrientFat = root.findViewById(R.id.textView_nutrientFat);
        textViewNutrientCarbs = root.findViewById(R.id.textView_nutrientCarbs);
        getResults();

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