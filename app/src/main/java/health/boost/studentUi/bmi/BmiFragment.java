package health.boost.studentUi.bmi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import health.boost.databinding.FragmentBmiStudentBinding;

public class BmiFragment extends Fragment {

    private BmiViewModel bmiViewModel;
    private FragmentBmiStudentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bmiViewModel =
                new ViewModelProvider(this).get(BmiViewModel.class);

        binding = FragmentBmiStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textBmi;
        bmiViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
