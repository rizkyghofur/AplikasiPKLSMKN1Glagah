package com.rizkyghofur.aplikasipklsmkn1glagah.siswa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rizkyghofur.aplikasipklsmkn1glagah.Login;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterListDUDI;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterPermohonanPKLSiswa;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataListDUDI;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataPermohonanPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.ResponStatus;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PermohonanPKL extends AppCompatActivity {

    private FloatingActionButton fab_tambah;
    String user, hasil, id_jurusan;
    SharedPreferences sharedpreferences;
    public static final String TAG_USER = "id";
    public static final String TAG_JURUSAN = "id_jurusan";
    private RecyclerView recyclerView;
    private AdapterPermohonanPKLSiswa adapter;
    private ArrayList<DataPermohonanPKL> arrayPermohonanPKL;
    public static PermohonanPKL mInstance;
    private static final String TAG = PermohonanPKL.class.getSimpleName();
    private static String url = Server.URL + "listdudi.php";
    private static String url1 = Server.URL + "cek_pengajuanpkl.php";
    public static final String TAG_ID_DUDI = "id_dudi";
    public static final String TAG_NAMA_DUDI = "nama_dudi";
    private static final String TAG_MESSAGE = "status_pesan";
    String tag_json_obj = "json_obj_req";
    String success;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_hasil;
    Spinner spinner_dudi;
    AdapterListDUDI adapter1;
    List<DataListDUDI> listdudi = new ArrayList<DataListDUDI>();
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa_permohonan_pkl);

        fab_tambah = findViewById(R.id.fab_tambah_permohonan);
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(TAG_USER, "");
        id_jurusan = sharedpreferences.getString(TAG_JURUSAN, "");
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mInstance = this;
        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekPengajuanPKL();
            }
        });
        MuatData();
    }

    private void DialogForm(final String hasilx, String button) {
        dialog = new AlertDialog.Builder(PermohonanPKL.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_siswa_permohonan_pkl_tambah, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.login);
        dialog.setTitle("Form Permohonan PKL");

        txt_hasil = dialogView.findViewById(R.id.txt_hasil);
        spinner_dudi = dialogView.findViewById(R.id.spinner_dudi);
        spinner_dudi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                txt_hasil.setText(listdudi.get(position).getId_dudi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        adapter1 = new AdapterListDUDI(PermohonanPKL.this, listdudi);
        spinner_dudi.setAdapter(adapter1);
        callData();

        if (!hasilx.isEmpty()) {
            txt_hasil.setText(hasilx);
        } else {
            kosong();
        }

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                hasil = txt_hasil.getText().toString();
                simpanData(hasil);
                hideDialog();
            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                hideDialog();
            }
        });
        dialog.show();
    }

    private void simpanData(String id_dudi) {
        pDialog = new ProgressDialog(PermohonanPKL.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Menyimpan data...");
        showDialog();
        String url = Server.URL + "tambah_permohonan_pkl_siswa.php?id_siswa=" + user + "&id_dudi=" + id_dudi;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                int status_kode = responStatus.getStatus_kode();
                String status_pesan = responStatus.getStatus_pesan();
                if (status_kode == 0) {
                   Toast.makeText(PermohonanPKL.this, status_pesan, Toast.LENGTH_LONG).show();
                   PermohonanPKL.mInstance.MuatData();
                   hideDialog();
                   finish();
                } else if (status_kode == 1) {
                    Toast.makeText(PermohonanPKL.this, status_pesan, Toast.LENGTH_LONG).show();
                    PermohonanPKL.mInstance.MuatData();
                    hideDialog();
                    finish();
                } else if (status_kode == 3) {
                    Toast.makeText(PermohonanPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toast.makeText(PermohonanPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toast.makeText(PermohonanPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 6) {
                    Toast.makeText(PermohonanPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 7) {
                    Toast.makeText(PermohonanPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 8) {
                    Toast.makeText(PermohonanPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 9) {
                    Toast.makeText(PermohonanPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 10) {
                    Toast.makeText(PermohonanPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PermohonanPKL.this, "Status Kesalahan Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(PermohonanPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(PermohonanPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(PermohonanPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(PermohonanPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(PermohonanPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(PermohonanPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PermohonanPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        });
        AppController.getInstance().addToQueue(request, "tambah_permohonan_pkl");
    }

    private void kosong() {
        txt_hasil.setText(null);
    }

    public void MuatData() {
        pDialog = new ProgressDialog(PermohonanPKL.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data...");
        showDialog();
        String url = Server.URL + "permohonan_pkl_siswa.php";
        StringRequest request = new StringRequest(Request.Method.GET, url + "?id_siswa=" + user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Type typePermohonanPKL = new TypeToken<ArrayList<DataPermohonanPKL>>() {
                    }.getType();
                    arrayPermohonanPKL = new Gson().fromJson(response, typePermohonanPKL);
                    adapter = new AdapterPermohonanPKLSiswa(PermohonanPKL.this, arrayPermohonanPKL);
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(PermohonanPKL.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(PermohonanPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(PermohonanPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(PermohonanPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(PermohonanPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(PermohonanPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(PermohonanPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PermohonanPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        });
        AppController.getInstance().addToQueue(request, "data_permohonan_pkl");
    }

    private void callData() {
        listdudi.clear();
        pDialog = new ProgressDialog(PermohonanPKL.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data...");
        showDialog();

        JsonArrayRequest jArr = new JsonArrayRequest(url + "?id_jurusan=" + id_jurusan,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                DataListDUDI item = new DataListDUDI();

                                item.setId_dudi(obj.getString(TAG_ID_DUDI));
                                item.setNama_dudi(obj.getString(TAG_NAMA_DUDI));

                                listdudi.add(item);
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
                    Toast.makeText(PermohonanPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(PermohonanPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(PermohonanPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(PermohonanPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(PermohonanPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(PermohonanPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PermohonanPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(PermohonanPKL.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void cekPengajuanPKL() {
        StringRequest strReq = new StringRequest(Request.Method.GET, url1 + "?id_siswa=" + user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Respon: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getString("status_kode");

                    if (success.equals("1")) {
                        Log.e("Permohonan PKL", jObj.toString());
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    } else {
                        DialogForm("", "SIMPAN");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Maaf, Jaringan Bermasalah", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(PermohonanPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(PermohonanPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(PermohonanPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(PermohonanPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(PermohonanPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(PermohonanPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PermohonanPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "PermohonanPKL Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            finish();
            return true;
        }
        return super.onTouchEvent(event);
    }
}
