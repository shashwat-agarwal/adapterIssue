package agarwal.shashwat.ecommerce.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProductViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public ProductViewModelFactory(Application application) {
        this.application=application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProductViewModel(application);
    }
}
