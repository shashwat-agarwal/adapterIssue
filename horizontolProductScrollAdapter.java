package agarwal.shashwat.ecommerce;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class horizontolProductScrollAdapter  extends FirestoreRecyclerAdapter<horizontalProductModel, horizontolProductScrollAdapter.ViewHolder> {
    private static final String TAG = "horizontalProductAdpter";


    public horizontolProductScrollAdapter(@NonNull FirestoreRecyclerOptions<horizontalProductModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull horizontalProductModel model) {
           holder.productName.setText(model.getProductName());
           holder.productQuantity.setText(model.getProductQuantity());
           holder.productPrice.setText(model.getProductPrice()+"");
           try {
               Picasso.get().load(model.getProductImage()).centerCrop().fit().into(holder.productImage);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.productImage);
            productName=itemView.findViewById(R.id.productName);
            productPrice=itemView.findViewById(R.id.productPrice);
            productQuantity=itemView.findViewById(R.id.productQuantity);
        }
    }
}
