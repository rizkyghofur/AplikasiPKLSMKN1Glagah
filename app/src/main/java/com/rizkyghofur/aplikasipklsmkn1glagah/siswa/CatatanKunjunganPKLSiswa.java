package com.rizkyghofur.aplikasipklsmkn1glagah.siswa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterCatatanKunjunganPKLSiswa;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataCatatanKunjunganPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.guru.CatatanKunjunganPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CatatanKunjunganPKLSiswa extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataCatatanKunjunganPKL> itemList = new ArrayList<DataCatatanKunjunganPKL>();
    AdapterCatatanKunjunganPKLSiswa adapter;
    String user;
    SharedPreferences sharedpreferences;

    private static final String TAG = CatatanKunjunganPKL.class.getSimpleName();
    public static final String TAG_USER = "id";
    private static String catatankunjunganpkl  = Server.URL + "catatankunjunganpkl.php";
    public static final String TAG_ID_CATATAN_KUNJUNGAN_PKL  = "id_catatan_kunjungan_pkl";
    public static final String TAG_NAMA_GURU  = "nama_guru";
    public static final String TAG_TANGGAL_KUNJUNGAN  = "tanggal_kunjungan";
    public static final String TAG_CATATAN_PEMBIMBING = "catatan_pembimbing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa_catatan_kunjungan_pkl);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(TAG_USER, "");

        swipe   = findViewById(R.id.swipe_refresh_layout);
        list    = findViewById(R.id.list);

        adapter = new AdapterCatatanKunjunganPKLSiswa(CatatanKunjunganPKLSiswa.this, itemList);
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
    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }

    private void callVolley(){
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        JsonArrayRequest jArr = new JsonArrayRequest(catatankunjunganpkl+"?id_siswa="+user, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataCatatanKunjunganPKL item = new DataCatatanKunjunganPKL();

                        item.setId_catatan_kunjungan_pkl(obj.getString(TAG_ID_CATATAN_KUNJUNGAN_PKL));
                        item.setId_guru(obj.getString(TAG_NAMA_GURU));
                        item.setTanggal_kunjungan(obj.getString(TAG_TANGGAL_KUNJUNGAN));
                        item.setCatatan_pembimbing(obj.getString(TAG_CATATAN_PEMBIMBING));

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
