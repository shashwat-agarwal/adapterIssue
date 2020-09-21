package agarwal.shashwat.ecommerce;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import agarwal.shashwat.ecommerce.data.Product;
import agarwal.shashwat.ecommerce.data.ProductViewModel;
import agarwal.shashwat.ecommerce.data.ProductViewModelFactory;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductViewModel productViewModel;
    private CartAdapter adapter;
    private static final String TAG = "CartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar=findViewById(R.id.toolbarCart);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView=findViewById(R.id.recyclerviewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setHasFixedSize(true);

        adapter=new CartAdapter(this);
        recyclerView.setAdapter(adapter);

            productViewModel =new ViewModelProvider(this, new ProductViewModelFactory(getApplication())).get(ProductViewModel.class);
            productViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    //upgradation to be done
                    //item added in cart
                     adapter.setProducts(products);
                }
            });
           adapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
               @Override
               public void onDeleteClick(int position) {
                   productViewModel.delete(adapter.getProductAt(position));
                   adapter.notifyItemRemoved(position);
               }

               @Override
               public void onUpdateClick(int position, int count) {
                   if (count!=0) {
                       Product product = adapter.getProductAt(position);
                       product.setProductPrice(count * adapter.getProductAt(position).getProductPrice());
                       productViewModel.update(product);
                       adapter.notifyDataSetChanged();
                   }else {
                       onDeleteClick(position);
                   }
               }
           });

    }

}