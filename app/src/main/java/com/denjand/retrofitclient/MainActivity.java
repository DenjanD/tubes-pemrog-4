package com.denjand.retrofitclient;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denjand.retrofitclient.Adapter.KontakAdapter;
import com.denjand.retrofitclient.Model.GetKontak;
import com.denjand.retrofitclient.Model.Kontak;
import com.denjand.retrofitclient.Rest.ApiClient;
import com.denjand.retrofitclient.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btIns;
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btIns = (Button) findViewById(R.id.btIns);
        btIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InsertActivity.class));
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        ma=this;
        refresh();
    }

    public void refresh() {
        Call<List<GetKontak>> kontakCall = mApiInterface.getKontak();
        kontakCall.enqueue(new Callback<List<GetKontak>>() {
            @Override
            public void onResponse(Call<List<GetKontak>> call, Response<List<GetKontak>>
                    response) {
                List<GetKontak> KontakList = response.body();
                Log.d("Retrofit Getsss", "Jumlah data Kontak: " +
                        String.valueOf(KontakList.size()));
                mAdapter = new KontakAdapter(KontakList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<GetKontak>> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }
}