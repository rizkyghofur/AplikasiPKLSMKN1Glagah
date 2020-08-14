package com.rizkyghofur.aplikasipklsmkn1glagah.guru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.rizkyghofur.aplikasipklsmkn1glagah.Login;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterAbsensiPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataAbsensiPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;

public class AbsensiPKLSiswa extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataAbsensiPKL> itemList = new ArrayList<DataAbsensiPKL>();
    AdapterAbsensiPKL adapter;
    String user;
    SharedPreferences sharedpreferences;
    EditText tanggal_absensi;
    Button cari;
    ProgressDialog pDialog;

    private static final String TAG = AbsensiPKLSiswa.class.getSimpleName();
    public static final String TAG_USER = "id";
    private static String absensipklfilter  = Server.URL + "guru_absensi_pkl_siswa_filter.php";
    public static final String TAG_ID_ABSENSI  = "id_absensi";
    public static final String TAG_NAMA_SISWA  = "nama_siswa";
    public static final String TAG_KELAS = "kelas";
    public static final String TAG_TANGGAL_ABSENSI  = "tanggal_absensi";
    public static final String TAG_KETERANGAN = "keterangan";
    public static final String TAG_NAMA_DUDI = "nama_dudi";
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_absensi_pkl);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(TAG_USER, "");
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        swipe   = findViewById(R.id.swipe_refresh_layout);
        list    = findViewById(R.id.list);
        tanggal_absensi = findViewById(R.id.input_tanggal_absensi);
        cari = findViewById(R.id.btn_cari);

        adapter = new AdapterAbsensiPKL(AbsensiPKLSiswa.this, itemList);
        list.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           adapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );

        tanggal_absensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TanggalMasuk();
            }
        });

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipe.setRefreshing(true);
                itemList.clear();
                adapter.notifyDataSetChanged();
                callVolley1();
            }
        });

        String date = dateFormatter.format(Calendar.getInstance().getTime());
        tanggal_absensi.setText(date);

    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }

    private void TanggalMasuk(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggal_absensi.setText(dateFormatter.format(newDate.getTime()));
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

    private void callVolley(){
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data...");
        showDialog();
        JsonArrayRequest jArr = new JsonArrayRequest(absensipklfilter+"?id_guru="+user+"&tanggal_absensi="+tanggal_absensi.getText().toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataAbsensiPKL item = new DataAbsensiPKL();

                        item.setId_absensi(obj.getString(TAG_ID_ABSENSI));
                        item.setId_siswa(obj.getString(TAG_NAMA_SISWA));
                        item.setTanggal_absensi(obj.getString(TAG_TANGGAL_ABSENSI));
                        item.setKelas(obj.getString(TAG_KELAS));
                        item.setKeterangan(obj.getString(TAG_KETERANGAN));
                        item.setNama_dudi(obj.getString(TAG_NAMA_DUDI));

                        itemList.add(item);
                    } catch (JSONException e) {
                        Toast.makeText(AbsensiPKLSiswa.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
                hideDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(AbsensiPKLSiswa.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(AbsensiPKLSiswa.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(AbsensiPKLSiswa.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(AbsensiPKLSiswa.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(AbsensiPKLSiswa.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(AbsensiPKLSiswa.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AbsensiPKLSiswa.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void callVolley1(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data...");
        showDialog();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        JsonArrayRequest jArr = new JsonArrayRequest(absensipklfilter+"?id_guru="+user+"&tanggal_absensi="+tanggal_absensi.getText().toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataAbsensiPKL item = new DataAbsensiPKL();

                        item.setId_absensi(obj.getString(TAG_ID_ABSENSI));
                        item.setId_siswa(obj.getString(TAG_NAMA_SISWA));
                        item.setTanggal_absensi(obj.getString(TAG_TANGGAL_ABSENSI));
                        item.setKelas(obj.getString(TAG_KELAS));
                        item.setKeterangan(obj.getString(TAG_KETERANGAN));
                        item.setNama_dudi(obj.getString(TAG_NAMA_DUDI));

                        itemList.add(item);
                    } catch (JSONException e) {
                        Toast.makeText(AbsensiPKLSiswa.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
                hideDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(AbsensiPKLSiswa.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(AbsensiPKLSiswa.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(AbsensiPKLSiswa.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(AbsensiPKLSiswa.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(AbsensiPKLSiswa.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(AbsensiPKLSiswa.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AbsensiPKLSiswa.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }

}