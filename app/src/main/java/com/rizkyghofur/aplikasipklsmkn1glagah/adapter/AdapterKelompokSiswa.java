package com.rizkyghofur.aplikasipklsmkn1glagah.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataKelompokSiswa;
import java.util.List;

public class AdapterKelompokSiswa extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataKelompokSiswa> item;

    public AdapterKelompokSiswa(Activity activity, List<DataKelompokSiswa> item) {
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
            convertView = inflater.inflate(R.layout.activity_siswa_absensi_pkl_list_siswa, null);

        TextView nama_siswa = convertView.findViewById(R.id.nama_siswa);

        DataKelompokSiswa data = new DataKelompokSiswa();
        if(data!= null && item.size() !=0){
            data = item.get(position);
        }

        nama_siswa.setText(data.getNama());

        return convertView;

    }
}
