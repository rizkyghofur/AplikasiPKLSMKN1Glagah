package com.rizkyghofur.aplikasipklsmkn1glagah.ketuakompetensi;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterKompetensiDasar;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.AdapterListMapel;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataKompetensiDasar;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataMapel;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class KompetensiDasar extends AppCompatActivity {

    private TextView txt_hasil_mapel;
    public static final String TAG_ID_MAPEL = "id";
    public static final String TAG_MAPEL = "nama_mapel";
    public static final String TAG_ID_KOMPETENSI_DASAR = "id";
    public static final String TAG_KODE = "kode";
    public static final String TAG_KOMPETENSI_DASAR = "kompetensi_dasar";
    private static final String TAG = KompetensiDasar.class.getSimpleName();
    private static String url_mapel = Server.URL + "mapel.php";
    private static String url_kompetensi_dasar = Server.URL + "kompetensi_dasar.php";
    ListView list;
    ProgressDialog pDialog;
    Spinner spinner_mapel;
    AdapterListMapel adapter;
    AdapterKompetensiDasar adapter1;
    List<DataMapel> listmapel = new ArrayList<DataMapel>();
    List<DataKompetensiDasar> listkompetensidasar = new ArrayList<DataKompetensiDasar>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakomp_kompetensi_dasar);

        list = findViewById(R.id.list);
        txt_hasil_mapel = findViewById(R.id.setspinner);
        spinner_mapel = findViewById(R.id.listItem);
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

        adapter = new AdapterListMapel(KompetensiDasar.this, listmapel);
        adapter1 = new AdapterKompetensiDasar(KompetensiDasar.this, listkompetensidasar);
        spinner_mapel.setAdapter(adapter);
        list.setAdapter(adapter1);
        callData();

    }

    private void callData1() {
        listkompetensidasar.clear();
        pDialog = new ProgressDialog(KompetensiDasar.this);
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
                                item.setKode(obj.getString(TAG_KODE));
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
                    Toast.makeText(KompetensiDasar.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(KompetensiDasar.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(KompetensiDasar.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(KompetensiDasar.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(KompetensiDasar.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(KompetensiDasar.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(KompetensiDasar.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(KompetensiDasar.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }
    
    private void callData() {
        listmapel.clear();
        pDialog = new ProgressDialog(KompetensiDasar.this);
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
                    Toast.makeText(KompetensiDasar.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(KompetensiDasar.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(KompetensiDasar.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(KompetensiDasar.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(KompetensiDasar.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(KompetensiDasar.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(KompetensiDasar.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(KompetensiDasar.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

}
