package com.rizkyghofur.aplikasipklsmkn1glagah.ketuakompetensi;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterPengajuanPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataPermohonanPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class PengajuanPKLSiswa extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataPermohonanPKL> itemList = new ArrayList<DataPermohonanPKL>();
    AdapterPengajuanPKL adapter;
    Button cari;
    TextView setspinner;

    private static final String TAG = PengajuanPKLSiswa.class.getSimpleName();

    private static String permohonanpkl  = Server.URL + "pengajuanpkl_siswa.php";
    private static String permohonanpkl_filter  = Server.URL + "pengajuanpkl_siswa_filter.php";
    public static final String TAG_ID_PENGAJUANPKL = "id_pengajuanpkl";
    public static final String TAG_ID_SISWA = "id_siswa";
    public static final String TAG_ID_DUDI  = "nama_dudi";
    public static final String TAG_NAMA_SISWA = "nama";
    public static final String TAG_KELAS  = "kelas";
    public static final String TAG_TANGGAL_MASUK   = "tanggal_masuk";
    public static final String TAG_TANGGAL_KELUAR  = "tanggal_keluar";
    public static final String TAG_ID_GURU = "nama_guru";
    public static final String TAG_STATUS_VALIDASI = "status_validasi";

    private String[] Item = {"Belum Tervalidasi","Proses Pengajuan","Diterima","Ditolak"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakomp_pengajuanpkl);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Spinner ListStatusValidasi = findViewById(R.id.listItem);
        swipe   = findViewById(R.id.swipe_refresh_layout);
        list    = findViewById(R.id.list);
        cari = findViewById(R.id.btn_cari);
        setspinner = findViewById(R.id.setspinner);

        adapter = new AdapterPengajuanPKL(PengajuanPKLSiswa.this, itemList);
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

        final ArrayAdapter<String> adapterlist = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,Item);

        ListStatusValidasi.setAdapter(adapterlist);
        ListStatusValidasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                setspinner.setText(adapterlist.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView adapterView) {
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

        JsonArrayRequest jArr = new JsonArrayRequest(permohonanpkl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataPermohonanPKL item = new DataPermohonanPKL();

                        item.setId(obj.getString(TAG_ID_PENGAJUANPKL));
                        item.setId_siswa(obj.getString(TAG_ID_SISWA));
                        item.setId_dudi(obj.getString(TAG_ID_DUDI));
                        item.setNama_siswa(obj.getString(TAG_NAMA_SISWA));
                        item.setKelas(obj.getString(TAG_KELAS));
                        item.setTanggal_keluar(obj.getString(TAG_TANGGAL_KELUAR));
                        item.setTanggal_masuk(obj.getString(TAG_TANGGAL_MASUK));
                        item.setTanggal_keluar(obj.getString(TAG_TANGGAL_KELUAR));
                        item.setId_guru(obj.getString(TAG_ID_GURU));
                        item.setStatus_validasi(obj.getString(TAG_STATUS_VALIDASI));

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

        JsonArrayRequest jArr = new JsonArrayRequest(permohonanpkl_filter+"?status_validasi="+setspinner.getText().toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataPermohonanPKL item = new DataPermohonanPKL();

                        item.setId(obj.getString(TAG_ID_PENGAJUANPKL));
                        item.setId_siswa(obj.getString(TAG_ID_SISWA));
                        item.setNama_siswa(obj.getString(TAG_NAMA_SISWA));
                        item.setKelas(obj.getString(TAG_KELAS));
                        item.setId_dudi(obj.getString(TAG_ID_DUDI));
                        item.setTanggal_masuk(obj.getString(TAG_TANGGAL_MASUK));
                        item.setTanggal_keluar(obj.getString(TAG_TANGGAL_KELUAR));
                        item.setId_guru(obj.getString(TAG_ID_GURU));
                        item.setStatus_validasi(obj.getString(TAG_STATUS_VALIDASI));

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
