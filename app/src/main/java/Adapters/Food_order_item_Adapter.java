package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aplication.dilevery_app.HELPER;
import com.aplication.dilevery_app.R;
import com.squareup.picasso.Picasso;

import java.nio.InvalidMarkException;
import java.util.ArrayList;

import Models.Food;
import Models.Food_Order;

public class Items_item_order_Adapter extends RecyclerView.Adapter<Items_item_order_Adapter.this_Vh> {

    private ArrayList<Food_Order> food_orders ;
    private Context mContext;

    public Items_item_order_Adapter(ArrayList<Food_Order> food_orders, Context mContext) {
        this.food_orders = food_orders;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public this_Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_item_order , parent , false);
        return new this_Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull this_Vh holder, int position) {

        Food_Order current_Item = this.food_orders.get(position);

        Picasso.get().load(HELPER.URL + "/").into(holder.foodImage);
        holder.food_Quantity.setText("x" + current_Item.getQuantity() );
        holder.food_name.setText(current_Item.getName() );


    }

    @Override
    public int getItemCount() {
        return this.food_orders.size();
    }

    public class this_Vh extends RecyclerView.ViewHolder {

        public ImageView foodImage;
        public TextView food_name , food_Quantity;


        public this_Vh(@NonNull View itemView) {
            super(itemView);
            this.foodImage = itemView.findViewById(R.id.food_Order_image);
            this.food_name = itemView.findViewById(R.id.food_order_name);
            this.food_Quantity = itemView.findViewById(R.id.food_order_qantity);

        }
    }
}
