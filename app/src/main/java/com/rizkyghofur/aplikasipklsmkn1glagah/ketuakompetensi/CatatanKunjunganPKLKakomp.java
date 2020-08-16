package com.rizkyghofur.aplikasipklsmkn1glagah.ketuakompetensi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterCatatanKunjunganPKLKaKomp;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataCatatanKunjunganPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.guru.CatatanKunjunganPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class CatatanKunjunganPKLKakomp extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView list;
    SwipeRefreshLayout swipe;
    List<DataCatatanKunjunganPKL> itemList = new ArrayList<DataCatatanKunjunganPKL>();
    AdapterCatatanKunjunganPKLKaKomp adapter;
    ProgressDialog pDialog;

    private static final String TAG = CatatanKunjunganPKL.class.getSimpleName();
    public static final String TAG_USER = "id";
    private static String catatankunjunganpkl  = Server.URL + "kakomp_catatan_kunjungan_pkl.php";
    public static final String TAG_ID_CATATAN_KUNJUNGAN_PKL  = "id_catatan_kunjungan_pkl";
    public static final String TAG_NAMA_GURU  = "nama_guru";
    public static final String TAG_TANGGAL_KUNJUNGAN  = "tanggal_kunjungan";
    public static final String TAG_CATATAN_PEMBIMBING = "catatan_pembimbing";
    public static final String TAG_NAMA_DUDI = "nama_dudi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakomp_catatan_kunjungan_pkl_guru);

        Toolbar ToolBarAtas2 = findViewById(R.id.toolbar);
        setSupportActionBar(ToolBarAtas2);

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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void callVolley(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data...");
        showDialog();
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
                        Toast.makeText(CatatanKunjunganPKLKakomp.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(CatatanKunjunganPKLKakomp.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(CatatanKunjunganPKLKakomp.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(CatatanKunjunganPKLKakomp.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(CatatanKunjunganPKLKakomp.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(CatatanKunjunganPKLKakomp.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(CatatanKunjunganPKLKakomp.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CatatanKunjunganPKLKakomp.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }
}
