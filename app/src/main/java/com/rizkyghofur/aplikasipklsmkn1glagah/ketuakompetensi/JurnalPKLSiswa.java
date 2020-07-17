package com.rizkyghofur.aplikasipklsmkn1glagah.ketuakompetensi;

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
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterJurnalPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataJurnalPKL;
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

public class JurnalPKLSiswa extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataJurnalPKL> itemList = new ArrayList<DataJurnalPKL>();
    AdapterJurnalPKL adapter;
    String user;
    SharedPreferences sharedpreferences;
    EditText tanggal_jurnal;
    Button cari;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    private static final String TAG = JurnalPKLSiswa.class.getSimpleName();
    public static final String TAG_USER = "id";
    private static String jurnalpkl = Server.URL + "kakomp_jurnalpkl_siswa.php";
    private static String jurnalpklfilter = Server.URL + "kakomp_jurnalpkl_siswa_filter.php";
    public static final String TAG_ID_JURNAL_PKL  = "id_jurnal_pkl";
    public static final String TAG_NAMA_SISWA  = "nama";
    public static final String TAG_KELAS = "kelas";
    public static final String TAG_KOMPETENSI_DASAR  = "kompetensi_dasar";
    public static final String TAG_TANGGAL = "tanggal";
    public static final String TAG_TOPIK_PEKERJAAN  = "topik_pekerjaan";
    public static final String TAG_NAMA_DUDI  = "nama_dudi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakomp_jurnal_pkl_siswa);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(TAG_USER, "");
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        swipe   = findViewById(R.id.swipe_refresh_layout);
        list    = findViewById(R.id.list);
        tanggal_jurnal = findViewById(R.id.input_tanggal_jurnal);
        cari = findViewById(R.id.btn_cari);

        String date = dateFormatter.format(Calendar.getInstance().getTime());
        tanggal_jurnal.setText(date);

        adapter = new AdapterJurnalPKL(JurnalPKLSiswa.this, itemList);
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

        tanggal_jurnal.setOnClickListener(new View.OnClickListener() {
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

    private void callVolley(){
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        JsonArrayRequest jArr = new JsonArrayRequest(jurnalpklfilter + "?tanggal=" + tanggal_jurnal.getText().toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataJurnalPKL item = new DataJurnalPKL();

                        item.setId_jurnal_pkl(obj.getString(TAG_ID_JURNAL_PKL));
                        item.setId_siswa(obj.getString(TAG_NAMA_SISWA));
                        item.setKompetensi_dasar(obj.getString(TAG_KOMPETENSI_DASAR));
                        item.setTanggal(obj.getString(TAG_TANGGAL));
                        item.setKelas(obj.getString(TAG_KELAS));
                        item.setTopik_pekerjaan(obj.getString(TAG_TOPIK_PEKERJAAN));
                        item.setId_dudi(obj.getString(TAG_NAMA_DUDI));

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

        JsonArrayRequest jArr = new JsonArrayRequest(jurnalpklfilter+"?tanggal="+tanggal_jurnal.getText().toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataJurnalPKL item = new DataJurnalPKL();

                        item.setId_jurnal_pkl(obj.getString(TAG_ID_JURNAL_PKL));
                        item.setId_siswa(obj.getString(TAG_NAMA_SISWA));
                        item.setKompetensi_dasar(obj.getString(TAG_KOMPETENSI_DASAR));
                        item.setTanggal(obj.getString(TAG_TANGGAL));
                        item.setTopik_pekerjaan(obj.getString(TAG_TOPIK_PEKERJAAN));
                        item.setId_dudi(obj.getString(TAG_NAMA_DUDI));

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

    private void TanggalMasuk(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggal_jurnal.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}
