package com.rizkyghofur.aplikasipklsmkn1glagah;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rizkyghofur.aplikasipklsmkn1glagah.guru.MenuGuruPembimbing;
import com.rizkyghofur.aplikasipklsmkn1glagah.ketuakompetensi.MenuKaKomp;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import com.rizkyghofur.aplikasipklsmkn1glagah.siswa.MenuSiswa;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_login;
    EditText txt_username, txt_password;
    String success;
    ConnectivityManager conMgr;
    private String url = Server.URL + "login.php";
    private static final String TAG = Login.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public final static String TAG_USERNAME = "username";
    public final static String TAG_ID_DUDI = "id_dudi";
    public final static String TAG_USER = "nama";
    public static final String TAG_USERTYPE = "role";
    public final static String TAG_ID = "id";
    String tag_json_obj = "json_obj_req";
    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, username, nama, role, id_dudi;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        btn_login = findViewById(R.id.login);
        txt_username = findViewById(R.id.inputusernamesiswa);
        txt_password = findViewById(R.id.inputpasswordsiswa);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        username = sharedpreferences.getString(TAG_USERNAME, null);
        nama = sharedpreferences.getString(TAG_USER, null);
        role = sharedpreferences.getString(TAG_USERTYPE, null);
        id_dudi = sharedpreferences.getString(TAG_USERTYPE, null);

        if (session&&role.equals("siswa")) {
                Intent intent = new Intent(Login.this, MenuSiswa.class);
                intent.putExtra(TAG_ID, id);
                intent.putExtra(TAG_USERNAME, username);
                intent.putExtra(TAG_USER, nama);
                intent.putExtra(TAG_USERTYPE, role);
                intent.putExtra(TAG_ID_DUDI, id_dudi);
                finish();
                startActivity(intent);
            }
        else if (session&&role.equals("guru")) {
                Intent intent = new Intent(Login.this, MenuGuruPembimbing.class);
                intent.putExtra(TAG_ID, id);
                intent.putExtra(TAG_USERNAME, username);
                intent.putExtra(TAG_USER, nama);
                intent.putExtra(TAG_USERTYPE, role);
                finish();
                startActivity(intent);
            }
        else if (session&&role.equals("koordinator_jurusan")) {
                Intent intent = new Intent(Login.this, MenuKaKomp.class);
                intent.putExtra(TAG_ID, id);
                intent.putExtra(TAG_USERNAME, username);
                intent.putExtra(TAG_USER, nama);
                intent.putExtra(TAG_USERTYPE, role);
                finish();
                startActivity(intent);
            }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();

                if (username.trim().length() > 0 && password.trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkLogin(username, password);
                    } else {
                        Toast.makeText(getApplicationContext() ,"Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext() ,"Kolom nama pengguna atau kata sandi tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkLogin(final String username, final String password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Sedang melakukan login ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Respon: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getString("success");

                    if (success.equals("1")) {
                        String username = jObj.getString(TAG_USERNAME);
                        String id = jObj.getString(TAG_ID);
                        String role = jObj.getString(TAG_USERTYPE);
                        String nama = jObj.getString(TAG_USER);
                        String id_dudi = jObj.getString(TAG_ID_DUDI);
                        Log.e("Login Berhasil", jObj.toString());
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_ID, id);
                        editor.putString(TAG_USERNAME, username);
                        editor.putString(TAG_USER, nama);
                        editor.putString(TAG_USERTYPE, role);
                        editor.putString(TAG_ID_DUDI, id_dudi);
                        editor.commit();

                        if (role.equals("siswa")) {
                            Intent intent = new Intent(Login.this, MenuSiswa.class);
                            intent.putExtra(TAG_ID, id);
                            intent.putExtra(TAG_USERNAME, username);
                            intent.putExtra(TAG_USER, nama);
                            intent.putExtra(TAG_USERTYPE, role);
                            intent.putExtra(TAG_ID_DUDI, id_dudi);
                            finish();
                            startActivity(intent);
                        }
                       else if (role.equals("guru")) {
                            Intent intent = new Intent(Login.this, MenuGuruPembimbing.class);
                            intent.putExtra(TAG_ID, id);
                            intent.putExtra(TAG_USERNAME, username);
                            intent.putExtra(TAG_USER, nama);
                            intent.putExtra(TAG_USERTYPE, role);
                            finish();
                            startActivity(intent);
                        }
                        else if (role.equals("koordinator_jurusan")) {
                            Intent intent = new Intent(Login.this, MenuKaKomp.class);
                            intent.putExtra(TAG_ID, id);
                            intent.putExtra(TAG_USERNAME, username);
                            intent.putExtra(TAG_USER, nama);
                            intent.putExtra(TAG_USERTYPE, role);
                            finish();
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Maaf, Jaringan Bermasalah", Toast.LENGTH_LONG).show();
                }
            }
            }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
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
}