package com.rizkyghofur.aplikasipklsmkn1glagah.siswa;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterAbsensiPKLSiswa;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterKelompokSiswa;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataKelompokSiswa;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataAbsensiPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.ResponStatus;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AbsensiPKL extends AppCompatActivity {

    private FloatingActionButton fab_tambah;
    String user, hasil, hasil1, tanggal, id_dudi;
    SharedPreferences sharedpreferences;
    public static final String TAG_USER = "id";
    public final static String TAG_ID_DUDI = "id_dudi";
    private RecyclerView recyclerView;
    private AdapterAbsensiPKLSiswa adapter;
    private ArrayList<DataAbsensiPKL> arrayAbsensiPKL;
    public static AbsensiPKL mInstance;
    private static final String TAG = AbsensiPKL.class.getSimpleName();
    private static String url = Server.URL + "listkelompoksiswa.php";
    public static final String TAG_ID_SISWA = "id_siswa";
    public static final String TAG_NAMA_SISWA = "nama_siswa";
    private static final String TAG_MESSAGE = "status_pesan";
    private static String url1 = Server.URL + "cek_absensi_pkl_siswa.php";
    private static String url2 = Server.URL + "cek_absensi_pkl_siswa_keanggotaan.php";
    String tag_json_obj = "json_obj_req";
    String success;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_hasil, txt_tanggal, input_tanggal;
    Spinner spinner_siswa;
    AdapterKelompokSiswa adapter1;
    List<DataKelompokSiswa> listsiswa = new ArrayList<DataKelompokSiswa>();
    ProgressDialog pDialog;
    Button cari;
    public DatePickerDialog datePickerDialog;
    public SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa_absensi_pkl);

        fab_tambah = findViewById(R.id.fab_tambah_absensi);
        input_tanggal = findViewById(R.id.input_tanggal);
        cari = findViewById(R.id.btn_cari);
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(TAG_USER, "");
        id_dudi = sharedpreferences.getString(TAG_ID_DUDI, "");
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mInstance = this;
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekKeanggotaan();
            }
        });
        MuatData();

    }

    private void cekKeanggotaan() {
        StringRequest strReq = new StringRequest(Request.Method.GET, url2 + "?id_siswa=" + user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Respon: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getString("status_kode");
                    if (success.equals("1")) {
                        DialogForm("", "","SIMPAN");
                    } else{
                        Log.e("Absensi PKL", jObj.toString());
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(AbsensiPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(AbsensiPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(AbsensiPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(AbsensiPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(AbsensiPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(AbsensiPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AbsensiPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "AbsensiPKL Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void DialogForm(final String tanggalx, String siswax, String button) {
        dialog = new AlertDialog.Builder(AbsensiPKL.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_siswa_absensi_pkl_tambah, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.login);
        dialog.setTitle("Form Absensi PKL");

        txt_tanggal = dialogView.findViewById(R.id.tanggal_absensi);
        txt_hasil = dialogView.findViewById(R.id.txt_hasil);
        spinner_siswa = dialogView.findViewById(R.id.spinner_list_siswa);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        String date = dateFormatter.format(Calendar.getInstance().getTime());
        txt_tanggal.setText(date);

        final Spinner spinner_keterangan = dialogView.findViewById(R.id.spinner_keterangan);

        spinner_keterangan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                spinner_keterangan.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spinner_siswa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                txt_hasil.setText(listsiswa.get(position).getId_siswa());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        adapter1 = new AdapterKelompokSiswa(AbsensiPKL.this, listsiswa);
        spinner_siswa.setAdapter(adapter1);
        callData();

        if (!siswax.isEmpty()) {
            txt_hasil.setText(siswax);
        }
        else if (!tanggalx.isEmpty()) {
            txt_tanggal.setText(tanggalx);
        } else {
            kosong();
        }

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                tanggal = txt_tanggal.getText().toString();
                hasil = txt_hasil.getText().toString();
                hasil1 = spinner_keterangan.getSelectedItem().toString();
                cekAbsensiPKL();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                kosong();
            }
        });
        dialog.show();
    }

    private void simpanData(String id_siswa, String tanggal, String keterangan) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Sedang menyimpan data ...");
        showDialog();
        String url = Server.URL + "tambah_absensi_pkl_siswa.php?id_siswa=" + id_siswa + "&tanggal_absensi=" + tanggal + "&keterangan=" + keterangan;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                int status_kode = responStatus.getStatus_kode();
                String status_pesan = responStatus.getStatus_pesan();
                if (status_kode == 1) {
                    Toast.makeText(AbsensiPKL.this, status_pesan, Toast.LENGTH_LONG).show();
                    AbsensiPKL.mInstance.MuatData();
                } else if (status_kode == 2) {
                    Toast.makeText(AbsensiPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 3) {
                    Toast.makeText(AbsensiPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toast.makeText(AbsensiPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toast.makeText(AbsensiPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 6) {
                    Toast.makeText(AbsensiPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 7) {
                    Toast.makeText(AbsensiPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 8) {
                    Toast.makeText(AbsensiPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 9) {
                    Toast.makeText(AbsensiPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 10) {
                    Toast.makeText(AbsensiPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AbsensiPKL.this, "Status Kesalahan Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(AbsensiPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(AbsensiPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(AbsensiPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(AbsensiPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(AbsensiPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(AbsensiPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AbsensiPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        });
        AppController.getInstance().addToQueue(request, "tambah_jurnal_pkl");
    }

    private void kosong() {
        txt_hasil.setText(null);
    }

    public void MuatData() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data ...");
        showDialog();
        String url = Server.URL + "absensi_pkl_siswa.php";
        StringRequest request = new StringRequest(Request.Method.GET, url + "?id_siswa=" + user + "&id_dudi=" + id_dudi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Type typeAbsensiPKL = new TypeToken<ArrayList<DataAbsensiPKL>>() {
                    }.getType();
                    arrayAbsensiPKL = new Gson().fromJson(response, typeAbsensiPKL);
                    adapter = new AdapterAbsensiPKLSiswa(AbsensiPKL.this, arrayAbsensiPKL);
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(AbsensiPKL.this, "Data Kosong!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(AbsensiPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(AbsensiPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(AbsensiPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(AbsensiPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(AbsensiPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(AbsensiPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AbsensiPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        });
        AppController.getInstance().addToQueue(request, "data_absensi_pkl");
    }

    private void callData() {
        listsiswa.clear();
        pDialog = new ProgressDialog(AbsensiPKL.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat data...");
        showDialog();

        JsonArrayRequest jArr = new JsonArrayRequest(url+"?id_siswa=" + user + "&id_dudi=" + id_dudi,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                DataKelompokSiswa item = new DataKelompokSiswa();

                                item.setId_siswa(obj.getString(TAG_ID_SISWA));
                                item.setNama(obj.getString(TAG_NAMA_SISWA));

                                listsiswa.add(item);
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
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(AbsensiPKL.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

    private void cekAbsensiPKL() {
        String date = dateFormatter.format(Calendar.getInstance().getTime());
        StringRequest strReq = new StringRequest(Request.Method.GET, url1 + "?id_siswa=" + user + "&tanggal_absensi=" + date, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Respon: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getString("status_kode");

                    if (success.equals("1")) {
                        Log.e("Absensi PKL", jObj.toString());
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    } else{
                        simpanData(hasil, tanggal, hasil1);
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
                    Toast.makeText(AbsensiPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(AbsensiPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(AbsensiPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(AbsensiPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(AbsensiPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(AbsensiPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AbsensiPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "AbsensiPKL Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
    
}
