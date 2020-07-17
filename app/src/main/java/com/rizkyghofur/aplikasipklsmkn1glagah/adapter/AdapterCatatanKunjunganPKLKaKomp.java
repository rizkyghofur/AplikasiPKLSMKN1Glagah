package com.rizkyghofur.aplikasipklsmkn1glagah.adapter;

import android.app.Activity;
import android.content.Context;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataCatatanKunjunganPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class AdapterCatatanKunjunganPKLKaKomp extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataCatatanKunjunganPKL> items;

    public AdapterCatatanKunjunganPKLKaKomp(Activity activity, List<DataCatatanKunjunganPKL> items) {
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
            convertView = inflater.inflate(R.layout.activity_kakomp_catatan_kunjungan_pkl_guru_list, null);

        TextView id_catatan_kunjungan_pkl = convertView.findViewById(R.id.id_catatan_kunjungan_pkl);
        TextView nama_guru = convertView.findViewById(R.id.nama_guru);
        TextView tanggal_kunjungan = convertView.findViewById(R.id.tanggal_kunjungan);
        TextView catatan_pembimbing = convertView.findViewById(R.id.catatan_pembimbing);
        TextView nama_dudi = convertView.findViewById(R.id.nama_dudi);

        DataCatatanKunjunganPKL data = items.get(position);

        id_catatan_kunjungan_pkl.setText(data.getId_catatan_kunjungan_pkl());
        nama_guru.setText("Guru Pembimbing : "+data.getId_guru());
        tanggal_kunjungan.setText("Tanggal Kunjungan : "+ data.getTanggal_kunjungan());
        catatan_pembimbing.setText("Catatan : "+ data.getCatatan_pembimbing());
        nama_dudi.setText("DUDI : "+ data.getNama_dudi());
        return convertView;
    }

}
