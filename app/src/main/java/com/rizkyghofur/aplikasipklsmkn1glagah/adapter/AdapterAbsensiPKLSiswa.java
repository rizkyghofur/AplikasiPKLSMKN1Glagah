package com.rizkyghofur.aplikasipklsmkn1glagah.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataAbsensiPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import java.util.ArrayList;

public class AdapterAbsensiPKLSiswa extends RecyclerView.Adapter<AdapterAbsensiPKLSiswa.ViewHolder> {

    private Context context;
    private ArrayList<DataAbsensiPKL> arrayAbsensiPKLSiswa;

    public AdapterAbsensiPKLSiswa(Context context, ArrayList<DataAbsensiPKL> arrayAbsensiPKLSiswa) {
        this.context = context;
        this.arrayAbsensiPKLSiswa = arrayAbsensiPKLSiswa;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_siswa_absensi_pkl_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataAbsensiPKL AbsensiPKLSiswa = arrayAbsensiPKLSiswa.get(position);

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
                    final DataAbsensiPKL AbsensiPKLSiswa = arrayAbsensiPKLSiswa.get(position);

                    String[] pilihan = {"Lihat"};
                    new AlertDialog.Builder(context)
                            .setTitle("Pilihan")
                            .setItems(pilihan, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        lihatDataAbsensiPKLSiswa(AbsensiPKLSiswa);
                                    }
                                }
                            })
                            .create()
                            .show();
                    return false;
                }
            });
        }

        private void lihatDataAbsensiPKLSiswa(@NonNull DataAbsensiPKL AbsensiPKLSiswa) {
            String deskripsi =
                    "\n Nama Siswa : " + AbsensiPKLSiswa.getId_siswa() +
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
}

