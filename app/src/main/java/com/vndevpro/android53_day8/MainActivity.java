package com.vndevpro.android53_day8;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://dummyjson.com/";
    private Retrofit mRetrofit;
    private DummyServices dummyServices;

    private BottomNavigationView bottom_nav;
    private ArrayList<Product> mListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mRetrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
        initView();
        loadListProduct();

    }

    private void initView() {
        bottom_nav = findViewById(R.id.bottom_nav);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottom_nav, navController);

//        navController.navigate;
        Bundle bundle = new Bundle();
        bundle.putString("ID_PRODUCT", "1");
        navController.navigate(R.id.action_homeFragment_to_productDetailsFragment, bundle);
    }

    private void loadListProduct() {
        dummyServices = RetrofitClient.create(DummyServices.class);

        dummyServices.getProducts().enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        ProductsResponse productsResponse = response.body();
                        Log.d("TAG", "onResponse: " + productsResponse.getProducts().get(0).getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public void demoStream() {
        ArrayList<Product> hotDeals = (ArrayList<Product>) mListData.stream()
                .filter(product -> product.getRating() > 4.0)
                .collect(Collectors.toList());

        ArrayList<Product> orderByPrice = (ArrayList<Product>) mListData.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .collect(Collectors.toList());


    }
}