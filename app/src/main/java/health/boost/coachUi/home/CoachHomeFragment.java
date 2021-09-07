package health.boost.coachUi.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

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
import health.boost.coachUi.recipe.RecipeViewModel;
import health.boost.data.Nutrient;
import health.boost.databinding.FragmentHireCoachBinding;
import health.boost.databinding.FragmentRecipeCoachBinding;


public class CoachHomeFragment extends Fragment {

    private CoachHomeViewModel coachHomeViewModel;
    private FragmentHireCoachBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        coachHomeViewModel =
                new ViewModelProvider(this).get(CoachHomeViewModel.class);

        binding = FragmentHireCoachBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textHireCoach;
        coachHomeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }

        });


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}