package health.boost.studentUi.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import health.boost.R;
import health.boost.databinding.FragmentHomeStudentBinding;

public class StudentHomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeStudentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        VideoView videoView =binding.videoView;
        videoView.setVideoPath("https://cdn.videvo.net/videvo_files/video/free/2014-06/large_watermarked/Oranges_3Videvo_preview.mp4");

        MediaController mediaController = new MediaController(getContext());
        //link mediaController to videoView
        mediaController.setAnchorView(videoView);
        //allow mediaController to control our videoView
        videoView.setMediaController(mediaController);
        videoView.start();


        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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