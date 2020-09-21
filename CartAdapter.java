package agarwal.shashwat.ecommerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import agarwal.shashwat.ecommerce.data.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewHolder> {

    private List<Product> products=new ArrayList<>();
    private OnItemClickListener listener;
    private Context context;

    public CartAdapter(Context context) {
        this.context = context;
    }

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
        Glide.with(context).load(currProduct.getProductImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }
    public Product getProductAt(int position){
        return products.get(position);
    }

    class viewHolder extends RecyclerView.ViewHolder{
        private TextView name,quantity,price;
        private ImageView deleteProductItem,image;
        private ElegantNumberButton addProductItem;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.productNameCart);
            quantity=itemView.findViewById(R.id.productQuantityCart);
            price=itemView.findViewById(R.id.productPriceCart);
            image=itemView.findViewById(R.id.productImageCart);
            deleteProductItem=itemView.findViewById(R.id.productDeleteCart);
            addProductItem=itemView.findViewById(R.id.addProductCart);

            deleteProductItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION && listener!=null){
                        listener.onDeleteClick(position);
                    }
                }
            });

            addProductItem.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                @Override
                public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        int count = Integer.parseInt(addProductItem.getNumber());
                        listener.onUpdateClick(position,count);
                    }
                }
            });
        }

    }
    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onUpdateClick(int position,int count);
    }
    public void setOnItemClickListener(CartAdapter.OnItemClickListener listener){

        this.listener=listener;
    }
}
