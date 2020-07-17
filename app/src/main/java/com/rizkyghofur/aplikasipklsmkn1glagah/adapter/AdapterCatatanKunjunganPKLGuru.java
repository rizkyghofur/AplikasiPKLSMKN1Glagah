package com.rizkyghofur.aplikasipklsmkn1glagah.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataCatatanKunjunganPKLGuru;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.guru.UbahCatatanKunjunganPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import java.util.ArrayList;

public class AdapterCatatanKunjunganPKLGuru extends RecyclerView.Adapter<AdapterCatatanKunjunganPKLGuru.ViewHolder> {

    private Context context;
    private ArrayList<DataCatatanKunjunganPKLGuru> arrayCatatanKunjunganPKL;

    public AdapterCatatanKunjunganPKLGuru(Context context, ArrayList<DataCatatanKunjunganPKLGuru> arrayCatatanKunjunganPKL) {
        this.context = context;
        this.arrayCatatanKunjunganPKL = arrayCatatanKunjunganPKL;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_guru_catatan_kunjungan_pkl_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataCatatanKunjunganPKLGuru catatankunjungan = arrayCatatanKunjunganPKL.get(position);

        holder.id_catatan_kunjungan_pkl.setText(catatankunjungan.getId_catatan_kunjungan_pkl());
        holder.id_guru.setText(catatankunjungan.getId_guru());
        holder.tanggal_kunjungan.setText("Tanggal Kunjungan : " + catatankunjungan.getTanggal_kunjungan());
        holder.catatan.setText("Catatan : " + catatankunjungan.getCatatan_pembimbing());
    }

    @Override
    public int getItemCount() {
        return arrayCatatanKunjunganPKL.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id_catatan_kunjungan_pkl, id_guru, tanggal_kunjungan, catatan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_catatan_kunjungan_pkl = itemView.findViewById(R.id.id_catatan_kunjungan_pkl);
            id_guru = itemView.findViewById(R.id.id_guru);
            tanggal_kunjungan = itemView.findViewById(R.id.tanggal_kunjungan);
            catatan = itemView.findViewById(R.id.catatan_pembimbing);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int position = getAdapterPosition();
                    final DataCatatanKunjunganPKLGuru catatankunjungan = arrayCatatanKunjunganPKL.get(position);

                    String[] pilihan = {"Ubah", "Hapus"};
                    new AlertDialog.Builder(context)
                            .setTitle("Pilihan")
                            .setItems(pilihan, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                     if (which == 0) {
                                        ubahDataCatatan(catatankunjungan);
                                    } else if (which == 1) { //
                                        hapusDataCatatan(position, catatankunjungan);
                                    }
                                }
                            })
                            .create()
                            .show();
                    return false;
                }
            });
        }

        private void ubahDataCatatan(@NonNull DataCatatanKunjunganPKLGuru catatankunjungan) {
            Intent intent = new Intent(context, UbahCatatanKunjunganPKL.class);
            intent.putExtra("ed_id_catatan_kunjungan_pkl", catatankunjungan.getId_catatan_kunjungan_pkl());
            intent.putExtra("ed_id_guru", catatankunjungan.getId_guru());
            intent.putExtra("ed_tanggal_kunjungan", catatankunjungan.getTanggal_kunjungan());
            intent.putExtra("ed_catatan_pembimbing", catatankunjungan.getCatatan_pembimbing());
            context.startActivity(intent);
        }

        private void hapusDataCatatan(final int position, @NonNull DataCatatanKunjunganPKLGuru catatankunjungan) {
            String url = Server.URL + "hapuscatatankunjunganpkl_guru.php?id_catatan_kunjungan_pkl=" + catatankunjungan.getId_catatan_kunjungan_pkl();
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                    int status_kode = responStatus.getStatus_kode();
                    String status_pesan = responStatus.getStatus_pesan();
                    if (status_kode == 1) {
                        Toast.makeText(context, status_pesan, Toast.LENGTH_SHORT).show();
                        notifyItemRemoved(position);
                        arrayCatatanKunjunganPKL.remove(position);
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
            AppController.getInstance().addToQueue(request, "hapus_catatan_kunjungan_pkl");
        }
    }
}
