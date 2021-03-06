package agarwal.shashwat.ecommerce;

import android.os.Bundle;

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

        final CartAdapter adapter=new CartAdapter();
        recyclerView.setAdapter(adapter);

            productViewModel =new ViewModelProvider(this, new ProductViewModelFactory(getApplication())).get(ProductViewModel.class);
            productViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    //updation to be done
                    //item added in cart
                     adapter.setProducts(products);
                }
            });

    }
}