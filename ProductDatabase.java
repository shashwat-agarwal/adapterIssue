package agarwal.shashwat.ecommerce.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Product.class},version = 8)
public abstract class ProductDatabase extends RoomDatabase {

    public abstract ProductDao productDao();

   private static ProductDatabase instance;

    public static synchronized ProductDatabase getInstance(Context context){

        if (instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    ProductDatabase.class,"product_database")
                    .fallbackToDestructiveMigration()

                    .build();
        }
        return instance;
    }

//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
//        }
//    };
//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//        private ProductDao productDao;
//
//        private PopulateDbAsyncTask(ProductDatabase db) {
//            productDao = db.productDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            productDao.insert(new Product("Potato", "28", 28l));
//            productDao.insert(new Product("Tomato", "50", 500l));
//            productDao.insert(new Product("Coriander", "80", 100l));
//            return null;
//        }
//    }
}
