package com.rizkyghofur.aplikasipklsmkn1glagah.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataPresensiPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.ResponStatus;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;

import java.util.ArrayList;

public class AdapterPresensiPKLSiswa extends RecyclerView.Adapter<AdapterPresensiPKLSiswa.ViewHolder> {

    private Context context;
    private ArrayList<DataPresensiPKL> arrayAbsensiPKLSiswa;

    public AdapterPresensiPKLSiswa(Context context, ArrayList<DataPresensiPKL> arrayAbsensiPKLSiswa) {
        this.context = context;
        this.arrayAbsensiPKLSiswa = arrayAbsensiPKLSiswa;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_siswa_presensi_pkl_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataPresensiPKL AbsensiPKLSiswa = arrayAbsensiPKLSiswa.get(position);

        holder.id_absensi_pkl.setText(AbsensiPKLSiswa.getId_absensi());
        holder.id_siswa.setText("Nama Siswa : "+ AbsensiPKLSiswa.getId_siswa());
        holder.tanggal_absensi.setText("Tanggal Absensi : " + AbsensiPKLSiswa.getTanggal_absensi());
        holder.keterangan.setText("Keterangan : " + AbsensiPKLSiswa.getKeterangan());
    }

    @Override
    public int getItemCount() {
        return arrayAbsensiPKLSiswa.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id_absensi_pkl, id_siswa, tanggal_absensi, keterangan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_absensi_pkl = itemView.findViewById(R.id.id_absensi);
            id_siswa = itemView.findViewById(R.id.nama_siswa);
            tanggal_absensi = itemView.findViewById(R.id.tanggal_absensi);
            keterangan = itemView.findViewById(R.id.keterangan);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int position = getAdapterPosition();
                    final DataPresensiPKL AbsensiPKLSiswa = arrayAbsensiPKLSiswa.get(position);

                    String[] pilihan = {"Lihat", "Hapus"};
                    new AlertDialog.Builder(context)
                            .setTitle("Pilihan")
                            .setItems(pilihan, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        lihatDataAbsensiPKLSiswa(AbsensiPKLSiswa);
                                    }
                                    if (which == 1) {
                                        hapusDataAbsensiPKLSiswa(position, AbsensiPKLSiswa);
                                    }
                                }
                            })
                            .create()
                            .show();
                    return false;
                }
            });
        }

        private void lihatDataAbsensiPKLSiswa(@NonNull DataPresensiPKL AbsensiPKLSiswa) {
            String deskripsi =
                    "\n Nama Siswa : \n" + AbsensiPKLSiswa.getId_siswa() +
                            "\n\n Tanggal Absensi : " + AbsensiPKLSiswa.getTanggal_absensi() +
                                    "\n\n Keterangan : " + AbsensiPKLSiswa.getKeterangan();


            new AlertDialog.Builder(context)
                    .setTitle("Detail Absensi PKL Siswa")
                    .setMessage(deskripsi)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    }

    private void hapusDataAbsensiPKLSiswa(final int position, @NonNull DataPresensiPKL absensiPKLSiswa) {
        String url = Server.URL + "hapus_absensi_pkl_siswa.php?id_absensi=" + absensiPKLSiswa.getId_absensi();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                int status_kode = responStatus.getStatus_kode();
                String status_pesan = responStatus.getStatus_pesan();
                if (status_kode == 1) {
                    Toast.makeText(context, status_pesan, Toast.LENGTH_LONG).show();
                    notifyItemRemoved(position);
                    arrayAbsensiPKLSiswa.remove(position);
                } else if (status_kode == 2) {
                    Toast.makeText(context, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 3) {
                    Toast.makeText(context, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toast.makeText(context, status_pesan, Toast.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toast.makeText(context, status_pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Status Kesalahan Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(context, "Network TimeoutError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "Nerwork NoConnectionError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "Network AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, "Parse Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Status Error Tidak Diketahui!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppController.getInstance().addToQueue(request, "hapus_permohonan_pkl");
    }
}

