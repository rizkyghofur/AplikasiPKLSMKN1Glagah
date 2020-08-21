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
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataProgramPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.ResponStatus;
import com.rizkyghofur.aplikasipklsmkn1glagah.siswa.UbahProgramPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.AppController;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;
import java.util.ArrayList;

public class AdapterProgramPKLSiswa extends RecyclerView.Adapter<AdapterProgramPKLSiswa.ViewHolder> {

    private Context context;
    private ArrayList<DataProgramPKL> arrayProgramPKL;

    public AdapterProgramPKLSiswa(Context context, ArrayList<DataProgramPKL> arrayProgramPKL) {
        this.context = context;
        this.arrayProgramPKL = arrayProgramPKL;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_siswa_program_pkl_list, parent, false);
        return new ViewHolder(view);
    }
    
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataProgramPKL programpkl = arrayProgramPKL.get(position);

        holder.id_program_pkl.setText(programpkl.getId_programpkl());
        holder.id_siswa.setText(programpkl.getId_siswa());
        holder.tanggal.setText("Tanggal Kunjungan : " + programpkl.getUrutan_waktu_pelaksanaan());
        holder.nama_dudi.setText("Instansi / DUDI Pasangan : " + programpkl.getDudi_pasangan());
        holder.kompetensi_dasar.setText("Rujukan Kompetensi Dasar : " + programpkl.getKompetensi_dasar());
        holder.topik_pekerjaan.setText("Topik Pekerjaan : " + programpkl.getTopik_pekerjaan());
    }

    @Override
    public int getItemCount() {
        return arrayProgramPKL.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id_program_pkl, id_siswa, tanggal, nama_dudi, kompetensi_dasar, topik_pekerjaan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_program_pkl = itemView.findViewById(R.id.id_program_pkl);
            id_siswa = itemView.findViewById(R.id.id_siswa);
            tanggal = itemView.findViewById(R.id.tanggal);
            nama_dudi = itemView.findViewById(R.id.nama_dudi);
            kompetensi_dasar = itemView.findViewById(R.id.kompetensi_dasar);
            topik_pekerjaan = itemView.findViewById(R.id.topik_pekerjaan);
            
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int position = getAdapterPosition();
                    final DataProgramPKL programpkl = arrayProgramPKL.get(position);

                    String[] pilihan = {"Lihat", "Ubah", "Hapus"};
                    new AlertDialog.Builder(context)
                            .setTitle("Pilihan")
                            .setItems(pilihan, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        lihatDataProgramPKL(programpkl);
                                    } else if (which == 1) {
                                        ubahDataProgramPKL(programpkl);
                                    } else if (which == 2) { //
                                        hapusDataProgramPKL(position, programpkl);
                                    }
                                }
                            })
                            .create()
                            .show();
                    return false;
                }
            });
        }

        private void lihatDataProgramPKL(@NonNull DataProgramPKL programpkl) {
            String deskripsi =
                    "\n Waktu Pelaksanaan : " + programpkl.getUrutan_waktu_pelaksanaan() +
                            "\n\n Instansi / DUDI Pasangan : \n" + programpkl.getDudi_pasangan() +
                                        "\n\n Rujukan Kompetensi Dasar : \n" + programpkl.getKompetensi_dasar() +
                                                   "\n\n Topik Pekerjaan : \n" + programpkl.getTopik_pekerjaan();
                    

            new AlertDialog.Builder(context)
                    .setTitle("Detail ProgramPKL")
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

        private void ubahDataProgramPKL(@NonNull DataProgramPKL programpkl) {
            Intent intent = new Intent(context, UbahProgramPKL.class);
            intent.putExtra("ed_id_program_pkl", programpkl.getId_programpkl());
            intent.putExtra("ed_id_siswa", programpkl.getId_siswa());
            intent.putExtra("ed_dudi_pasangan", programpkl.getDudi_pasangan());
            intent.putExtra("ed_tanggal", programpkl.getUrutan_waktu_pelaksanaan());
            intent.putExtra("ed_kompetensi_dasar", programpkl.getKompetensi_dasar());
            intent.putExtra("ed_topik_pekerjaan", programpkl.getTopik_pekerjaan());
            context.startActivity(intent);
        }

        private void hapusDataProgramPKL(final int position, @NonNull DataProgramPKL programpkl) {
            String url = Server.URL + "siswa_hapus_program_pkl.php?id_program_pkl=" + programpkl.getId_programpkl();
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                    int status_kode = responStatus.getStatus_kode();
                    String status_pesan = responStatus.getStatus_pesan();
                    if (status_kode == 1) {
                        Toast.makeText(context, status_pesan, Toast.LENGTH_SHORT).show();
                        notifyItemRemoved(position);
                        arrayProgramPKL.remove(position);
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
            AppController.getInstance().addToQueue(request, "hapus_program_pkl");
        }
    }
}

