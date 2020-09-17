package agarwal.shashwat.ecommerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import agarwal.shashwat.ecommerce.data.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewHolder> {

    private List<Product> products=new ArrayList<>();

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Product currProduct=products.get(position);
        holder.name.setText(currProduct.getProductName());
        holder.quantity.setText(currProduct.getProductQuantity());
        holder.price.setText(String.valueOf(currProduct.getProductPrice()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        private TextView name,quantity,price;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cart_product_name);
            quantity=itemView.findViewById(R.id.cart_product_quantity);
            price=itemView.findViewById(R.id.cart_product_price);
        }
    }
}
