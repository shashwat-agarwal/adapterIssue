package agarwal.shashwat.ecommerce;

import android.content.Intent;
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

import agarwal.shashwat.ecommerce.data.Product;
import agarwal.shashwat.ecommerce.data.ProductViewModel;
import agarwal.shashwat.ecommerce.data.ProductViewModelFactory;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    private horizontalProductScrollAdapter adapterVegetable,adapterDailyNeeds,adapterMilk;
    private CartAdapter adapter;

    private CollectionReference vegetables= FirebaseFirestore.getInstance().collection("vegetables");
    private CollectionReference dailyNeeds= FirebaseFirestore.getInstance().collection("dailyNeeds");
    private CollectionReference milk= FirebaseFirestore.getInstance().collection("milk");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.fragment_home,container,false);

       adapter=new CartAdapter(getContext());

        view.findViewById(R.id.viewAllButton_vegetable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), verticalScrollActivity.class);
                intent.putExtra("collection","vegetables");
                startActivity(intent);
            }
        });
        view.findViewById(R.id.viewAllButton_dailyneeds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), verticalScrollActivity.class);
                intent.putExtra("collection","dailyNeeds");
                startActivity(intent);
            }
        });
        view.findViewById(R.id.viewAllButton_milk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), verticalScrollActivity.class);
                intent.putExtra("collection","milk");
                startActivity(intent);
            }
        });

        view.findViewById(R.id.linearLayoutVegetableCategory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), verticalScrollActivity.class);
                intent.putExtra("collection","vegetables");
                startActivity(intent);
            }
        });
        view.findViewById(R.id.linearLayoutDailyNeedsCategory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), verticalScrollActivity.class);
                intent.putExtra("collection","dailyNeeds");
                startActivity(intent);
            }
        });
        view.findViewById(R.id.linearLayoutMilkCategory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), verticalScrollActivity.class);
                intent.putExtra("collection","milk");
                startActivity(intent);
            }
        });

      recyclerViewVegetable(view);
      recyclerViewDailyNeeds(view);
      recyclerViewMilk(view);




       return view;
    }

    private void recyclerViewVegetable(View view) {



        Query query = vegetables.orderBy("productName");

        FirestoreRecyclerOptions<horizontalProductModel> options=new FirestoreRecyclerOptions.Builder<horizontalProductModel>()
                .setQuery(query,horizontalProductModel.class)
                .build();
        adapterVegetable=new horizontalProductScrollAdapter(options,getContext());
        adapterVegetable.updateOptions(options);

        RecyclerView recyclerView=view.findViewById(R.id.horizontal_scroll_layout_recyclerview_vegetable);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setAdapter(adapterVegetable);


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

        adapterVegetable.notifyDataSetChanged();

        final ProductViewModel productViewModel =new ViewModelProvider(this, new ProductViewModelFactory(getActivity().getApplication())).get(ProductViewModel.class);
        adapterVegetable.setOnItemClickListener(new horizontalProductScrollAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Product product=documentSnapshot.toObject(Product.class);
                assert product != null;
                Log.d("inserted_in_cart", ""+product.getProductName());
                productViewModel.insert(product);

            }

            @Override
            public void onUpdateClick(int position, int count) {

            }

            @Override
            public void onDeleteClick(DocumentSnapshot documentSnapshot, int position) {
                Product product=documentSnapshot.toObject(Product.class);
                productViewModel.delete(product);

                Log.d(TAG, "onDeleteClick: deleted");
                adapter.notifyDataSetChanged();
            }


        });
    }

    private void recyclerViewDailyNeeds(View view) {

        Query query = dailyNeeds.orderBy("productName");

        FirestoreRecyclerOptions<horizontalProductModel> options=new FirestoreRecyclerOptions.Builder<horizontalProductModel>()
                .setQuery(query,horizontalProductModel.class)
                .build();
        adapterDailyNeeds=new horizontalProductScrollAdapter(options,getContext());
        adapterDailyNeeds.updateOptions(options);

        RecyclerView recyclerView=view.findViewById(R.id.horizontal_scroll_layout_recyclerview_dailyneeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setAdapter(adapterDailyNeeds);


        dailyNeeds
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

        adapterDailyNeeds.notifyDataSetChanged();

        final ProductViewModel productViewModel =new ViewModelProvider(this, new ProductViewModelFactory(getActivity().getApplication())).get(ProductViewModel.class);
        adapterDailyNeeds.setOnItemClickListener(new horizontalProductScrollAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Product product=documentSnapshot.toObject(Product.class);
                assert product != null;
                Log.d("inserted_in_cart", ""+product.getProductName());
                productViewModel.insert(product);

            }

            @Override
            public void onUpdateClick(int position, int count) {

            }

            @Override
            public void onDeleteClick(DocumentSnapshot documentSnapshot, int position) {
                Product product=documentSnapshot.toObject(Product.class);
                productViewModel.delete(product);

                adapter.notifyDataSetChanged();
            }
        });
    }

    private void recyclerViewMilk(View view) {

        Query query = milk.orderBy("productName");

        FirestoreRecyclerOptions<horizontalProductModel> options=new FirestoreRecyclerOptions.Builder<horizontalProductModel>()
                .setQuery(query,horizontalProductModel.class)
                .build();
        adapterMilk=new horizontalProductScrollAdapter(options,getContext());
        adapterMilk.updateOptions(options);

        RecyclerView recyclerView=view.findViewById(R.id.horizontal_scroll_layout_recyclerview_milk);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setAdapter(adapterMilk);


        milk
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

        adapterMilk.notifyDataSetChanged();

        final ProductViewModel productViewModel =new ViewModelProvider(this, new ProductViewModelFactory(getActivity().getApplication())).get(ProductViewModel.class);
        adapterMilk.setOnItemClickListener(new horizontalProductScrollAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Product product=documentSnapshot.toObject(Product.class);
                assert product != null;
                Log.d("inserted_in_cart", ""+product.getProductName());
                productViewModel.insert(product);

            }

            @Override
            public void onUpdateClick(int position, int count) {

            }

            @Override
            public void onDeleteClick(DocumentSnapshot documentSnapshot, int position) {
                Product product=documentSnapshot.toObject(Product.class);
                productViewModel.delete(product);

                Log.d(TAG, "onDeleteClick: deleted");
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        if (adapterVegetable!=null) {
            Log.d(TAG, "onStart: Fragment started");

            adapterVegetable.startListening();
            adapterDailyNeeds.startListening();
            adapterMilk.startListening();
            adapterVegetable.notifyDataSetChanged();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterVegetable.stopListening();
        adapterDailyNeeds.stopListening();
        adapterMilk.stopListening();
    }


    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume: WORKING");
        adapterVegetable.notifyDataSetChanged();
        adapterDailyNeeds.notifyDataSetChanged();
        adapterMilk.notifyDataSetChanged();
    }

}

