package health.boost.coachUi.home;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.GridLayoutManager;

import health.boost.R;
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


//        VideoView video = (VideoView) root.findViewById(R.id.videoView);
//        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                video.requestFocus();
//                video.start();
//            }
//        });
//        video.setVideoURI(Uri.parse("https://cdn.videvo.net/videvo_files/video/free/2014-06/large_watermarked/Oranges_3Videvo_preview.mp4"));



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