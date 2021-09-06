package health.boost.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import health.boost.data.Ingredient;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {

    private Context mContext;
    private List<Ingredient> mData;

    public IngredientAdapter(Context mContext, List<Ingredient> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public IngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.nutrients_list, parent, false);
        return new IngredientAdapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(IngredientAdapter.MyViewHolder holder, final int position) {
        holder.ingredientName.setText(mData.get(position).getName());

        holder.nutrientTitle.setText("Calories: " + mData.get(position).getNutrientsTitle());
        if (mData.get(position).getImage().isEmpty()) {
            holder.imgIngredient.setImageResource(R.drawable.ic_launcher_background); //احتياط
        } else {
            Picasso.get().load(mData.get(position).getImage()).into(holder.imgIngredient);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nutrientTitle;
        ImageView imgIngredient;

        TextView ingredientName;


        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            nutrientTitle = itemView.findViewById(R.id.TextView_nutrientTitle);
            imgIngredient = itemView.findViewById(R.id.imageView_ingredient);
            ingredientName = itemView.findViewById(R.id.imageView_ingredientName);

            cardView = itemView.findViewById(R.id.cardView_nutrient);
        }
    }
}