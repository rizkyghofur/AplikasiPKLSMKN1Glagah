package com.rizkyghofur.aplikasipklsmkn1glagah.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataJurnalPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.ResponStatus;
import com.rizkyghofur.aplikasipklsmkn1glagah.siswa.UbahJurnalPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import java.util.ArrayList;

public class AdapterJurnalPKLSiswa extends RecyclerView.Adapter<AdapterJurnalPKLSiswa.ViewHolder> {

    private Context context;
    private ArrayList<DataJurnalPKL> arrayJurnalPKL;

    public AdapterJurnalPKLSiswa(Context context, ArrayList<DataJurnalPKL> arrayJurnalPKL) {
        this.context = context;
        this.arrayJurnalPKL = arrayJurnalPKL;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_siswa_jurnal_pkl_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataJurnalPKL JurnalPKL = arrayJurnalPKL.get(position);

        holder.id_jurnal_pkl.setText(JurnalPKL.getId_jurnal_pkl());
        holder.id_siswa.setText(JurnalPKL.getId_siswa());
        holder.tanggal.setText("Tanggal Kunjungan : " + JurnalPKL.getTanggal());
        holder.kompetensi_dasar.setText("Rujukan Kompetensi Dasar : " + JurnalPKL.getKompetensi_dasar());
        holder.topik_pekerjaan.setText("Topik Pekerjaan : " + JurnalPKL.getTopik_pekerjaan());
        holder.status.setText("Status Validasi : " + JurnalPKL.getStatus());
        holder.catatan.setText("Catatan : " + JurnalPKL.getCatatan());
        Glide.with(context)
                .load(Server.URLDoc + JurnalPKL.getDokumentasi())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.dokumentasi);
    }

    @Override
    public int getItemCount() {
        return arrayJurnalPKL.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id_jurnal_pkl, id_siswa, tanggal, kompetensi_dasar, topik_pekerjaan, status, status1, status2, catatan;
        private ImageView dokumentasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_jurnal_pkl = itemView.findViewById(R.id.id_jurnal_pkl);
            id_siswa = itemView.findViewById(R.id.id_siswa);
            tanggal = itemView.findViewById(R.id.tanggal);
            kompetensi_dasar = itemView.findViewById(R.id.kompetensi_dasar);
            topik_pekerjaan = itemView.findViewById(R.id.topik_pekerjaan);
            status = itemView.findViewById(R.id.status_validasi);
            status1 = itemView.findViewById(R.id.status_validasi1);
            status2 = itemView.findViewById(R.id.status_validasi2);
            catatan = itemView.findViewById(R.id.catatan);
            dokumentasi = itemView.findViewById(R.id.dokumentasi);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int position = getAdapterPosition();
                    final DataJurnalPKL JurnalPKL = arrayJurnalPKL.get(position);

                    String[] pilihan = {"Lihat", "Ubah", "Hapus"};
                    new AlertDialog.Builder(context)
                            .setTitle("Pilihan")
                            .setItems(pilihan, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        lihatDataJurnalPKL(JurnalPKL);
                                    } else if (which == 1) {
                                        ubahDataJurnalPKL(JurnalPKL);
                                    } else if (which == 2) { //
                                        hapusDataJurnalPKL(position, JurnalPKL);
                                    }
                                }
                            })
                            .create()
                            .show();
                    return false;
                }
            });
        }

        private void lihatDataJurnalPKL(@NonNull DataJurnalPKL JurnalPKL) {
            String deskripsi =
                    "\n Waktu Pelaksanaan : " + JurnalPKL.getTanggal() +
                            "\n\n Rujukan Kompetensi Dasar : \n" + JurnalPKL.getKompetensi_dasar() +
                                 "\n\n DUDI : \n" + JurnalPKL.getId_dudi() +
                                    "\n\n Topik Pekerjaan : \n" + JurnalPKL.getTopik_pekerjaan() +
                                        "\n\n Status Validasi : \n" + JurnalPKL.getStatus() +
                                                    "\n\n Catatan : \n" + JurnalPKL.getCatatan();

            new AlertDialog.Builder(context)
                    .setTitle("Detail JurnalPKL")
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

        private void ubahDataJurnalPKL(@NonNull DataJurnalPKL JurnalPKL) {
            Intent intent = new Intent(context, UbahJurnalPKL.class);
            intent.putExtra("ed_id_jurnal_pkl", JurnalPKL.getId_jurnal_pkl());
            intent.putExtra("ed_id_siswa", JurnalPKL.getId_siswa());
            intent.putExtra("ed_tanggal", JurnalPKL.getTanggal());
            intent.putExtra("ed_nama_dudi", JurnalPKL.getId_dudi());
            intent.putExtra("ed_kompetensi_dasar", JurnalPKL.getKompetensi_dasar());
            intent.putExtra("ed_id_mapel", JurnalPKL.getId_mapel());
            intent.putExtra("ed_topik_pekerjaan", JurnalPKL.getTopik_pekerjaan());
            intent.putExtra("ed_dokumentasi", JurnalPKL.getDokumentasi());
            context.startActivity(intent);
        }

        private void hapusDataJurnalPKL(final int position, @NonNull DataJurnalPKL JurnalPKL) {
            String url = Server.URL + "siswa_hapus_jurnal_pkl.php?id_jurnal_pkl=" + JurnalPKL.getId_jurnal_pkl();
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                    int status_kode = responStatus.getStatus_kode();
                    String status_pesan = responStatus.getStatus_pesan();
                    if (status_kode == 1) {
                        Toast.makeText(context, status_pesan, Toast.LENGTH_SHORT).show();
                        notifyItemRemoved(position);
                        arrayJurnalPKL.remove(position);
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
            AppController.getInstance().addToQueue(request, "hapus_jurnal_pkl");
        }
    }
}

