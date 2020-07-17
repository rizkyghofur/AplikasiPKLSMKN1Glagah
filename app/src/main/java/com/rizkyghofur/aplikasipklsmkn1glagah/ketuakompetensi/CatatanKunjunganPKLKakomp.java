package com.rizkyghofur.aplikasipklsmkn1glagah.ketuakompetensi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterCatatanKunjunganPKLKaKomp;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataCatatanKunjunganPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.guru.CatatanKunjunganPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import com.rizkyghofur.aplikasipklsmkn1glagah.siswa.PermohonanPKL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CatatanKunjunganPKLKakomp extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataCatatanKunjunganPKL> itemList = new ArrayList<DataCatatanKunjunganPKL>();
    AdapterCatatanKunjunganPKLKaKomp adapter;

    private static final String TAG = CatatanKunjunganPKL.class.getSimpleName();
    public static final String TAG_USER = "id";
    private static String catatankunjunganpkl  = Server.URL + "catatankunjunganpkl_kakomp.php";
    public static final String TAG_ID_CATATAN_KUNJUNGAN_PKL  = "id_catatan_kunjungan_pkl";
    public static final String TAG_NAMA_GURU  = "nama_guru";
    public static final String TAG_TANGGAL_KUNJUNGAN  = "tanggal_kunjungan";
    public static final String TAG_CATATAN_PEMBIMBING = "catatan_pembimbing";
    public static final String TAG_NAMA_DUDI = "nama_dudi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakomp_catatan_kunjungan_pkl_guru);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipe   = findViewById(R.id.swipe_refresh_layout);
        list    = findViewById(R.id.list);

        adapter = new AdapterCatatanKunjunganPKLKaKomp(CatatanKunjunganPKLKakomp.this, itemList);
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

        JsonArrayRequest jArr = new JsonArrayRequest(catatankunjunganpkl, new Response.Listener<JSONArray>() {
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
