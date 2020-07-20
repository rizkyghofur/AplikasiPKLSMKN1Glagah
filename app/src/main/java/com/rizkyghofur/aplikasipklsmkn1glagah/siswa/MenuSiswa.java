package com.rizkyghofur.aplikasipklsmkn1glagah.siswa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
import com.rizkyghofur.aplikasipklsmkn1glagah.Login;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuSiswa extends AppCompatActivity {

    public static final String TAG_ID_USER = "id";
    public static final String TAG_USER = "nama_siswa";
    ImageView btn_logout;
    TextView txt_username, txt_status_validasi;
    String user, id_siswa;
    public static final String session_status = "session_status";
    SharedPreferences sharedpreferences;
    String tag_json_obj = "json_obj_req";
    String success;
    private static String url1 = Server.URL + "cek_validasipermohonanpkl.php";
    private static String url2 = Server.URL + "cek_validasipermohonanpkl_menu.php";
    private static final String TAG_STATUS_VALIDASI = "status_validasi";
    private static final String TAG = MenuSiswa.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_siswa);

        txt_username = findViewById(R.id.user_id);
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        btn_logout = findViewById(R.id.logout);
        user = getIntent().getStringExtra(TAG_USER);
        id_siswa = getIntent().getStringExtra(TAG_ID_USER);
        txt_username.setText(user);
        txt_status_validasi = findViewById(R.id.status_validasi);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
            cekPengajuanPKL();
    }

    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setTitle("Logout dari aplikasi");
        alertDialogBuilder
                .setMessage("Yakin ingin keluar dari aplikasi?")
                .setIcon(R.mipmap.login)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        logout();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void logout() {
              SharedPreferences.Editor editor = sharedpreferences.edit();
              editor.putBoolean(MenuSiswa.session_status, false);
              editor.putString(TAG_USER, null);
              editor.commit();
              Intent ua = new Intent(MenuSiswa.this, Login.class);
              finish();
              startActivity(ua);
    }

        public void PermohonanPKL (View view){
            Intent intent = new Intent(MenuSiswa.this, PermohonanPKL.class);
            startActivity(intent);
        }

        public void InfoDUDI (View view){
            Intent intent = new Intent(MenuSiswa.this, InfoDUDI.class);
            startActivity(intent);
        }

        public void ProgramPKL (View view){
            StringRequest strReq = new StringRequest(Request.Method.GET, url2 + "?id_siswa=" + id_siswa, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(TAG, "Login Respon: " + response.toString());

                    try {
                        JSONObject jObj = new JSONObject(response);
                        success = jObj.getString("status_kode");

                        if (success.equals("1")) {
                            Log.e("Permohonan PKL", jObj.toString());
                            Intent intent = new Intent(MenuSiswa.this, ProgramPKL.class);
                            startActivity(intent);
                        } else {
                            txt_status_validasi.setText("Status Validasi : Belum Mengajukan");
                            Toast.makeText(getApplicationContext(), "Maaf, Anda belum diizinkan mengakses menu ini karena belum melakukan atau dalam proses pengajuan PKL", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(MenuSiswa.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NoConnectionError) {
                        Toast.makeText(MenuSiswa.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(MenuSiswa.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(MenuSiswa.this, "Tidak diizinkan terhubung dengan server", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(MenuSiswa.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(MenuSiswa.this, "Parse Error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MenuSiswa.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                    }
                    Log.e(TAG, "MenuSiswa Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
            };
            AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
        }

        public void JurnalPKL (View view){
            StringRequest strReq = new StringRequest(Request.Method.GET, url2 + "?id_siswa=" + id_siswa, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(TAG, "Login Respon: " + response.toString());

                    try {
                        JSONObject jObj = new JSONObject(response);
                        success = jObj.getString("status_kode");

                        if (success.equals("1")) {
                            Log.e("Permohonan PKL", jObj.toString());
                            Intent intent = new Intent(MenuSiswa.this, JurnalPKL.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Maaf, Anda belum diizinkan mengakses menu ini karena belum melakukan atau dalam proses pengajuan PKL", Toast.LENGTH_LONG).show();
                            txt_status_validasi.setText("Status Validasi : Belum Mengajukan");
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
                        Toast.makeText(MenuSiswa.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NoConnectionError) {
                        Toast.makeText(MenuSiswa.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(MenuSiswa.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(MenuSiswa.this, "Tidak diizinkan terhubung dengan server", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(MenuSiswa.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(MenuSiswa.this, "Parse Error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MenuSiswa.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                    }
                    Log.e(TAG, "MenuSiswa Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
            };
            AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
        }

        public void AbsensiPKL (View view){
            StringRequest strReq = new StringRequest(Request.Method.GET, url2 + "?id_siswa=" + id_siswa, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(TAG, "Login Respon: " + response.toString());

                    try {
                        JSONObject jObj = new JSONObject(response);
                        success = jObj.getString("status_kode");

                        if (success.equals("1")) {
                            Log.e("Permohonan PKL", jObj.toString());
                            Intent intent = new Intent(MenuSiswa.this, AbsensiPKL.class);
                            startActivity(intent);
                        } else {
                            txt_status_validasi.setText("Status Validasi : Belum Mengajukan");
                            Toast.makeText(getApplicationContext(), "Maaf, Anda belum diizinkan mengakses menu ini karena belum melakukan atau dalam proses pengajuan PKL", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(MenuSiswa.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NoConnectionError) {
                        Toast.makeText(MenuSiswa.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(MenuSiswa.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(MenuSiswa.this, "Tidak diizinkan terhubung dengan server", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(MenuSiswa.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(MenuSiswa.this, "Parse Error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MenuSiswa.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                    }
                    Log.e(TAG, "MenuSiswa Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
            };
            AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
        }

        public void CatatanKunjunganPKL (View view){
            StringRequest strReq = new StringRequest(Request.Method.GET, url2 + "?id_siswa=" + id_siswa, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(TAG, "Login Respon: " + response.toString());

                    try {
                        JSONObject jObj = new JSONObject(response);
                        success = jObj.getString("status_kode");

                        if (success.equals("1")) {
                            Log.e("Permohonan PKL", jObj.toString());
                            Intent intent = new Intent(MenuSiswa.this, CatatanKunjunganPKLSiswa.class);
                            startActivity(intent);
                        } else {
                            txt_status_validasi.setText("Status Validasi : Belum Mengajukan");
                            Toast.makeText(getApplicationContext(), "Maaf, Anda belum diizinkan mengakses menu ini karena belum melakukan atau dalam proses pengajuan PKL", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(MenuSiswa.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NoConnectionError) {
                        Toast.makeText(MenuSiswa.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(MenuSiswa.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(MenuSiswa.this, "Tidak diizinkan terhubung dengan server", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(MenuSiswa.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(MenuSiswa.this, "Parse Error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MenuSiswa.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                    }
                    Log.e(TAG, "MenuSiswa Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
            };
            AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
        }

    long lastPress;
    Toast backpressToast;
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastPress > 5000){
            backpressToast = Toast.makeText(getBaseContext(), "Tekan tombol kembali 2 kali untuk keluar", Toast.LENGTH_LONG);
            backpressToast.show();
            lastPress = currentTime;

        } else {
            if (backpressToast != null) backpressToast.cancel();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(intent);
            super.onBackPressed();
        }
    }

    private void cekPengajuanPKL() {
        StringRequest strReq = new StringRequest(Request.Method.GET, url1 + "?id_siswa=" + id_siswa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Respon: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getString("status_kode");

                    if (success.equals("1")) {
                        Log.e("Permohonan PKL", jObj.toString());
                        txt_status_validasi.setText("Status Validasi : " + jObj.getString(TAG_STATUS_VALIDASI));
                    } else {
                        txt_status_validasi.setText("Status Validasi : Belum Mengajukan");
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
                    Toast.makeText(MenuSiswa.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(MenuSiswa.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(MenuSiswa.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(MenuSiswa.this, "Tidak diizinkan terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(MenuSiswa.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(MenuSiswa.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuSiswa.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "MenuSiswa Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
}
