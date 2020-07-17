package com.rizkyghofur.aplikasipklsmkn1glagah.guru;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.rizkyghofur.aplikasipklsmkn1glagah.Login;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.ResponStatus;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
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
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TambahCatatanKunjunganPKL extends AppCompatActivity {

    Toolbar toolbar;
    private EditText tanggal_kunjungan, catatan_pembimbing;
    private Button btn_simpan, btn_tanggal_kunjungan;
    String user;
    SharedPreferences sharedpreferences;
    public static final String TAG_USER = "id";
    public DatePickerDialog datePickerDialog;
    public SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_catatan_kunjungan_pkl_tambah_ubah);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(TAG_USER, "");
        tanggal_kunjungan = findViewById(R.id.tanggal_masuk);
        catatan_pembimbing = findViewById(R.id.catatan_pembimbing);
        btn_simpan = findViewById(R.id.simpan_tambah_ubah);
        btn_tanggal_kunjungan = findViewById(R.id.btn_tanggal_masuk);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        String date = dateFormatter.format(Calendar.getInstance().getTime());
        tanggal_kunjungan.setText(date);

        btn_tanggal_kunjungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TanggalMasuk();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggal_kunjunganx = tanggal_kunjungan.getText().toString();
                String catatan_pembimbingx = catatan_pembimbing.getText().toString();

                if (tanggal_kunjunganx.isEmpty()) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, "Tanggal Kunjungan masih kosong!", Toast.LENGTH_SHORT).show();
                } else if (catatan_pembimbingx.isEmpty()) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, "Catatan pembimbing masih kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    simpanData(tanggal_kunjunganx, catatan_pembimbingx);
                }
            }
        });
    }

    public void TanggalMasuk(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggal_kunjungan.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void simpanData(String tanggal_kunjungan, String catatan_pembimbing) {
        String url = Server.URL + "tambahcatatankunjunganpkl_guru.php?id_guru=" + user + "&tanggal_kunjungan=" + tanggal_kunjungan + "&catatan_pembimbing=" + catatan_pembimbing;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                int status_kode = responStatus.getStatus_kode();
                String status_pesan = responStatus.getStatus_pesan();
                if (status_kode == 1) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                    CatatanKunjunganPKL.mInstance.MuatData();
                    finish();
                } else if (status_kode == 2) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 3) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 6) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 7) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 8) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 9) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 10) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, "Status Kesalahan Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahCatatanKunjunganPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppController.getInstance().addToQueue(request, "tambah_catatan_kunjungan_pkl");
    }
}