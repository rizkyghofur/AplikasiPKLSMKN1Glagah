package com.rizkyghofur.aplikasipklsmkn1glagah.guru;

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

public class MenuGuruPembimbing extends AppCompatActivity {

    public static final String TAG_ID_USER = "id";
    public static final String TAG_USER_GURU = "nama_guru";
    private static String session_status = "session_status";
    private static String url = Server.URL + "cek_menu_guru_pembimbing.php";
    private static final String TAG = MenuGuruPembimbing.class.getSimpleName();
    String success;
    TextView txt_username;
    String user, id_guru;
    String tag_json_obj = "json_obj_req";
    SharedPreferences sharedpreferences;
    ImageView btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_guru_pembimbing);

        txt_username = findViewById(R.id.user_id);
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        user = getIntent().getStringExtra(TAG_USER_GURU);
        id_guru = getIntent().getStringExtra(TAG_ID_USER);
        txt_username.setText(user);
        btn_logout = findViewById(R.id.logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

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
        editor.putBoolean(MenuGuruPembimbing.session_status, false);
        editor.putString(TAG_USER_GURU, null);
        editor.commit();
        Intent ua = new Intent(MenuGuruPembimbing.this, Login.class);
        finish();
        startActivity(ua);
    }

    public void CatatanKunjunganPKL (View view){
        StringRequest strReq = new StringRequest(Request.Method.GET, url + "?id_guru=" + id_guru, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Respon: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getString("status_kode");

                    if (success.equals("1")) {
                        Log.e("MenuGuruPembimbing", jObj.toString());
                        Intent intent = new Intent(MenuGuruPembimbing.this, CatatanKunjunganPKL.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Maaf, Anda belum diizinkan mengakses menu ini karena belum ada siswa yang melakukan atau dalam proses pengajuan PKL", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MenuGuruPembimbing.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Tidak diizinkan terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuGuruPembimbing.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "MenuGuruPembimbing Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    public void AbsensiPKLSiswa (View view){
        StringRequest strReq = new StringRequest(Request.Method.GET, url + "?id_guru=" + id_guru, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Respon: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getString("status_kode");

                    if (success.equals("1")) {
                        Log.e("MenuGuruPembimbing", jObj.toString());
                        Intent intent = new Intent(MenuGuruPembimbing.this, AbsensiPKLSiswa.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Maaf, Anda belum diizinkan mengakses menu ini karena belum ada siswa yang melakukan atau dalam proses pengajuan PKL", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MenuGuruPembimbing.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Tidak diizinkan terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuGuruPembimbing.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "MenuGuruPembimbing Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    public void JurnalPKLSiswa (View view){
        StringRequest strReq = new StringRequest(Request.Method.GET, url + "?id_guru=" + id_guru, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Respon: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getString("status_kode");

                    if (success.equals("1")) {
                        Log.e("MenuGuruPembimbing", jObj.toString());
                        Intent intent = new Intent(MenuGuruPembimbing.this, JurnalPKLSiswa.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Maaf, Anda belum diizinkan mengakses menu ini karena belum ada siswa yang melakukan atau dalam proses pengajuan PKL", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MenuGuruPembimbing.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Tidak diizinkan terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuGuruPembimbing.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "MenuGuruPembimbing Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    public void ProgramPKLSiswa (View view){
        StringRequest strReq = new StringRequest(Request.Method.GET, url + "?id_guru=" + id_guru, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Respon: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getString("status_kode");

                    if (success.equals("1")) {
                        Log.e("MenuGuruPembimbing", jObj.toString());
                        Intent intent = new Intent(MenuGuruPembimbing.this, ProgramPKLSiswa.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Maaf, Anda belum diizinkan mengakses menu ini karena belum ada siswa yang melakukan atau dalam proses pengajuan PKL", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MenuGuruPembimbing.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Tidak diizinkan terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(MenuGuruPembimbing.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuGuruPembimbing.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "MenuGuruPembimbing Error: " + error.getMessage());
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
            backpressToast = Toast.makeText(getBaseContext(), "Tekan tombol kembali lagi keluar", Toast.LENGTH_LONG);
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
}
