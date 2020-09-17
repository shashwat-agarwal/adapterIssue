package agarwal.shashwat.ecommerce.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import agarwal.shashwat.ecommerce.R;
import agarwal.shashwat.ecommerce.data.Product;
import agarwal.shashwat.ecommerce.data.ProductViewModel;
import agarwal.shashwat.ecommerce.data.ProductViewModelFactory;
import agarwal.shashwat.ecommerce.horizontalProductModel;
import agarwal.shashwat.ecommerce.horizontolProductScrollAdapter;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    public RecyclerView recyclerView;
    private horizontolProductScrollAdapter adapter;

    private CollectionReference vegetables= FirebaseFirestore.getInstance().collection("vegetables");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.fragment_home,container,false);

       // recycler view vegetable starts
        Query query = vegetables.orderBy("productName");

        FirestoreRecyclerOptions<horizontalProductModel> options=new FirestoreRecyclerOptions.Builder<horizontalProductModel>()
                .setQuery(query,horizontalProductModel.class)
                .build();
        adapter=new horizontolProductScrollAdapter(options);
        adapter.updateOptions(options);

       recyclerView=view.findViewById(R.id.horizontal_scroll_layout_recyclerview);
      // recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
       //recyclerView.setAdapter(new randomAdapter(1234));

       recyclerView.setAdapter(adapter);


       vegetables
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

       adapter.notifyDataSetChanged();

        final ProductViewModel productViewModel =new ViewModelProvider(this, new ProductViewModelFactory(getActivity().getApplication())).get(ProductViewModel.class);
       adapter.setOnItemClickListener(new horizontolProductScrollAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

               Product product=documentSnapshot.toObject(Product.class);
               assert product != null;
               Log.d("name_cart", ""+product.getProductName());
               productViewModel.insert(product);

           }
       });
       //recyclerview vegetable ends

       return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        if (adapter!=null) {
            Log.d(TAG, "onStart: Fragment started");

            adapter.startListening();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume: WORKING");
        adapter.notifyDataSetChanged();
    }
}

