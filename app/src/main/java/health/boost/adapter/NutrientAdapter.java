package health.boost.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import health.boost.R;
import health.boost.data.Nutrient;

public class NutrientAdapter extends RecyclerView.Adapter<NutrientAdapter.MyViewHolder> {

    private Context mContext;
    private List<Nutrient> mData;

    public NutrientAdapter(Context mContext, List<Nutrient> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.nutrients_list, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.nutrientTitle.setText(mData.get(position).getTitle());

        holder.nutrientCalories.setText("Calories: " + mData.get(position).getCalories());
        holder.nutrientProtein.setText("Protein: " + mData.get(position).getProtein());
        holder.nutrientFat.setText("Fat: " + mData.get(position).getFat());
        holder.nutrientCarbs.setText("Carbs: " + mData.get(position).getCarbs());
        if (mData.get(position).getImage().isEmpty()) {
            holder.imgNutrient.setImageResource(R.drawable.ic_launcher_background); //احتياط
        } else {
            Picasso.get().load(mData.get(position).getImage()).into(holder.imgNutrient);
        }
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, MainActivity.class);
////                intent.putExtra("id", mData.get(position).getId());
////                intent.putExtra("title",mData.get(position).getTitle());
////                intent.putExtra("img",mData.get(position).getThumbnail());
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nutrientTitle;
        ImageView imgNutrient;

        TextView nutrientCalories;
        TextView nutrientProtein;
        TextView nutrientFat;
        TextView nutrientCarbs;


        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            nutrientTitle = itemView.findViewById(R.id.TextView_nutrientTitle);
            imgNutrient = itemView.findViewById(R.id.imageView_nutrient);

            nutrientCalories = itemView.findViewById(R.id.TextView_nutrientCalories);
            nutrientProtein = itemView.findViewById(R.id.TextView_nutrientProtein);
            nutrientFat = itemView.findViewById(R.id.TextView_nutrientFat);
            nutrientCarbs = itemView.findViewById(R.id.TextView_nutrientCarbs);

            cardView = itemView.findViewById(R.id.cardView_nutrient);
        }
    }
}