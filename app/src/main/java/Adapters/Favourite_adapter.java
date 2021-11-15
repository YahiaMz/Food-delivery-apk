package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.aplication.dilevery_app.Fragments.Cart_Fragment;
import com.aplication.dilevery_app.R;

import org.w3c.dom.Text;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;

import Models.Cart_Item;
import Models.Favourite_model;

public class Favourite_adapter extends RecyclerView.Adapter<Favourite_adapter.Favourite_VH> {


    private ArrayList<Favourite_model> favourites;
    private Context mContext;

    public Favourite_adapter(ArrayList<Favourite_model> favourites, Context mContext) {
        this.favourites = favourites;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public Favourite_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.favourite_item_layout , parent , false);
        return new Favourite_VH(view );
    }

    @Override
    public void onBindViewHolder(@NonNull Favourite_VH holder, @SuppressLint("RecyclerView") int position) {
        Favourite_model current_item = this.favourites.get(position);
        holder.image.setImageResource(current_item.getImage());
        holder.price.setText(current_item.getPrice()  +"Da");
        holder.name.setText(current_item.getName());


        holder.bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     Favourite_model current_Item = favourites.get(position);

                int p;
                if(Cart_Adapter.items == null){
                    p = Cart_Fragment.has(current_Item.getId());
                    if (p != -1) {
                        Cart_Fragment.items.get(p).setQuantity(Cart_Adapter.items.get(p).getQuantity() + 1);
                    }
                    else { Cart_Fragment.items.add(new Cart_Item(current_Item.getId(), current_Item.getName() , current_Item.getPrice()  , "" )); }

                } else {

                    p = Cart_Adapter.has(current_Item.getId());
                    if (p != -1) {
                        Cart_Fragment.items.get(p).setQuantity(Cart_Adapter.items.get(p).getQuantity() + 1); }
                    else {
                        Cart_Fragment.items.add(new Cart_Item(current_Item.getId(), current_Item.getName() ,
                                current_Item.getPrice() , "" ));

                    }

                }

                Toast toast = new Toast(mContext);

                toast.setGravity(Gravity.BOTTOM,0 , 150);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(LayoutInflater.from(mContext).inflate(R.layout.succes_taost , null , false));
                toast.show();





            }
        });

    }

    @Override
    public int getItemCount() {
        return this.favourites.size();
    }

    public class Favourite_VH extends RecyclerView.ViewHolder {

        public  TextView price , name;
        public  ImageView image,bAdd;


        public Favourite_VH(@NonNull View itemView) {
             super(itemView);
            this.image = itemView.findViewById(R.id.food_image);
            this.name = itemView.findViewById(R.id.food_name);
            this.price = itemView.findViewById(R.id.food_price);
            this.bAdd = itemView.findViewById(R.id.addFood_btn);
        }
    }
}
