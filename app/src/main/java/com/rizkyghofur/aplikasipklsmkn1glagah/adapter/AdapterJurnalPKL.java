package com.rizkyghofur.aplikasipklsmkn1glagah.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataJurnalPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.handler.Server;

import java.util.List;

public class AdapterJurnalPKL extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataJurnalPKL> item;

    public AdapterJurnalPKL(Activity activity, List<DataJurnalPKL> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
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
            convertView = inflater.inflate(R.layout.activity_guru_jurnal_pkl_list, null);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_kakomp_jurnal_pkl_siswa_list, null);

        TextView id_jurnal_pkl = convertView.findViewById(R.id.id_jurnal_pkl);
        TextView nama_siswa = convertView.findViewById(R.id.nama_siswa);
        TextView kelas = convertView.findViewById(R.id.kelas);
        TextView kompetensi_dasar = convertView.findViewById(R.id.kompetensi_dasar);
        TextView tanggal = convertView.findViewById(R.id.tanggal);
        TextView topik_pekerjaan = convertView.findViewById(R.id.topik_pekerjaan);
        TextView nama_dudi = convertView.findViewById(R.id.nama_dudi);
        ImageView dokumentasi = convertView.findViewById(R.id.dokumentasi);

        DataJurnalPKL data = item.get(position);

        id_jurnal_pkl.setText(data.getId_jurnal_pkl());
        nama_siswa.setText("Nama Siswa : "+data.getId_siswa());
        kelas.setText("Kelas : " + data.getKelas());
        kompetensi_dasar.setText("Kompetensi Dasar : "+ data.getKompetensi_dasar());
        tanggal.setText("Tanggal : "+ data.getTanggal());
        topik_pekerjaan.setText("Topik Pekerjaan : "+ data.getTopik_pekerjaan());
        nama_dudi.setText("DUDI : "+ data.getId_dudi());
        Glide.with(convertView)
                .load(Server.URLDoc + data.getDokumentasi())
                .placeholder(R.mipmap.ic_launcher)
                .into(dokumentasi);
        return convertView;
    }
}
