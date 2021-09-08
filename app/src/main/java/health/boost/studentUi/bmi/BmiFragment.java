package health.boost.studentUi.bmi;

import static com.amazonaws.mobile.client.internal.oauth2.OAuth2Client.TAG;
import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.w3c.dom.Text;

import health.boost.MainActivity;
import health.boost.R;
import health.boost.databinding.FragmentBmiStudentBinding;

public class BmiFragment extends Fragment {

    private BmiViewModel bmiViewModel;
    private FragmentBmiStudentBinding binding;
    private Button calculatBmi;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bmiViewModel =
                new ViewModelProvider(this).get(BmiViewModel.class);

        binding = FragmentBmiStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        calculatBmi = root.findViewById(R.id.calculate_bmi);
//        final TextView textView = binding.textBmi;
        bmiViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        calculatBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText height = root.findViewById(R.id.height);
                EditText weight = root.findViewById(R.id.weight);
                TextView result = root.findViewById(R.id.result);
                TextView status = root.findViewById(R.id.status);
                bmi(weight, height, result , status);

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void bmi(EditText weight, EditText height, TextView bmires , TextView status) {
        if (height.getText().toString().isEmpty() && weight.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Both Height and Weight are Mandatory.", Toast.LENGTH_SHORT).show();
        } else {
            float height1 = Float.parseFloat(height.getText().toString()) / 100;
            float weight1 = Float.parseFloat(weight.getText().toString());
            float res = weight1 / (height1 * height1);
            status.setText(format("%.2f", res));
            if (res < 18.5) {
                bmires.setText("Underweight ");
                bmires.setBackgroundColor(Color.parseColor("#00658F"));
//                sharedbmi = "Underweight";
            }
            if (res > 18.5 && res < 24.9) {
                bmires.setText("Healthy " );
                bmires.setBackgroundColor(Color.parseColor("#63C1C6"));
//                sharedbmi = "Healthy";
            }
            if (res > 24.9 && res < 29.9) {
                bmires.setText("Overweight " );
                bmires.setBackgroundColor(Color.parseColor("#F5AD84"));
//                sharedbmi = "Overweight";
            }
            if (res > 30 && res < 34.9) {
                bmires.setText("obesity ");
                bmires.setBackgroundColor(Color.parseColor("#ED7632"));
//                sharedbmi = "obesity";
            }
            if (res > 35) {
                bmires.setText("severe obesity ");
                bmires.setBackgroundColor(Color.parseColor("#CB414C"));
//                sharedbmi = "severe obesity";
            }
        }


    }


}
