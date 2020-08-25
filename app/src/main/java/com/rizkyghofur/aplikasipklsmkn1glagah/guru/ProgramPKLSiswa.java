package com.rizkyghofur.aplikasipklsmkn1glagah.guru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterProgramPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataProgramPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import com.rizkyghofur.aplikasipklsmkn1glagah.siswa.ProgramPKL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ProgramPKLSiswa extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView list;
    SwipeRefreshLayout swipe;
    List<DataProgramPKL> itemList = new ArrayList<DataProgramPKL>();
    AdapterProgramPKL adapter;
    String user;
    SharedPreferences sharedpreferences;
    EditText tanggal_program;
    Button cari;
    ProgressDialog pDialog;

    private static final String TAG = ProgramPKL.class.getSimpleName();
    public static final String TAG_USER = "id";
    private static String programpkl_filter = Server.URL + "guru_program_pkl_siswa_filter.php";
    public static final String TAG_ID_JURNAL_PKL  = "id_program_pkl";
    public static final String TAG_NAMA_SISWA  = "nama_siswa";
    public static final String TAG_KELAS = "kelas";
    public static final String TAG_KOMPETENSI_DASAR  = "kompetensi_dasar";
    public static final String TAG_TANGGAL = "tanggal";
    public static final String TAG_TOPIK_PEKERJAAN  = "topik_pekerjaan";
    public static final String TAG_NAMA_DUDI  = "nama_dudi";
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_program_pkl);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(TAG_USER, "");
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        swipe   = findViewById(R.id.swipe_refresh_layout);
        list    = findViewById(R.id.list);
        tanggal_program = findViewById(R.id.input_tanggal_program);
        cari = findViewById(R.id.btn_cari);

        adapter = new AdapterProgramPKL(ProgramPKLSiswa.this, itemList);
        list.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

        tanggal_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TanggalMasuk();
            }
        });

        String date = dateFormatter.format(Calendar.getInstance().getTime());
        tanggal_program.setText(date);

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipe.setRefreshing(true);
                itemList.clear();
                adapter.notifyDataSetChanged();
                callVolley1();
            }
        });

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
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data...");
        showDialog();
        JsonArrayRequest jArr = new JsonArrayRequest(programpkl_filter+"?id_guru="+user+"&tanggal="+tanggal_program.getText().toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                if(response.length()==0){
                    Toast.makeText(ProgramPKLSiswa.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataProgramPKL item = new DataProgramPKL();

                        item.setId_programpkl(obj.getString(TAG_ID_JURNAL_PKL));
                        item.setId_siswa(obj.getString(TAG_NAMA_SISWA));
                        item.setKelas(obj.getString(TAG_KELAS));
                        item.setKompetensi_dasar(obj.getString(TAG_KOMPETENSI_DASAR));
                        item.setUrutan_waktu_pelaksanaan(obj.getString(TAG_TANGGAL));
                        item.setTopik_pekerjaan(obj.getString(TAG_TOPIK_PEKERJAAN));
                        item.setDudi_pasangan(obj.getString(TAG_NAMA_DUDI));

                        itemList.add(item);
                    } catch (JSONException e) {
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
                    Toast.makeText(ProgramPKLSiswa.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(ProgramPKLSiswa.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(ProgramPKLSiswa.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(ProgramPKLSiswa.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(ProgramPKLSiswa.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(ProgramPKLSiswa.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProgramPKLSiswa.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
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
        JsonArrayRequest jArr = new JsonArrayRequest(programpkl_filter+"?id_guru="+user+"&tanggal="+tanggal_program.getText().toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                if(response.length()==0){
                    Toast.makeText(ProgramPKLSiswa.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataProgramPKL item = new DataProgramPKL();

                        item.setId_programpkl(obj.getString(TAG_ID_JURNAL_PKL));
                        item.setId_siswa(obj.getString(TAG_NAMA_SISWA));
                        item.setKompetensi_dasar(obj.getString(TAG_KOMPETENSI_DASAR));
                        item.setUrutan_waktu_pelaksanaan(obj.getString(TAG_TANGGAL));
                        item.setTopik_pekerjaan(obj.getString(TAG_TOPIK_PEKERJAAN));
                        item.setDudi_pasangan(obj.getString(TAG_NAMA_DUDI));

                        itemList.add(item);
                    } catch (JSONException e) {
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
                    Toast.makeText(ProgramPKLSiswa.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(ProgramPKLSiswa.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(ProgramPKLSiswa.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(ProgramPKLSiswa.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(ProgramPKLSiswa.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(ProgramPKLSiswa.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProgramPKLSiswa.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
                hideDialog();
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
                tanggal_program.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}
