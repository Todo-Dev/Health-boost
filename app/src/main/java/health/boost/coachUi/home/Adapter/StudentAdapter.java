package health.boost.coachUi.home.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Student;

import java.util.List;

import health.boost.R;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    private List<Student> students;
    private onStudentClickedListener listener;


    // InterFace for Events listener
    public interface onStudentClickedListener {
        void onCall(int position);
    }


    public StudentAdapter(List<Student> students, onStudentClickedListener listener) {
        this.students = students;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list, parent, false);
        return new MyViewHolder(view, listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = students.get(position);
        holder.studentName.setText(student.getFirstName() + " " + student.getLastName());
        holder.studentNumber.setText(0+student.getPhoneNumber().toString());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView studentName;
        TextView studentNumber;
        ImageButton call;
        CardView cardView;

        public MyViewHolder(View itemView, onStudentClickedListener listener) {
            super(itemView);
            studentName = itemView.findViewById(R.id.student_name);
            studentNumber = itemView.findViewById(R.id.student_phone);

            call = itemView.findViewById(R.id.call_student);

//            cardView = itemView.findViewById(R.id.cardView_nutrient);
            call.setOnClickListener(v -> listener.onCall(getAbsoluteAdapterPosition()));
        }
    }
}