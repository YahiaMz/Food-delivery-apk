package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.CellSignalStrength;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aplication.dilevery_app.Fragments.Home_Fragment;
import com.aplication.dilevery_app.MainActivity;
import com.aplication.dilevery_app.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import Models.Category;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.Category_VH> {

    private ArrayList<Category> categories ;
    private Context context;
    int selected_item = 0;


    public Category_Adapter(ArrayList<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }


    @NonNull
    @Override
    public Category_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.category_item_layout , parent , false);
        return new Category_VH(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Category_VH holder, @SuppressLint("RecyclerView") int position) {

        holder.c_name.setText(this.categories.get(position).getName());
        holder.c_image.setImageResource(this.categories.get(position).getImage());
        holder.cardView.setStrokeWidth(0);
        holder.cardView.animate().scaleX(1.0f);
        holder.cardView.animate().scaleY(1.0f);

        if(selected_item == position) {
            holder.cardView.setStrokeWidth(4);
            holder.cardView.animate().scaleX(1.1f);
            holder.cardView.animate().scaleY(1.2f);

        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                selected_item = position;
                notifyDataSetChanged();
                Home_Fragment.food_adapter.update_data( Home_Fragment.filter_food(Home_Fragment.foods, categories.get(position).getId()));
                Home_Fragment.foodRV.startLayoutAnimation();
            }

        });

    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }

    public  class Category_VH extends RecyclerView.ViewHolder {
        public ImageView c_image;
        public TextView c_name;
        MaterialCardView cardView;

        public Category_VH(@NonNull View itemView) {
            super(itemView);
            this.c_image = itemView.findViewById(R.id.category_image);
            this.c_name = itemView.findViewById(R.id.category_name);
            this.cardView = itemView.findViewById(R.id.Category_CV);
        }
    }
}
