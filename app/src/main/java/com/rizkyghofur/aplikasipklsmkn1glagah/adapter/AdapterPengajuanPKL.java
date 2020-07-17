package com.rizkyghofur.aplikasipklsmkn1glagah.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataPermohonanPKL;
import android.view.ViewGroup;
import android.view.View;
import android.widget.BaseAdapter;
import java.util.List;

public class AdapterPengajuanPKL extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataPermohonanPKL> items;

    public AdapterPengajuanPKL(Activity activity, List<DataPermohonanPKL> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_kakomp_pengajuanpkl_list, null);

        TextView id_pengajuanpkl = convertView.findViewById(R.id.id_permohonan_pkl);
        TextView id_siswa = convertView.findViewById(R.id.id_siswa);
        TextView id_dudi = convertView.findViewById(R.id.id_dudi);
        TextView nama_siswa = convertView.findViewById(R.id.nama_siswa);
        TextView kelas = convertView.findViewById(R.id.kelas);
        TextView tanggal_masuk = convertView.findViewById(R.id.tanggal_masuk);
        TextView tanggal_keluar = convertView.findViewById(R.id.tanggal_keluar);
        TextView id_guru = convertView.findViewById(R.id.id_guru);
        TextView status_validasi = convertView.findViewById(R.id.status_validasi);

        DataPermohonanPKL data = items.get(position);

        id_pengajuanpkl.setText(data.getId());
        id_siswa.setText(data.getId_siswa());
        id_dudi.setText("Tempat DUDI : " + data.getId_dudi());
        nama_siswa.setText("Nama Siswa : " + data.getNama_siswa());
        kelas.setText("Kelas : " + data.getKelas());
        tanggal_masuk.setText("Tanggal Masuk : " + data.getTanggal_masuk());
        tanggal_keluar.setText("Tanggal Keluar : " + data.getTanggal_keluar());
        id_guru.setText("Guru : " + data.getId_guru());
        status_validasi.setText("Status Validasi : " + data.getStatus_validasi());

        return convertView;
    }
}
