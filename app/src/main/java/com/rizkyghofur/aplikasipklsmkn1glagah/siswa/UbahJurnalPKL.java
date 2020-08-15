package com.rizkyghofur.aplikasipklsmkn1glagah.siswa;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UbahJurnalPKL extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG_ID_MAPEL = "id";
    public static final String TAG_MAPEL = "nama_mapel";
    public static final String TAG_ID_KOMPETENSI_DASAR = "id";
    public static final String TAG_KOMPETENSI_DASAR = "kompetensi_dasar";
    private static final String TAG = TambahJurnalPKL.class.getSimpleName();
    private static String url_mapel = Server.URL + "mapel.php";
    private static String url_kompetensi_dasar = Server.URL + "kompetensi_dasar.php";
    private EditText tanggal, txt_hasil_mapel, txt_hasil_kompetensi_dasar, topik_pekerjaan;
    private Button btn_simpan;
    private String ed_id_jurnal_pkl, ed_id_siswa, ed_tanggal, ed_kompetensi_dasar, ed_topik_pekerjaan, ed_dokumentasi;
    TextView texttoolbar;
    ProgressDialog pDialog;
    Spinner spinner_mapel, spinner_kompetensi_dasar;
    ImageView ivPhoto;
    Bitmap bitmap, decoded;
    int bitmap_size = 60;
    Intent intent;
    Uri fileUri;
    public final int REQUEST_CAMERA = 0;
    public final int SELECT_FILE = 1;
    int max_resolution_image = 1024;
    AdapterListMapel adapter;
    AdapterListKompetensiDasar adapter1;
    List<DataMapel> listmapel = new ArrayList<DataMapel>();
    List<DataKompetensiDasar> listkompetensidasar = new ArrayList<DataKompetensiDasar>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa_jurnal_pkl_tambah_ubah);

        Toolbar ToolBarAtas2 = findViewById(R.id.toolbar);
        setSupportActionBar(ToolBarAtas2);
        texttoolbar = findViewById(R.id.texttoolbar);
        texttoolbar.setText("Ubah Data Jurnal PKL");

        tanggal = findViewById(R.id.tanggal);
        spinner_mapel = findViewById(R.id.spinner_mapel);
        spinner_kompetensi_dasar = findViewById(R.id.spinner_kompetensi_dasar);
        txt_hasil_mapel = findViewById(R.id.txt_hasil_mapel);
        txt_hasil_kompetensi_dasar = findViewById(R.id.txt_hasil_kompetensi_dasar);
        topik_pekerjaan = findViewById(R.id.topik_pekerjaan);
        btn_simpan = findViewById(R.id.simpan_tambah_ubah);
        ivPhoto = findViewById(R.id.iv_photo);
        ivPhoto.setOnClickListener(this);

        btn_simpan.setText("Ubah");

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            ed_id_jurnal_pkl = intent.getString("ed_id_jurnal_pkl");
            ed_id_siswa = intent.getString("ed_id_siswa");
            ed_tanggal = intent.getString("ed_tanggal");
            ed_kompetensi_dasar = intent.getString("ed_kompetensi_dasar");
            ed_topik_pekerjaan = intent.getString("ed_topik_pekerjaan");
            ed_dokumentasi = intent.getString("ed_dokumentasi");
            Glide.with(this)
                    .load( Server.URLDoc + intent.getString("ed_dokumentasi"))
                    .apply(new RequestOptions().centerCrop())
                    .into(ivPhoto);
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

        adapter = new AdapterListMapel(UbahJurnalPKL.this, listmapel);
        adapter1 = new AdapterListKompetensiDasar(UbahJurnalPKL.this, listkompetensidasar);
        spinner_mapel.setAdapter(adapter);
        spinner_kompetensi_dasar.setAdapter(adapter1);
        callData();

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggalx = tanggal.getText().toString();
                String kompetensi_dasarx = txt_hasil_kompetensi_dasar.getText().toString();
                String topik_pekerjaanx = topik_pekerjaan.getText().toString();
                String dokumentasix = getStringImage(decoded);

                if (tanggalx.isEmpty()) {
                    Toast.makeText(UbahJurnalPKL.this, "Tanggal Jurnal PKL masih kosong!", Toast.LENGTH_SHORT).show();
                } else if (kompetensi_dasarx.isEmpty()) {
                    Toast.makeText(UbahJurnalPKL.this, "Rujukan Kompetensi Dasar masih kosong!", Toast.LENGTH_SHORT).show();
                } else if (topik_pekerjaanx.isEmpty()) {
                    Toast.makeText(UbahJurnalPKL.this, "Topik Pekerjaan masih kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    updateData(ed_id_jurnal_pkl, ed_id_siswa, tanggalx, kompetensi_dasarx, topik_pekerjaanx, dokumentasix);
                }
            }
        });
    }

    private void callData() {
        listmapel.clear();
        pDialog = new ProgressDialog(UbahJurnalPKL.this);
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
                    Toast.makeText(UbahJurnalPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(UbahJurnalPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(UbahJurnalPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(UbahJurnalPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(UbahJurnalPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(UbahJurnalPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UbahJurnalPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(UbahJurnalPKL.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void callData1() {
        listkompetensidasar.clear();
        pDialog = new ProgressDialog(UbahJurnalPKL.this);
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
                    Toast.makeText(UbahJurnalPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(UbahJurnalPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(UbahJurnalPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(UbahJurnalPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(UbahJurnalPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(UbahJurnalPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UbahJurnalPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(UbahJurnalPKL.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.iv_photo:
                pickImage();
                break;
        }
    }

    private void pickImage() {

        ivPhoto.setImageResource(0);
        final CharSequence[] items = {"Kamera", "Galeri",
                "Batal"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UbahJurnalPKL.this);
        builder.setTitle("Tambahkan Foto / Dokumentasi");
        builder.setIcon(R.mipmap.login);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Kamera")) {
                    intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Galeri")) {
                    intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), SELECT_FILE);
                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("onActivityResult", "requestCode " + requestCode + ", resultCode " + resultCode);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                try {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    setToImageView(getResizedBitmap(bitmap, max_resolution_image));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_FILE && data != null && data.getData() != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(UbahJurnalPKL.this.getContentResolver(), data.getData());
                    setToImageView(getResizedBitmap(bitmap, max_resolution_image));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        return encodedImage;
    }

    private void setToImageView(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        ivPhoto.setImageBitmap(decoded);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void updateData(final String id_jurnal_pkl, final String id_siswa, final String tanggal, final String id_kompetensi_dasar, final String topik_pekerjaan, final String dokumentasi) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Menyimpan data...");
        showDialog();
        String url = Server.URL + "siswa_ubah_jurnal_pkl.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                int status_kode = responStatus.getStatus_kode();
                String status_pesan = responStatus.getStatus_pesan();
                if (status_kode == 1) {
                    Toast.makeText(UbahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                    JurnalPKL.mInstance.MuatData();
                    hideDialog();
                    finish();
                } else if (status_kode == 2) {
                    Toast.makeText(UbahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 3) {
                    Toast.makeText(UbahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toast.makeText(UbahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toast.makeText(UbahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 6) {
                    Toast.makeText(UbahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 7) {
                    Toast.makeText(UbahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 8) {
                    Toast.makeText(UbahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 9) {
                    Toast.makeText(UbahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 10) {
                    Toast.makeText(UbahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UbahJurnalPKL.this, "Status Kesalahan Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(UbahJurnalPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(UbahJurnalPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(UbahJurnalPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(UbahJurnalPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(UbahJurnalPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(UbahJurnalPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UbahJurnalPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id_jurnal_pkl", String.valueOf(id_jurnal_pkl));
                hashMap.put("id_siswa", id_siswa);
                hashMap.put("tanggal", tanggal);
                hashMap.put("id_kompetensi_dasar", id_kompetensi_dasar);
                hashMap.put("topik_pekerjaan", topik_pekerjaan);
                hashMap.put("dokumentasi", dokumentasi);
                return hashMap;
            }
        };
        AppController.getInstance().addToQueue(request, "ubah_jurnal_pkl");
    }
}
