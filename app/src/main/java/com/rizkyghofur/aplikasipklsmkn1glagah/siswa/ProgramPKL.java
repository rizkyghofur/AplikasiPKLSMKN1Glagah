package com.rizkyghofur.aplikasipklsmkn1glagah.siswa;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
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
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterProgramPKLSiswa;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataProgramPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ProgramPKL extends AppCompatActivity {

    private FloatingActionButton fab_tambah;
    String user;
    SharedPreferences sharedpreferences;
    public static final String TAG_ID_USER = "id";
    private RecyclerView recyclerView;
    private AdapterProgramPKLSiswa adapter;
    private ArrayList<DataProgramPKL> arrayProgramPKL;
    public static ProgramPKL mInstance;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    ProgressDialog pDialog;
    TextView tanggal;
    Button cari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa_program_pkl);

        fab_tambah = findViewById(R.id.fab_tambah_program);
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(TAG_ID_USER, "");
        recyclerView = findViewById(R.id.recyclerview);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        tanggal = findViewById(R.id.input_tanggal);
        cari = findViewById(R.id.btn_cari);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        mInstance = this;
        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProgramPKL.this, TambahProgramPKL.class));
            }
        });
        MuatData();

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TanggalMasuk();
            }
        });

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MuatData1();
            }
        });
    }

    private void TanggalMasuk() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggal.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void MuatData() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data...");
        showDialog();
        String url = Server.URL + "siswa_program_pkl.php";
        StringRequest request = new StringRequest(Request.Method.GET, url+"?id_siswa="+user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Type typeProgramPKL = new TypeToken<ArrayList<DataProgramPKL>>() {
                    }.getType();
                    arrayProgramPKL = new Gson().fromJson(response, typeProgramPKL);
                    adapter = new AdapterProgramPKLSiswa(ProgramPKL.this, arrayProgramPKL);
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(ProgramPKL.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(ProgramPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(ProgramPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(ProgramPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(ProgramPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(ProgramPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(ProgramPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProgramPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        });
        AppController.getInstance().addToQueue(request, "data_program_pkl");
    }

    public void MuatData1() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data...");
        showDialog();
        adapter.notifyDataSetChanged();
        String url = Server.URL + "siswa_program_pkl_filter.php";
        StringRequest request = new StringRequest(Request.Method.GET, url+"?id_siswa="+user+"&tanggal="+tanggal.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Type typeProgramPKL = new TypeToken<ArrayList<DataProgramPKL>>() {
                    }.getType();
                    arrayProgramPKL = new Gson().fromJson(response, typeProgramPKL);
                    adapter = new AdapterProgramPKLSiswa(ProgramPKL.this, arrayProgramPKL);
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(ProgramPKL.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(ProgramPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(ProgramPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(ProgramPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(ProgramPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(ProgramPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(ProgramPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProgramPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        });
        AppController.getInstance().addToQueue(request, "data_program_pkl");
    }

}
