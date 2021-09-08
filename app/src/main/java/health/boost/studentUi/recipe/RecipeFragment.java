package health.boost.studentUi.recipe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import health.boost.adapter.IngredientAdapter;
import health.boost.data.Nutrient;
import health.boost.databinding.FragmentRecipeStudentBinding;


public class RecipeFragment extends Fragment {

    private RecipeViewModel recipeViewModel;
    private FragmentRecipeStudentBinding binding;
    List<Nutrient> ingredientsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EditText searchedCalorie;
    private JSONArray JSONArr;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recipeViewModel =
                new ViewModelProvider(this).get(RecipeViewModel.class);

        binding = FragmentRecipeStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        searchedCalorie = binding.editTextSearchedCalories;
        progressBar = binding.progressbarSearch;

        getResults();


        recyclerView = binding.recycleViewCaloriesSearch;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        Button btn = binding.buttonSearch;
        btn.setOnClickListener(v -> {
            // Do something in response to button click
            getSearchedResults(searchedCalorie.getText().toString());
        });

//        final TextView textView = binding.textRecipe;
//        recipeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private void getResults() {
        String url = "https://api.spoonacular.com/recipes/findByNutrients?minCarbs=10&maxCarbs=50&number=20&apiKey=0af706a2f4d74bfc8f4d0c9aaf2625d4";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        @SuppressLint("NotifyDataSetChanged") JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArr = response;
                        ingredientsList.clear();
                        for (int i = 0; i < JSONArr.length(); i++) {
                            JSONObject jsonObject = JSONArr.getJSONObject(i);
                            ingredientsList.add(new Nutrient(jsonObject.optString("title"), jsonObject.optString("image"), jsonObject.optInt("calories"),
                                    jsonObject.getString("protein"), jsonObject.getString("fat"), jsonObject.getString("carbs")));
                        }
                        progressBar.setVisibility(View.GONE);
                        IngredientAdapter myAdapter = new IngredientAdapter(getContext(), ingredientsList);
                        recyclerView.getRecycledViewPool().clear();
                        recyclerView.setAdapter(myAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.i("the res is error:", error.toString())
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void getSearchedResults(String calorieNum) {
//&number=10
//        apiKey=c957b6816ba048139fbc25a67d2cff33
        String url = "https://api.spoonacular.com/recipes/findByNutrients?maxCalories=" + calorieNum + "&apiKey=0af706a2f4d74bfc8f4d0c9aaf2625d4";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        @SuppressLint("NotifyDataSetChanged") JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArr = response;
                        ingredientsList.clear();
                        for (int i = 0; i < JSONArr.length(); i++) {
                            JSONObject jsonObject = JSONArr.getJSONObject(i);
                            ingredientsList.add(new Nutrient(jsonObject.optString("title"), jsonObject.optString("image"), jsonObject.optInt("calories"),
                                    jsonObject.getString("protein"), jsonObject.getString("fat"), jsonObject.getString("carbs")));
                        }
                        progressBar.setVisibility(View.GONE);
                        IngredientAdapter myAdapter = new IngredientAdapter(getContext(), ingredientsList);
                        recyclerView.getRecycledViewPool().clear();
                        recyclerView.setAdapter(myAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.i("the res is error:", error.toString())
        );
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}