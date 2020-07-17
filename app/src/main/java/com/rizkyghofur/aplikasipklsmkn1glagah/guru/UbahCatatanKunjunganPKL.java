package com.rizkyghofur.aplikasipklsmkn1glagah.guru;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.google.gson.Gson;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.ResponStatus;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;

import java.util.HashMap;
import java.util.Map;

public class UbahCatatanKunjunganPKL extends AppCompatActivity {

    private EditText tanggal_kunjungan, catatan_pembimbing;
    private Button btn_simpan;
    private String ed_id_guru, ed_tanggal_kunjungan, ed_catatan_pembimbing;
    private String ed_id_catatan_kunjungan_pkl;
    Toolbar toolbar;
    TextView texttoolbar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_catatan_kunjungan_pkl_tambah_ubah);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        texttoolbar = findViewById(R.id.texttoolbar);
        texttoolbar.setText("Ubah Catatan Kunjungan PKL");

        tanggal_kunjungan = findViewById(R.id.tanggal_masuk);
        catatan_pembimbing = findViewById(R.id.catatan_pembimbing);
        btn_simpan = findViewById(R.id.simpan_tambah_ubah);

        btn_simpan.setText("Ubah");

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            ed_id_catatan_kunjungan_pkl = intent.getString("ed_id_catatan_kunjungan_pkl");
            ed_id_guru = intent.getString("ed_id_guru");
            ed_tanggal_kunjungan = intent.getString("ed_tanggal_kunjungan");
            ed_catatan_pembimbing = intent.getString("ed_catatan_pembimbing");
        }

        tanggal_kunjungan.setText(ed_tanggal_kunjungan);
        catatan_pembimbing.setText(ed_catatan_pembimbing);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggal_kunjunganx = tanggal_kunjungan.getText().toString();
                String catatan_pembimbingx = catatan_pembimbing.getText().toString();

                if (tanggal_kunjunganx.isEmpty()) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, "Tanggal Kunjungan masih kosong!", Toast.LENGTH_SHORT).show();
                } else if (catatan_pembimbingx.isEmpty()) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, "Catatan Pembimbing masih kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    updateData(ed_id_catatan_kunjungan_pkl, ed_id_guru, tanggal_kunjunganx, catatan_pembimbingx);
                }
            }
        });
    }

    private void updateData(final String id_catatan_kunjungan_pkl, final String id_guru, final String tanggal_kunjungan, final String catatan_pembimbing) {
        String url = Server.URL + "ubahcatatankunjunganpkl_guru.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                int status_kode = responStatus.getStatus_kode();
                String status_pesan = responStatus.getStatus_pesan();
                if (status_kode == 1) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                    CatatanKunjunganPKL.mInstance.MuatData();
                    finish();
                } else if (status_kode == 2) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 3) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 6) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 7) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 8) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 9) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 10) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, "Status Kesalahan Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, "Network TimeoutError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, "Nerwork NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, "Server Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, "Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UbahCatatanKunjunganPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id_catatan_kunjungan_pkl", String.valueOf(id_catatan_kunjungan_pkl));
                hashMap.put("id_guru", id_guru);
                hashMap.put("tanggal_kunjungan", tanggal_kunjungan);
                hashMap.put("catatan_pembimbing", catatan_pembimbing);

                return hashMap;
            }
        };
        AppController.getInstance().addToQueue(request, "ubah_catatan_kunjungan_pkl");
    }
}