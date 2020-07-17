package com.rizkyghofur.aplikasipklsmkn1glagah.adapter;

import android.app.Activity;
import android.content.Context;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataAbsensiPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class AdapterAbsensiPKL extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataAbsensiPKL> items;

    public AdapterAbsensiPKL(Activity activity, List<DataAbsensiPKL> items) {
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
            convertView = inflater.inflate(R.layout.activity_guru_absensi_pkl_list, null);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_kakomp_absensi_pkl_siswa_list, null);

        TextView id_absensi = convertView.findViewById(R.id.id_absensi);
        TextView nama_siswa = convertView.findViewById(R.id.nama_siswa);
        TextView kelas = convertView.findViewById(R.id.kelas);
        TextView tanggal_absensi = convertView.findViewById(R.id.tanggal_absensi);
        TextView keterangan = convertView.findViewById(R.id.keterangan);
        TextView nama_dudi = convertView.findViewById(R.id.nama_dudi);

        DataAbsensiPKL data = items.get(position);

        id_absensi.setText(data.getId_absensi());
        nama_siswa.setText("Nama Siswa : "+data.getId_siswa());
        kelas.setText("Kelas : " + data.getKelas());
        tanggal_absensi.setText("Tanggal Absensi : "+ data.getTanggal_absensi());
        keterangan.setText("Keterangan : "+ data.getKeterangan());
        nama_dudi.setText("DUDI : " + data.getNama_dudi());

        return convertView;
    }

}
