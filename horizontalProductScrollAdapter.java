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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

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
           holder.productPrice.setText(model.getProductPrice()+"");
           try {

               Glide.with(context)
                       .load(model.getProductImage())
                     //  .centerCrop()
                       .into(holder.productImage);
           }catch (Exception e){
               Log.e(TAG, "onBindViewHolder: "+e.getMessage() );
           }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, parent,false);
       return new ViewHolder(v);
    }



    @Override
    public void onError(@NonNull FirebaseFirestoreException e) {
        Log.d(TAG, "onError: "+e.getMessage());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName,productPrice,productQuantity;
        ImageView productImage;
        Button addProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.productImage);
            productName=itemView.findViewById(R.id.productName);
            productPrice=itemView.findViewById(R.id.productPrice);
            productQuantity=itemView.findViewById(R.id.productQuantity);
            addProduct=itemView.findViewById(R.id.addProduct);

            addProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }


    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener=listener;
    }
}
