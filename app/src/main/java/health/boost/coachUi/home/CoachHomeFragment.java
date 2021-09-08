package health.boost.coachUi.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Coach;

import health.boost.databinding.FragmentHireCoachBinding;


public class CoachHomeFragment extends Fragment {

    private static final String TAG = "CoachHomeFragment";
    private CoachHomeViewModel coachHomeViewModel;
    private FragmentHireCoachBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        coachHomeViewModel =
                new ViewModelProvider(this).get(CoachHomeViewModel.class);

        binding = FragmentHireCoachBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Amplify.API.query(
                ModelQuery.list(Coach.class , Coach.ROLE.contains("coach")) ,
                responseCoach -> {
                    Log.i(TAG, "signIn: response" + responseCoach.getData());
                } ,
                error2 -> Log.i(TAG, "signIn: QueryFailure")
        );


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
}