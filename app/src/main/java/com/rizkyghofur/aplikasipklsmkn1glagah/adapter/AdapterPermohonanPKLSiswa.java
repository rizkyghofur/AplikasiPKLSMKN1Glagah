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
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataPermohonanPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import java.util.ArrayList;

public class AdapterPermohonanPKLSiswa extends RecyclerView.Adapter<AdapterPermohonanPKLSiswa.ViewHolder> {

    private Context context;
    private ArrayList<DataPermohonanPKL> arrayPermohonanPKLSiswa;

    public AdapterPermohonanPKLSiswa(Context context, ArrayList<DataPermohonanPKL> arrayPermohonanPKLSiswa) {
        this.context = context;
        this.arrayPermohonanPKLSiswa = arrayPermohonanPKLSiswa;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_siswa_permohonan_pkl_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataPermohonanPKL PermohonanPKLSiswa = arrayPermohonanPKLSiswa.get(position);

        holder.id_permohonan_pkl.setText(PermohonanPKLSiswa.getId());
        holder.id_siswa.setText(PermohonanPKLSiswa.getId_siswa());
        holder.nama_dudi.setText("DUDI : " + PermohonanPKLSiswa.getId_dudi());
        holder.nama_guru.setText("Guru Pembimbing : " + PermohonanPKLSiswa.getId_guru());
        holder.tanggal_masuk.setText("Tanggal Masuk : " + PermohonanPKLSiswa.getTanggal_masuk());
        holder.tanggal_keluar.setText("Tanggal Keluar : " + PermohonanPKLSiswa.getTanggal_keluar());
        holder.status_validasi.setText("Status Validasi : " + PermohonanPKLSiswa.getStatus_validasi());
    }

    @Override
    public int getItemCount() {
        return arrayPermohonanPKLSiswa.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id_permohonan_pkl, id_siswa, tanggal_masuk, tanggal_keluar, nama_guru, nama_dudi, status_validasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_permohonan_pkl = itemView.findViewById(R.id.id_permohonan_pkl);
            id_siswa = itemView.findViewById(R.id.id_siswa);
            tanggal_masuk = itemView.findViewById(R.id.tanggal_masuk);
            tanggal_keluar = itemView.findViewById(R.id.tanggal_keluar);
            nama_dudi = itemView.findViewById(R.id.nama_dudi);
            nama_guru = itemView.findViewById(R.id.nama_guru);
            status_validasi = itemView.findViewById(R.id.status_validasi);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int position = getAdapterPosition();
                    final DataPermohonanPKL PermohonanPKLSiswa = arrayPermohonanPKLSiswa.get(position);

                    String[] pilihan = {"Lihat", "Hapus"};
                    new AlertDialog.Builder(context)
                            .setTitle("Pilihan")
                            .setItems(pilihan, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        lihatDataPermohonanPKLSiswa(PermohonanPKLSiswa);
                                    } else if (which == 1) { //
                                        hapusDataPermohonanPKLSiswa(position, PermohonanPKLSiswa);
                                    }
                                }
                            })
                            .create()
                            .show();
                    return false;
                }
            });
        }

        private void lihatDataPermohonanPKLSiswa(@NonNull DataPermohonanPKL PermohonanPKLSiswa) {
            String deskripsi =
                    "\n DUDI : " + PermohonanPKLSiswa.getId_dudi() +
                            "\n\n Guru Pembimbing : \n" + PermohonanPKLSiswa.getId_guru() +
                            "\n\n Tanggal Masuk : \n" + PermohonanPKLSiswa.getTanggal_masuk() +
                            "\n\n Tanggal Keluar : \n" + PermohonanPKLSiswa.getTanggal_keluar() +
                            "\n\n Status Validasi : \n" + PermohonanPKLSiswa.getStatus_validasi();


            new AlertDialog.Builder(context)
                    .setTitle("Detail Permohonan PKL Siswa")
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

        private void hapusDataPermohonanPKLSiswa(final int position, @NonNull DataPermohonanPKL PermohonanPKLSiswa) {
            String url = Server.URL + "hapus_permohonan_pkl_siswa.php?id_pengajuanpkl=" + PermohonanPKLSiswa.getId();
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                    int status_kode = responStatus.getStatus_kode();
                    String status_pesan = responStatus.getStatus_pesan();
                    if (status_kode == 1) {
                            Toast.makeText(context, status_pesan, Toast.LENGTH_LONG).show();
                            notifyItemRemoved(position);
                            arrayPermohonanPKLSiswa.remove(position);
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
}

