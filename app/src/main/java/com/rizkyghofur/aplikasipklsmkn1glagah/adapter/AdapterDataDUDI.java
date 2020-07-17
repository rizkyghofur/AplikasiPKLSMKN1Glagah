package com.rizkyghofur.aplikasipklsmkn1glagah.adapter;

import android.app.Activity;
import android.content.Context;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataDUDI;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class AdapterDataDUDI extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataDUDI> items;

    public AdapterDataDUDI(Activity activity, List<DataDUDI> items) {
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
            convertView = inflater.inflate(R.layout.activity_siswa_info_dudi_list, null);

        TextView id_dudi = convertView.findViewById(R.id.id_dudi);
        TextView nama_dudi = convertView.findViewById(R.id.nama_dudi);
        TextView alamat_dudi = convertView.findViewById(R.id.alamat_dudi);
        TextView no_telp_dudi = convertView.findViewById(R.id.no_telp_dudi);
        TextView jenis_usaha = convertView.findViewById(R.id.jenis_usaha);
        TextView nama_pimpinan = convertView.findViewById(R.id.nama_pimpinan);
        TextView no_telp_pimpinan = convertView.findViewById(R.id.no_telp_pimpinan);
        TextView kuota = convertView.findViewById(R.id.kuota);

        DataDUDI data = items.get(position);

        id_dudi.setText(data.getId_dudi());
        nama_dudi.setText(data.getNama_dudi());
        alamat_dudi.setText("Alamat : " + data.getAlamat_dudi());
        no_telp_dudi.setText("No. Telp DUDI : " + data.getNo_telp_dudi());
        jenis_usaha.setText("Jenis Usaha : " + data.getJenis_usaha());
        nama_pimpinan.setText("Nama Pimpinan : " + data.getNama_pimpinan());
        no_telp_pimpinan.setText("No. Telp. Pimpinan : "+ data.getNo_telp_pimpinan());
        kuota.setText("Kuota : " + data.getKuota());
        return convertView;
    }
}
