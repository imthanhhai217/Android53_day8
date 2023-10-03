package com.vndevpro.android53_day8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://dummyjson.com/";
    private Retrofit mRetrofit;
    private DummyServices dummyServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dummyServices = mRetrofit.create(DummyServices.class);

        dummyServices.getProducts().enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                if (response.isSuccessful()){
                    if (response.code() == 200){
                        ProductsResponse productsResponse = response.body();
                        Log.d("TAG", "onResponse: "+productsResponse.getProducts().get(0).getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
            }
        });

        dummyServices.getProductById("2").enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()){
                    if (response.code() == 200){
                        Product product = response.body();

                        Log.d("TAG", "onResponse: getProductById : "+product.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

        DummyServices dummyServices2 = RetrofitClient.create(DummyServices.class);

        dummyServices2.getProducts().enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {

            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {

            }
        });
        CartServices cartServices = RetrofitClient.create(CartServices.class);


        dummyServices.addProduct(new AddProductRequest("Cái cốc")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse: AddProductRequest "+response.body().get("id").getAsString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
            }
        });
        dummyServices.addProduct(new AddProductRequest("Cái cốc 2")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse: AddProductRequest "+response.body().get("id").getAsString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
            }
        });
    }
}