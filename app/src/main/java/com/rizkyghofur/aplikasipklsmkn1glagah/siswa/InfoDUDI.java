package com.rizkyghofur.aplikasipklsmkn1glagah.siswa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterDataDUDI;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataDUDI;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class InfoDUDI extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView list;
    SwipeRefreshLayout swipe;
    List<DataDUDI> itemList = new ArrayList<DataDUDI>();
    AdapterDataDUDI adapter;
    EditText nama_dudi;
    Button cari;
    String id_jurusan;
    SharedPreferences sharedpreferences;
    ProgressDialog pDialog;

    private static final String TAG = InfoDUDI.class.getSimpleName();

    private static String infodudi  = Server.URL + "infodudi.php";
    private static String infodudi_search  = Server.URL + "infodudi_search.php";
    public static final String TAG_JURUSAN = "id_jurusan";
    public static final String TAG_ID_DUDI       = "id_dudi";
    public static final String TAG_NAMA_DUDI     = "nama_dudi";
    public static final String TAG_ALAMAT_DUDI   = "alamat_dudi";
    public static final String TAG_NO_TELP_DUDI   = "no_telp_dudi";
    public static final String TAG_JENIS_USAHA  = "jenis_usaha";
    public static final String TAG_NAMA_PIMPINAN       = "nama_pimpinan";
    public static final String TAG_NO_TELP_PIMPINAN = "no_telp_pimpinan";
    public static String TAG_KUOTA = "kuota";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa_info_dudi);

        swipe   = findViewById(R.id.swipe_refresh_layout);
        list    = findViewById(R.id.list);
        nama_dudi = findViewById(R.id.input_nama_dudi);
        cari = findViewById(R.id.btn_cari);
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        id_jurusan = sharedpreferences.getString(TAG_JURUSAN, "");

        adapter = new AdapterDataDUDI(InfoDUDI.this, itemList);
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
        JsonArrayRequest jArr = new JsonArrayRequest(infodudi + "?id_jurusan=" + id_jurusan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                if(response.length()==0){
                    Toast.makeText(InfoDUDI.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataDUDI item = new DataDUDI();

                        item.setId_dudi(obj.getString(TAG_ID_DUDI));
                        item.setNama_dudi(obj.getString(TAG_NAMA_DUDI));
                        item.setAlamat_dudi(obj.getString(TAG_ALAMAT_DUDI));
                        item.setNo_telp_dudi(obj.getString(TAG_NO_TELP_DUDI));
                        item.setJenis_usaha(obj.getString(TAG_JENIS_USAHA));
                        item.setNama_pimpinan(obj.getString(TAG_NAMA_PIMPINAN));
                        item.setNo_telp_pimpinan(obj.getString(TAG_NO_TELP_PIMPINAN));
                        item.setKuota(obj.getString(TAG_KUOTA));

                        itemList.add(item);
                    } catch (JSONException e) {
                        Toast.makeText(InfoDUDI.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(InfoDUDI.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(InfoDUDI.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(InfoDUDI.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(InfoDUDI.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(InfoDUDI.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(InfoDUDI.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InfoDUDI.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void callVolley1(){
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data...");
        showDialog();
        JsonArrayRequest jArr = new JsonArrayRequest(infodudi_search+ "?id_jurusan=" + id_jurusan + "&nama_dudi="+nama_dudi.getText().toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                if(response.length()==0){
                    Toast.makeText(InfoDUDI.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataDUDI item = new DataDUDI();

                        item.setId_dudi(obj.getString(TAG_ID_DUDI));
                        item.setNama_dudi(obj.getString(TAG_NAMA_DUDI));
                        item.setAlamat_dudi(obj.getString(TAG_ALAMAT_DUDI));
                        item.setNo_telp_dudi(obj.getString(TAG_NO_TELP_DUDI));
                        item.setJenis_usaha(obj.getString(TAG_JENIS_USAHA));
                        item.setNama_pimpinan(obj.getString(TAG_NAMA_PIMPINAN));
                        item.setNo_telp_pimpinan(obj.getString(TAG_NO_TELP_PIMPINAN));
                        item.setKuota(obj.getString(TAG_KUOTA));

                        itemList.add(item);
                    } catch (JSONException e) {
                        Toast.makeText(InfoDUDI.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
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
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }
}
