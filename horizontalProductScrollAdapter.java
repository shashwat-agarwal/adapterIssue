package agarwal.shashwat.ecommerce;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import agarwal.shashwat.ecommerce.data.Product;

public class horizontalProductScrollAdapter extends FirestoreRecyclerAdapter<horizontalProductModel, horizontalProductScrollAdapter.ViewHolder> {
    private static final String TAG = "horizontalProductAdpter";
    private OnItemClickListener listener;

    private Context context;

    public horizontalProductScrollAdapter(@NonNull FirestoreRecyclerOptions<horizontalProductModel> options, Context context) {
        super(options);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull horizontalProductModel model) {
        holder.productName.setText(model.getProductName());
        holder.productQuantity.setText(model.getProductQuantity());
        holder.productPrice.setText(String.valueOf(model.getProductPrice()));
        try {

            Glide.with(context)
                    .load(model.getProductImage())
                    .placeholder(R.drawable.placeholder)
                    //  .centerCrop()
                    .into(holder.productImage);
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: " + e.getMessage());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onError(@NonNull FirebaseFirestoreException e) {
        Log.d(TAG, "onError: " + e.getMessage());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice, productQuantity;
        ImageView productImage;
        Button addProduct;
        ElegantNumberButton updateProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            addProduct = itemView.findViewById(R.id.addProduct);
            updateProduct = itemView.findViewById(R.id.updateProduct);

            addProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        addProduct.setVisibility(View.INVISIBLE);
                        updateProduct.setVisibility(View.VISIBLE);
                        updateProduct.setNumber("1");
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

            updateProduct.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                @Override
                public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {

                        int count = Integer.parseInt(updateProduct.getNumber());
                        Log.d(TAG, "onValueChange: "+count+"pos"+position);
                        if (count == 0) {
                            updateProduct.setVisibility(View.INVISIBLE);
                            addProduct.setVisibility(View.VISIBLE);
                            listener.onDeleteClick(getSnapshots().getSnapshot(position),position);
                        } else
                            listener.onUpdateClick(position, count);
                    }
                }
            });
        }


    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

        void onUpdateClick(int position, int count);

        void onDeleteClick(DocumentSnapshot documentSnapshot,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }
}
