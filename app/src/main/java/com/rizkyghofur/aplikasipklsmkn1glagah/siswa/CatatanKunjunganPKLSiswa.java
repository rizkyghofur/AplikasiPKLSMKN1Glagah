package com.rizkyghofur.aplikasipklsmkn1glagah.siswa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
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
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterCatatanKunjunganPKLSiswa;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataCatatanKunjunganPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.guru.CatatanKunjunganPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class CatatanKunjunganPKLSiswa extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataCatatanKunjunganPKL> itemList = new ArrayList<DataCatatanKunjunganPKL>();
    AdapterCatatanKunjunganPKLSiswa adapter;
    String user;
    SharedPreferences sharedpreferences;
    ProgressDialog pDialog;
    TextView nama_guru;

    private static final String TAG = CatatanKunjunganPKL.class.getSimpleName();
    public static final String TAG_ID_USER = "id";
    private static String catatankunjunganpkl  = Server.URL + "siswa_catatan_kunjungan_pkl.php";
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
        user = sharedpreferences.getString(TAG_ID_USER, "");

        swipe   = findViewById(R.id.swipe_refresh_layout);
        list    = findViewById(R.id.list);
        nama_guru = findViewById(R.id.nama_guru_pembimbing);

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
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data...");
        showDialog();
        JsonArrayRequest jArr = new JsonArrayRequest(catatankunjunganpkl+"?id_siswa="+user, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataCatatanKunjunganPKL item = new DataCatatanKunjunganPKL();

                        nama_guru.setText("Guru Pembimbing : " + obj.getString(TAG_NAMA_GURU));

                        item.setId_catatan_kunjungan_pkl(obj.getString(TAG_ID_CATATAN_KUNJUNGAN_PKL));
                        item.setId_guru(obj.getString(TAG_NAMA_GURU));
                        item.setTanggal_kunjungan(obj.getString(TAG_TANGGAL_KUNJUNGAN));
                        item.setCatatan_pembimbing(obj.getString(TAG_CATATAN_PEMBIMBING));

                        itemList.add(item);
                    } catch (JSONException e) {
                        Toast.makeText(CatatanKunjunganPKLSiswa.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(CatatanKunjunganPKLSiswa.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(CatatanKunjunganPKLSiswa.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(CatatanKunjunganPKLSiswa.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(CatatanKunjunganPKLSiswa.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(CatatanKunjunganPKLSiswa.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(CatatanKunjunganPKLSiswa.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CatatanKunjunganPKLSiswa.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
