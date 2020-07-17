package com.rizkyghofur.aplikasipklsmkn1glagah.siswa;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.adapter.ResponStatus;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import java.util.HashMap;
import java.util.Map;

public class UbahProgramPKL extends AppCompatActivity {

    private EditText tanggal, kompetensi_dasar, dudi_pasangan, topik_pekerjaan;
    private Button btn_simpan;
    private String ed_id_program_pkl, ed_id_siswa, ed_tanggal, ed_kompetensi_dasar, ed_dudi_pasangan, ed_topik_pekerjaan;
    Toolbar toolbar;
    TextView texttoolbar;

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
        kompetensi_dasar = findViewById(R.id.kompetensi_dasar);
        dudi_pasangan = findViewById(R.id.dudi_pasangan);
        topik_pekerjaan = findViewById(R.id.topik_pekerjaan);
        btn_simpan = findViewById(R.id.simpan_tambah_ubah);

        btn_simpan.setText("Ubah");

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            ed_id_program_pkl = intent.getString("ed_id_program_pkl");
            ed_id_siswa = intent.getString("ed_id_siswa");
            ed_tanggal = intent.getString("ed_tanggal");
            ed_kompetensi_dasar = intent.getString("ed_kompetensi_dasar");
            ed_dudi_pasangan = intent.getString("ed_dudi_pasangan");
            ed_topik_pekerjaan = intent.getString("ed_topik_pekerjaan");
        }

        tanggal.setText(ed_tanggal);
        kompetensi_dasar.setText(ed_kompetensi_dasar);
        dudi_pasangan.setText(ed_dudi_pasangan);
        topik_pekerjaan.setText(ed_topik_pekerjaan);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggalx = tanggal.getText().toString();
                String kompetensi_dasarx = kompetensi_dasar.getText().toString();
                String topik_pekerjaanx = topik_pekerjaan.getText().toString();
                String dudi_pasanganx = dudi_pasangan.getText().toString();


                if (tanggalx.isEmpty()) {
                    Toast.makeText(UbahProgramPKL.this, "Tanggal Program PKL masih kosong!", Toast.LENGTH_SHORT).show();
                } else if (kompetensi_dasarx.isEmpty()) {
                    Toast.makeText(UbahProgramPKL.this, "Rujukan Kompetensi Dasar masih kosong!", Toast.LENGTH_SHORT).show();
                } else if (topik_pekerjaanx.isEmpty()) {
                    Toast.makeText(UbahProgramPKL.this, "Topik Pekerjaan Dasar masih kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    updateData(ed_id_program_pkl, ed_id_siswa, tanggalx, kompetensi_dasarx, dudi_pasanganx, topik_pekerjaanx);
                }
            }
        });
    }

    private void updateData(final String id_program_pkl, final String id_siswa, final String tanggal, final String kompetensi_dasar, final String dudi_pasangan, final String topik_pekerjaan) {
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
                    Toast.makeText(UbahProgramPKL.this, "Network TimeoutError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(UbahProgramPKL.this, "Nerwork NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(UbahProgramPKL.this, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(UbahProgramPKL.this, "Server Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(UbahProgramPKL.this, "Network Error", Toast.LENGTH_SHORT).show();
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
                hashMap.put("kompetensi_dasar", kompetensi_dasar);
                hashMap.put("dudi_pasangan", dudi_pasangan);
                hashMap.put("topik_pekerjaan", topik_pekerjaan);

                return hashMap;
            }
        };
        AppController.getInstance().addToQueue(request, "ubah_program_pkl");
    }
}
