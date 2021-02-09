//package com.thp.shopster.ui.home;
//
//import android.app.Application;
//
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.thp.shopster.model.Product;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class HomeViewModel extends AndroidViewModel {
//
////    private MutableLiveData<String> mProducts;
////
////    public HomeViewModel() {
////        mText = new MutableLiveData<>();
////        mText.setValue("This is home fragment");
////    }
////
////    public LiveData<String> getText() {
////        return mText;
////    }
//
//    private MutableLiveData<List<Product>> mProducts;
//
//    public HomeViewModel(Application application) {
//        super(application);
//        mProducts = new MutableLiveData<>();
//    }
//
//    public MutableLiveData<List<Product>> getProducts() {
//        return mProducts;
//    }
//
//    public void setProducts(){
//        List<Product> list = new ArrayList<>();
//
//        list.add(new Product("unique1", "Product 1", "123 321", "url", 24.99, 9.96));
//        list.add(new Product("unique1", "Product 2", "123 321", "url", 24.99, 9.96));
//        list.add(new Product("unique1", "Product 3", "123 321", "url", 24.99, 9.96));
//        list.add(new Product("unique1", "Product 3", "123 321", "url", 24.99, 9.96));
//
//        mProducts.setValue(list);
//    }
//}