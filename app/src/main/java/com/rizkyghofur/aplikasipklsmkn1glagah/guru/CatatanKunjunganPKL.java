package com.rizkyghofur.aplikasipklsmkn1glagah.guru;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rizkyghofur.aplikasipklsmkn1glagah.Login;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterCatatanKunjunganPKLGuru;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataCatatanKunjunganPKLGuru;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CatatanKunjunganPKL extends AppCompatActivity {

    private FloatingActionButton fab_tambah;
    String user;
    SharedPreferences sharedpreferences;
    public static final String TAG_USER = "id";
    private RecyclerView recyclerView;
    private AdapterCatatanKunjunganPKLGuru adapter;
    private ArrayList<DataCatatanKunjunganPKLGuru> arrayCatatanKunjungan;
    public static CatatanKunjunganPKL mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_catatan_kunjungan_pkl);

        fab_tambah = findViewById(R.id.fab_tambah_catatan);
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(TAG_USER, "");
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mInstance = this;
        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CatatanKunjunganPKL.this, TambahCatatanKunjunganPKL.class));
            }
        });
        MuatData();
    }

    public void MuatData() {
        String url = Server.URL + "catatankunjunganpkl_guru.php";
        StringRequest request = new StringRequest(Request.Method.GET, url+"?id_guru="+user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Type typeCatatanKunjungan = new TypeToken<ArrayList<DataCatatanKunjunganPKLGuru>>() {
                    }.getType();
                    arrayCatatanKunjungan = new Gson().fromJson(response, typeCatatanKunjungan);
                    adapter = new AdapterCatatanKunjunganPKLGuru(CatatanKunjunganPKL.this, arrayCatatanKunjungan);
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(CatatanKunjunganPKL.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(CatatanKunjunganPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(CatatanKunjunganPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(CatatanKunjunganPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(CatatanKunjunganPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(CatatanKunjunganPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(CatatanKunjunganPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CatatanKunjunganPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppController.getInstance().addToQueue(request, "data_catatan_kunjungan_pkl");
    }
}
