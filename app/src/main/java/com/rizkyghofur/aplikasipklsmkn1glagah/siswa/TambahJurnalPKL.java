package com.rizkyghofur.aplikasipklsmkn1glagah.siswa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TambahJurnalPKL extends AppCompatActivity implements View.OnClickListener {

    private EditText tanggal, txt_hasil_mapel, txt_hasil_kompetensi_dasar, topik_pekerjaan;
    private Button btn_simpan, btn_tanggal;
    String user;
    SharedPreferences sharedpreferences;
    ImageView ivPhoto;
    Bitmap bitmap, decoded;
    int bitmap_size = 60;
    Intent intent;
    Uri fileUri;
    public final int REQUEST_CAMERA = 0;
    public final int SELECT_FILE = 1;
    int max_resolution_image = 1024;
    public static final String TAG_ID_USER = "id";
    public static final String TAG_ID_MAPEL = "id";
    public static final String TAG_MAPEL = "nama_mapel";
    public static final String TAG_ID_KOMPETENSI_DASAR = "id";
    public static final String TAG_KOMPETENSI_DASAR = "kompetensi_dasar";
    private static final String TAG = TambahJurnalPKL.class.getSimpleName();
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
        setContentView(R.layout.activity_siswa_jurnal_pkl_tambah_ubah);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        user = sharedpreferences.getString(TAG_ID_USER, "");
        tanggal = findViewById(R.id.tanggal);
        spinner_mapel = findViewById(R.id.spinner_mapel);
        spinner_kompetensi_dasar = findViewById(R.id.spinner_kompetensi_dasar);
        txt_hasil_mapel = findViewById(R.id.txt_hasil_mapel);
        txt_hasil_kompetensi_dasar = findViewById(R.id.txt_hasil_kompetensi_dasar);
        topik_pekerjaan = findViewById(R.id.topik_pekerjaan);
        ivPhoto = findViewById(R.id.iv_photo);
        ivPhoto.setOnClickListener(this);
        btn_simpan = findViewById(R.id.simpan_tambah_ubah);
        btn_tanggal = findViewById(R.id.btn_tanggal);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        String date = dateFormatter.format(Calendar.getInstance().getTime());
        tanggal.setText(date);

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

        adapter = new AdapterListMapel(TambahJurnalPKL.this, listmapel);
        adapter1 = new AdapterListKompetensiDasar(TambahJurnalPKL.this, listkompetensidasar);
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
                String dokumentasix = getStringImage(decoded);
                if(dokumentasix == null){
                    dokumentasix = "default.jpg";
                }

                if (tanggalx.isEmpty()) {
                    Toast.makeText(TambahJurnalPKL.this, "Tanggal Jurnal PKL masih kosong!", Toast.LENGTH_SHORT).show();
                } else if (kompetensi_dasarx.isEmpty()) {
                    Toast.makeText(TambahJurnalPKL.this, "Rujukan Kompetensi Dasar masih kosong!", Toast.LENGTH_SHORT).show();
                } else if (topik_pekerjaanx.isEmpty()) {
                    Toast.makeText(TambahJurnalPKL.this, "Topik Pekerjaan masih kosong!", Toast.LENGTH_SHORT).show();
                }
                else {
                    simpanData(tanggalx, kompetensi_dasarx, topik_pekerjaanx, dokumentasix);
                }
            }
        });
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
        final CharSequence[] items = {"Kamera", "Galeri",
                "Batal"};
        AlertDialog.Builder builder = new AlertDialog.Builder(TambahJurnalPKL.this);
        builder.setTitle("Tambahkan Dokumentasi");
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
                    bitmap = MediaStore.Images.Media.getBitmap(TambahJurnalPKL.this.getContentResolver(), data.getData());
                    setToImageView(getResizedBitmap(bitmap, max_resolution_image));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(bmp != null) {
            bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        } else {
            return null;
        }
    }
    
    private void callData() {
        listmapel.clear();
        pDialog = new ProgressDialog(TambahJurnalPKL.this);
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
                    Toast.makeText(TambahJurnalPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(TambahJurnalPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(TambahJurnalPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(TambahJurnalPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(TambahJurnalPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(TambahJurnalPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahJurnalPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(TambahJurnalPKL.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jArr);
    }

    private void callData1() {
        listkompetensidasar.clear();
        pDialog = new ProgressDialog(TambahJurnalPKL.this);
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
                    Toast.makeText(TambahJurnalPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(TambahJurnalPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(TambahJurnalPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(TambahJurnalPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(TambahJurnalPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(TambahJurnalPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahJurnalPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(TambahJurnalPKL.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

    private void simpanData(final String tanggal, final String kompetensi_dasar, final String topik_pekerjaan, final String dokumentasi) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Menyimpan data...");
        showDialog();
        String url = Server.URL + "siswa_tambah_jurnal_pkl.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                int status_kode = responStatus.getStatus_kode();
                String status_pesan = responStatus.getStatus_pesan();
                if (status_kode == 1) {
                    Toast.makeText(TambahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                    JurnalPKL.mInstance.MuatData();
                    finish();
                } else if (status_kode == 2) {
                    Toast.makeText(TambahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 3) {
                    Toast.makeText(TambahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toast.makeText(TambahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toast.makeText(TambahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 6) {
                    Toast.makeText(TambahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 7) {
                    Toast.makeText(TambahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 8) {
                    Toast.makeText(TambahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 9) {
                    Toast.makeText(TambahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 10) {
                    Toast.makeText(TambahJurnalPKL.this, status_pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahJurnalPKL.this, "Status Kesalahan Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(TambahJurnalPKL.this, "Waktu koneksi ke server habis", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(TambahJurnalPKL.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(TambahJurnalPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(TambahJurnalPKL.this, "Tidak dapat terhubung dengan server", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(TambahJurnalPKL.this, "Gangguan jaringan", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(TambahJurnalPKL.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahJurnalPKL.this, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("id_siswa", user);
                map.put("tanggal", tanggal);
                map.put("id_kompetensi_dasar", kompetensi_dasar);
                map.put("topik_pekerjaan", topik_pekerjaan);
                map.put("dokumentasi", dokumentasi);
                Log.e(TAG, "" + map);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(request, "tambah_jurnal_pkl");
    }
}