package com.rizkyghofur.aplikasipklsmkn1glagah.siswa;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.rizkyghofur.aplikasipklsmkn1glagah.Login;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterListKompetensiDasar;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterListMapel;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataKompetensiDasar;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataMapel;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.ResponStatus;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TambahProgramPKL extends AppCompatActivity {

    private EditText tanggal, txt_hasil_mapel, txt_hasil_kompetensi_dasar, topik_pekerjaan;
    private Button btn_simpan, btn_tanggal;
    String user;
    SharedPreferences sharedpreferences;
    public static final String TAG_USER = "id";
    public static final String TAG_ID_MAPEL = "id";
    public static final String TAG_MAPEL = "nama_mapel";
    public static final String TAG_ID_KOMPETENSI_DASAR = "id";
    public static final String TAG_KOMPETENSI_DASAR = "kompetensi_dasar";
    private static final String TAG = TambahProgramPKL.class.getSimpleName();
    private static String url_mapel = Server.URL + "mapel.php";
    private static String url_kompetensi_dasar = Server.URL + "kompetensi_dasar.php";
    public DatePickerDialog datePickerDialog;
    public SimpleDateFormat dateFormatter;
    ProgressDialog pDialog;
    Spinner spinner_mapel, spinner_kompetensi_dasar;
    AdapterListMapel adapter;
    AdapterListKompetensiDasar adapter1;
    List<DataMapel> listmapel = new ArrayList<DataMapel>();
    List<DataKompetensiDasar> listkompetensidasar = new ArrayList<DataKompetensiDasar>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa_program_pkl_tambah_ubah);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(TAG_USER, "");
        tanggal = findViewById(R.id.tanggal);
        spinner_mapel = findViewById(R.id.spinner_mapel);
        spinner_kompetensi_dasar = findViewById(R.id.spinner_kompetensi_dasar);
        txt_hasil_mapel = findViewById(R.id.txt_hasil_mapel);
        txt_hasil_kompetensi_dasar = findViewById(R.id.txt_hasil_kompetensi_dasar);
        topik_pekerjaan = findViewById(R.id.topik_pekerjaan);
        btn_simpan = findViewById(R.id.simpan_tambah_ubah);
        btn_tanggal = findViewById(R.id.btn_tanggal);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        String date = dateFormatter.format(Calendar.getInstance().getTime());
        tanggal.setText(date);

        adapter = new AdapterListMapel(TambahProgramPKL.this, listmapel);
        adapter1 = new AdapterListKompetensiDasar(TambahProgramPKL.this, listkompetensidasar);
        spinner_mapel.setAdapter(adapter);
        spinner_kompetensi_dasar.setAdapter(adapter1);
        callData();

        btn_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TanggalMasuk();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggalx = tanggal.getText().toString();
                String kompetensi_dasarx = txt_hasil_kompetensi_dasar.getText().toString();
                String topik_pekerjaanx = topik_pekerjaan.getText().toString();

                if (tanggalx.isEmpty()) {
                    Toast.makeText(TambahProgramPKL.this, "Tanggal Program PKL masih kosong!", Toast.LENGTH_SHORT).show();
                } else if (kompetensi_dasarx.isEmpty()) {
                    Toast.makeText(TambahProgramPKL.this, "Rujukan Kompetensi Dasar masih kosong!", Toast.LENGTH_SHORT).show();
                } else if (topik_pekerjaanx.isEmpty()) {
                    Toast.makeText(TambahProgramPKL.this, "Topik Pekerjaan masih kosong!", Toast.LENGTH_SHORT).show();
                }
                else {
                    simpanData(tanggalx, kompetensi_dasarx, topik_pekerjaanx);
                }
            }
        });

        spinner_mapel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_hasil_mapel.setText(listmapel.get(position).getId_mapel());
                callData1();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_kompetensi_dasar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_hasil_kompetensi_dasar.setText(listkompetensidasar.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void callData1() {
        listkompetensidasar.clear();
        pDialog = new ProgressDialog(TambahProgramPKL.this);
        pDialog.setCancelable(true);
        pDialog.setMessage("Memuat data...");
        showDialog();

        JsonArrayRequest jArr = new JsonArrayRequest(url_kompetensi_dasar + "?id_mapel=" + txt_hasil_mapel.getText().toString(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                DataKompetensiDasar item = new DataKompetensiDasar();

                                item.setId(obj.getString(TAG_ID_KOMPETENSI_DASAR));
                                item.setKompetensi_dasar(obj.getString(TAG_KOMPETENSI_DASAR));

                                listkompetensidasar.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter1.notifyDataSetChanged();
                        hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(TambahProgramPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(TambahProgramPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(TambahProgramPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(TambahProgramPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(TambahProgramPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(TambahProgramPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahProgramPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(TambahProgramPKL.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }

    public void TanggalMasuk(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggal.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void callData() {
        listmapel.clear();
        pDialog = new ProgressDialog(TambahProgramPKL.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data...");
        showDialog();

        JsonArrayRequest jArr = new JsonArrayRequest(url_mapel,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                DataMapel item = new DataMapel();

                                item.setId_mapel(obj.getString(TAG_ID_MAPEL));
                                item.setNama_mapel(obj.getString(TAG_MAPEL));

                                listmapel.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                        hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(TambahProgramPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(TambahProgramPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(TambahProgramPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(TambahProgramPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(TambahProgramPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(TambahProgramPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahProgramPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(TambahProgramPKL.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }
    
    private void simpanData(String tanggal, String kompetensi_dasar, String topik_pekerjaan) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Menyimpan data...");
        showDialog();
        String url = Server.URL + "siswa_tambah_program_pkl.php?id_siswa=" + user + "&tanggal=" + tanggal + "&id_kompetensi_dasar=" + kompetensi_dasar + "&topik_pekerjaan=" + topik_pekerjaan;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                int status_kode = responStatus.getStatus_kode();
                String status_pesan = responStatus.getStatus_pesan();
                if (status_kode == 1) {
                    Toast.makeText(TambahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                    ProgramPKL.mInstance.MuatData();
                    finish();
                } else if (status_kode == 2) {
                    Toast.makeText(TambahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 3) {
                    Toast.makeText(TambahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toast.makeText(TambahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toast.makeText(TambahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 6) {
                    Toast.makeText(TambahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 7) {
                    Toast.makeText(TambahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 8) {
                    Toast.makeText(TambahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 9) {
                    Toast.makeText(TambahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 10) {
                    Toast.makeText(TambahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahProgramPKL.this, "Status Kesalahan Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(TambahProgramPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(TambahProgramPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(TambahProgramPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(TambahProgramPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(TambahProgramPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(TambahProgramPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahProgramPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        });
        AppController.getInstance().addToQueue(request, "tambah_program_pkl");
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