package health.boost.studentUi.hireCoach.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Coach;
import com.amplifyframework.datastore.generated.model.Trainer;
import com.squareup.picasso.Picasso;

import java.util.List;

import health.boost.R;

public class CoachAdapter extends RecyclerView.Adapter<CoachAdapter.MyViewHolder> {

    private List<Trainer> coaches;
    private onCoachClickedListener listener;


    // InterFace for Events listener
    public interface onCoachClickedListener {

        void onCall(int position);

        void onHire(int position);
    }


    public CoachAdapter(List<Trainer> coaches, onCoachClickedListener listener) {
        this.coaches = coaches;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coach_card, parent, false);
        return new MyViewHolder(view, listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Trainer coach = coaches.get(position);
        holder.coachName.setText(coach.getFirstName() + " " + coach.getLastName());
        holder.coachNumber.setText(0+coach.getPhoneNumber().toString());
    }

    @Override
    public int getItemCount() {
        return coaches.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView coachName;
        TextView coachNumber;
        ImageButton call;
        ImageButton hire;


        CardView cardView;

        public MyViewHolder(View itemView, onCoachClickedListener listener) {
            super(itemView);
            coachName = itemView.findViewById(R.id.coach_name);
            coachNumber = itemView.findViewById(R.id.coach_phone);

            call = itemView.findViewById(R.id.call);
            hire = itemView.findViewById(R.id.hire_coach);

//            cardView = itemView.findViewById(R.id.cardView_nutrient);
            call.setOnClickListener(v -> listener.onCall(getAbsoluteAdapterPosition()));
            hire.setOnClickListener(v -> listener.onHire(getAbsoluteAdapterPosition()));
        }
    }
}