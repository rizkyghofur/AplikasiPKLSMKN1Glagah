package com.rizkyghofur.aplikasipklsmkn1glagah.siswa;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterListKompetensiDasar;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterListMapel;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataKompetensiDasar;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataMapel;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.ResponStatus;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UbahProgramPKL extends AppCompatActivity {

    public static final String TAG_ID_MAPEL = "id";
    public static final String TAG_MAPEL = "nama_mapel";
    public static final String TAG_ID_KOMPETENSI_DASAR = "id";
    public static final String TAG_KOMPETENSI_DASAR = "kompetensi_dasar";
    private static final String TAG = TambahJurnalPKL.class.getSimpleName();
    private static String url_mapel = Server.URL + "mapel.php";
    private static String url_kompetensi_dasar = Server.URL + "kompetensi_dasar.php";
    private EditText tanggal, txt_hasil_mapel, txt_hasil_kompetensi_dasar, topik_pekerjaan;
    private Button btn_simpan;
    private String ed_id_program_pkl, ed_id_siswa, ed_tanggal, ed_kompetensi_dasar, ed_topik_pekerjaan;
    Toolbar toolbar;
    TextView texttoolbar;
    ProgressDialog pDialog;
    Spinner spinner_mapel, spinner_kompetensi_dasar;
    AdapterListMapel adapter;
    AdapterListKompetensiDasar adapter1;
    List<DataMapel> listmapel = new ArrayList<DataMapel>();
    List<DataKompetensiDasar> listkompetensidasar = new ArrayList<DataKompetensiDasar>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa_program_pkl_tambah_ubah);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        texttoolbar = findViewById(R.id.texttoolbar);
        texttoolbar.setText("Ubah Data Program PKL");

        tanggal = findViewById(R.id.tanggal);
        spinner_mapel = findViewById(R.id.spinner_mapel);
        spinner_kompetensi_dasar = findViewById(R.id.spinner_kompetensi_dasar);
        txt_hasil_mapel = findViewById(R.id.txt_hasil_mapel);
        txt_hasil_kompetensi_dasar = findViewById(R.id.txt_hasil_kompetensi_dasar);
        topik_pekerjaan = findViewById(R.id.topik_pekerjaan);
        btn_simpan = findViewById(R.id.simpan_tambah_ubah);

        btn_simpan.setText("Ubah");

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            ed_id_program_pkl = intent.getString("ed_id_program_pkl");
            ed_id_siswa = intent.getString("ed_id_siswa");
            ed_tanggal = intent.getString("ed_tanggal");
            ed_kompetensi_dasar = intent.getString("ed_kompetensi_dasar");
            ed_topik_pekerjaan = intent.getString("ed_topik_pekerjaan");
        }

        tanggal.setText(ed_tanggal);
        txt_hasil_kompetensi_dasar.setText(ed_kompetensi_dasar);
        topik_pekerjaan.setText(ed_topik_pekerjaan);

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

        adapter = new AdapterListMapel(UbahProgramPKL.this, listmapel);
        adapter1 = new AdapterListKompetensiDasar(UbahProgramPKL.this, listkompetensidasar);
        spinner_mapel.setAdapter(adapter);
        spinner_kompetensi_dasar.setAdapter(adapter1);
        callData();

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggalx = tanggal.getText().toString();
                String kompetensi_dasarx = txt_hasil_kompetensi_dasar.getText().toString();
                String topik_pekerjaanx = topik_pekerjaan.getText().toString();


                if (tanggalx.isEmpty()) {
                    Toast.makeText(UbahProgramPKL.this, "Tanggal Program PKL masih kosong!", Toast.LENGTH_SHORT).show();
                } else if (kompetensi_dasarx.isEmpty()) {
                    Toast.makeText(UbahProgramPKL.this, "Rujukan Kompetensi Dasar masih kosong!", Toast.LENGTH_SHORT).show();
                } else if (topik_pekerjaanx.isEmpty()) {
                    Toast.makeText(UbahProgramPKL.this, "Topik Pekerjaan Dasar masih kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    updateData(ed_id_program_pkl, ed_id_siswa, tanggalx, kompetensi_dasarx, topik_pekerjaanx);
                }
            }
        });
    }

    private void callData() {
        listmapel.clear();
        pDialog = new ProgressDialog(UbahProgramPKL.this);
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
                    Toast.makeText(UbahProgramPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(UbahProgramPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(UbahProgramPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(UbahProgramPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(UbahProgramPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(UbahProgramPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UbahProgramPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(UbahProgramPKL.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void callData1() {
        listkompetensidasar.clear();
        pDialog = new ProgressDialog(UbahProgramPKL.this);
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
                    Toast.makeText(UbahProgramPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(UbahProgramPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(UbahProgramPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(UbahProgramPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(UbahProgramPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(UbahProgramPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UbahProgramPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(UbahProgramPKL.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

    private void updateData(final String id_program_pkl, final String id_siswa, final String tanggal, final String id_kompetensi_dasar, final String topik_pekerjaan) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Menyimpan data...");
        showDialog();
        String url = Server.URL + "ubah_program_pkl_siswa.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                int status_kode = responStatus.getStatus_kode();
                String status_pesan = responStatus.getStatus_pesan();
                if (status_kode == 1) {
                    Toast.makeText(UbahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                    ProgramPKL.mInstance.MuatData();
                    finish();
                } else if (status_kode == 2) {
                    Toast.makeText(UbahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 3) {
                    Toast.makeText(UbahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toast.makeText(UbahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toast.makeText(UbahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 6) {
                    Toast.makeText(UbahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 7) {
                    Toast.makeText(UbahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 8) {
                    Toast.makeText(UbahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 9) {
                    Toast.makeText(UbahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 10) {
                    Toast.makeText(UbahProgramPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UbahProgramPKL.this, "Status Kesalahan Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(UbahProgramPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(UbahProgramPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(UbahProgramPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(UbahProgramPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(UbahProgramPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(UbahProgramPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UbahProgramPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id_program_pkl", String.valueOf(id_program_pkl));
                hashMap.put("id_siswa", id_siswa);
                hashMap.put("tanggal", tanggal);
                hashMap.put("id_kompetensi_dasar", id_kompetensi_dasar);
                hashMap.put("topik_pekerjaan", topik_pekerjaan);

                return hashMap;
            }
        };
        AppController.getInstance().addToQueue(request, "ubah_program_pkl");
    }
}
