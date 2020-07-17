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
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import com.android.volley.Response;
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

    private static final String TAG = AbsensiPKLSiswa.class.getSimpleName();
    public static final String TAG_USER = "id";
    private static String absensipkl  = Server.URL + "guru_absensi_pkl_siswa.php";
    private static String absensipklfilter  = Server.URL + "guru_absensi_pkl_siswa_filter.php";
    public static final String TAG_ID_ABSENSI  = "id_absensi";
    public static final String TAG_NAMA_SISWA  = "nama";
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
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    private void callVolley(){
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        JsonArrayRequest jArr = new JsonArrayRequest(absensipkl+"?id_guru="+user, new Response.Listener<JSONArray>() {
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
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void callVolley1(){
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
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }

}