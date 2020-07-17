package com.rizkyghofur.aplikasipklsmkn1glagah.siswa;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
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

    Toolbar toolbar;
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataDUDI> itemList = new ArrayList<DataDUDI>();
    AdapterDataDUDI adapter;
    EditText nama_dudi;
    Button cari;

    private static final String TAG = InfoDUDI.class.getSimpleName();

    private static String infodudi  = Server.URL + "infodudi.php";
    private static String infodudi_search  = Server.URL + "infodudi_search.php";
    public static final String TAG_ID_DUDI       = "id";
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
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipe   = findViewById(R.id.swipe_refresh_layout);
        list    = findViewById(R.id.list);
        nama_dudi = findViewById(R.id.input_nama_dudi);
        cari = findViewById(R.id.btn_cari);

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

    private void callVolley(){
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        JsonArrayRequest jArr = new JsonArrayRequest(infodudi, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

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

        JsonArrayRequest jArr = new JsonArrayRequest(infodudi_search+"?nama_dudi="+nama_dudi.getText().toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

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
