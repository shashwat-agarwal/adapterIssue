package agarwal.shashwat.ecommerce.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;

    public ProductRepository(Application application){
        ProductDatabase database=ProductDatabase.getInstance(application);
        productDao=database.productDao();
        allProducts=productDao.getAllProducts();
    }

    //these repositories will be used as apis and these will bw called by the viewmodel...
    //this way we create the abstraction layer
    public void insert(Product product){
        new InsertProductAsyncTask(productDao).execute(product);
    }
    public void update(Product product){
        new UpdateProductAsyncTask(productDao).execute(product);
    }
    public void delete(Product product){
        new DeleteProductAsyncTask(productDao).execute(product);
    }
    public void updateQuantity(String name,long userQuantity,long userPrice){
        new UpdateQuantityProductAsyncTask(productDao);
    }
    public void deleteAllProducts(){
        new DeleteAllProductsAsyncTask(productDao).execute();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    private static class InsertProductAsyncTask extends AsyncTask<Product,Void,Void>{
        private ProductDao productDao;

        private InsertProductAsyncTask (ProductDao productDao){
            this.productDao=productDao;
        }

        @Override
        protected Void doInBackground(Product... notes) {
            productDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateProductAsyncTask extends AsyncTask<Product,Void,Void>{
        private ProductDao productDao;

        private UpdateProductAsyncTask (ProductDao productDao){
            this.productDao=productDao;
        }

        @Override
        protected Void doInBackground(Product... notes) {
            productDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteProductAsyncTask extends AsyncTask<Product,Void,Void>{
        private ProductDao productDao;

        private DeleteProductAsyncTask (ProductDao productDao){
            this.productDao=productDao;
        }

        @Override
        protected Void doInBackground(Product... notes) {
            productDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllProductsAsyncTask extends AsyncTask<Void,Void,Void>{
        private ProductDao productDao;

        private DeleteAllProductsAsyncTask (ProductDao productDao){
            this.productDao=productDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            productDao.deleteAllProducts();
            return null;
        }
    }

    private static class UpdateQuantityProductAsyncTask extends AsyncTask<Product,Void,Void>{
        private ProductDao productDao;

        private UpdateQuantityProductAsyncTask (ProductDao productDao){
            this.productDao=productDao;
        }

        @Override
        protected Void doInBackground(Product... notes) {
            productDao.update(notes[0]);
            return null;
        }
    }
}

