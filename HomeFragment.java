package agarwal.shashwat.ecommerce.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import agarwal.shashwat.ecommerce.R;
import agarwal.shashwat.ecommerce.horizontalProductModel;
import agarwal.shashwat.ecommerce.horizontolProductScrollAdapter;
import agarwal.shashwat.ecommerce.randomAdapter;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    //private HomeViewModel homeViewModel;
    public RecyclerView recyclerView;
    private horizontolProductScrollAdapter adapter;

    private CollectionReference vegetables= FirebaseFirestore.getInstance().collection("vegetables");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.fragment_home,container,false);

        Query query = vegetables.orderBy("productName");

        FirestoreRecyclerOptions<horizontalProductModel> options=new FirestoreRecyclerOptions.Builder<horizontalProductModel>()
                .setQuery(query,horizontalProductModel.class)
                .build();
         adapter=new horizontolProductScrollAdapter(options);

       recyclerView=view.findViewById(R.id.horizontal_scroll_layout_recyclerview);
       recyclerView.setHasFixedSize(true);
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


       return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }
}

